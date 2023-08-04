package com.atabekyan.image.service.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import com.atabekyan.image.service.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public Path getRootLocation() {
        return rootLocation;
    }

    @Override
    public String store(MultipartFile file) {
            if(Objects.isNull(file)) {
                throw new StorageException("File is not exist");
            }

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }

            String extension = getFileExtension(file);

            String filename = UUID.randomUUID() + extension;
            Path destinationFile = getAbsolutePath(filename);

            if (isInCurrentFolder(destinationFile)) {
                throw new StorageException("Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(
                        inputStream,
                        destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

                return filename;
            } catch (IOException exception) {
                throw new StorageException("I/O exception");
            }
    }

    private boolean isInCurrentFolder(Path destinationFile) {
        Path rootAbsoulutePath = getRootLocation().toAbsolutePath();

        Path parent = destinationFile.getParent();
        return !parent.equals(rootAbsoulutePath);
    }

    @Override
    public Stream<Path> loadAll() {
        try(Stream<Path> walk = Files.walk(this.rootLocation, 1)){
            return walk
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteFile(String url) {
        Path destinationFile = Path.of(rootLocation + "/" + url).normalize().toAbsolutePath();
        FileSystemUtils.deleteRecursively(destinationFile.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    private String getFileExtension(MultipartFile file) throws NullPointerException {
        String filename = file.getOriginalFilename();

        return Objects
                .requireNonNull(filename)
                .substring(filename.lastIndexOf("."));
    }

    private Path getAbsolutePath(String filename) {
        Path filenamePath = Paths.get(filename);

        return getRootLocation()
                .resolve(filenamePath)
                .normalize().toAbsolutePath();
    }
}
