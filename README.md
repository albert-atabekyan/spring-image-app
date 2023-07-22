# Image Service
Сервис для хранения фотографий
## Зависимости
- docker
- maven
- java 18
## Клонирование репозитория
Для работы с кодом приложения необходимо склонировать репозитория. 
```bash
git clone https://github.com/albert-atabekyan/spring-image-service.git
```
## Развертывание приложения
Для развертывания бекенд-части приложения необходимо перейти в папку `spring-image-service` и собрать образ:
```bash
cd spring-image-service
docker build -t $image_name --build-arg $JAR_PATH . 
```
$JAR_PATH - это путь до jar файла относительно папки `spring-image-service`, который собирается maven-ом.

Сборка jar-файла:
```bash
maven install -f pom.xml
```
Далее необходимо запустить docker-compose файл:
```bash
docker compose up 
```
Для развертывания фронтенд-части приложения необходимо перейти в папку `frontend` и собрать образ:
```bash
cd src/main/frontend
docker build -t $image_name . 
```
Далее необходимо запустить контейнер:
```bash
docker run -p 3000:3000 $image_name
```

Важно все действия выполнять в заданных папках, т. к. при сборке образа docker-контекст берет файлы из текущей директории. 
