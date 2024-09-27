# News Application

## Описание
News Application - это клиент-серверное приложение. Сервер представляет собой RestApi Spring приложение. В качестве базы данных используется MySQL. Серверная часть каждые 20 минут парсит новостной сайт и выгружает в БД новости. Клиентская часть представляет собой JavaFX UI, запрашивает новости за выбранный промежуток времени.

## Технологии
- Spring Boot
- MySQL
- Liquibase
- JavaFX
- Docker, DockerCompose

### Запуск серверной части
1. Отредактировать файл docker-compose.yml
2. Запустить контейнер:
```
docker-compose up --build
```
### Запуск клиентской части
1. Скачать проект на локальный АРМ.
2. Выполнить запуск через Gradle:
```
./gradlew run
```