Library Management System — веб-приложение для управления библиотекой с AI-рекомендациями книг(It's web library management app with AI-powered book recommendations).

Стэк технологий(technology stack):
|Java 21|
|Spring Boot|
|Spring Security|
|Spring Data JPA|
|Hibernate|
|MySQL|
|Docker|
|Docker Compose|
|Thymeleaf|
|OpenRouter API|

Функционал приложения(application functionality):
ROLE USER:
просмотр каталога/Browse the catalog;
поиск книг/Search for books;
операции с книгами/Book operations;
AI рекомендации/AI recommendationallity;
ROLE ADMIN:
добавление книг/adding books;
удаление книг/delete books;
управление пользователями/User management;
управление операциями/Transaction management;

Как запустить приложение/How to start the app:

1. docker pull ivanalem/librarymanagement-db:8.0
2. docker pull ivanalem/librarymanagementv2:latest
3. docker compose up
