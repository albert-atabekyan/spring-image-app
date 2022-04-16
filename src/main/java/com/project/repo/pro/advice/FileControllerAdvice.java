package com.project.repo.pro.advice;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {FileSizeLimitExceededException.class})
    public ResponseEntity<String> fileLimitException(FileSizeLimitExceededException ex, WebRequest request) {
        return new ResponseEntity<>("Большой размер", HttpStatus.ACCEPTED);
    }
}
