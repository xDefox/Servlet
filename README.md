# REST API для управления студентами, группами и факультетами

Лабораторная работа по Java. REST-сервис на Spring Boot с JPA, Spring Security и базой данных.

## Технологии

- **Java 17+**
- **Spring Boot** — Web, Data JPA, Security
- **Hibernate / JPA** — ORM
- **Spring Security** — HTTP Basic + ролевая модель (BCrypt)
- **PostgreSQL / H2** — БД (зависит от конфигурации)
- **Jackson** — сериализация JSON
- **Maven** — сборка

## Сущности

| Сущность | Описание | Связи |
|----------|----------|-------|
| **Student** | Студент | принадлежит к Group |
| **Group** | Учебная группа | содержит Student, принадлежит Faculty |
| **Faculty** | Факультет | содержит Group |
| **User** | Пользователь системы | для аутентификации (роли USER / ADMIN) |

## Безопасность

- **HTTP Basic Auth**
- **Роли:**
  - `USER` — только чтение (GET)
  - `ADMIN` — полный доступ (GET, POST, PUT, DELETE)

| Метод | USER | ADMIN |
|-------|------|-------|
| GET   | ✅   | ✅    |
| POST  | ❌   | ✅    |
| PUT   | ❌   | ✅    |
| DELETE| ❌   | ✅    |

## API Endpoints

### Group
- `GET /api/group` — список групп
- `GET /api/group/{id}` — группа по ID
- `POST /api/group` — создать группу
- `PUT /api/group/{id}` — обновить группу
- `DELETE /api/group/{id}` — удалить группу

### Student
- `GET /api/student` — список студентов
- `GET /api/student/{id}` — студент по ID
- `GET /api/student/surname/{surname}` — поиск по фамилии
- `POST /api/student` — создать студента
- `PUT /api/student/{id}` — обновить студента
- `DELETE /api/student/{id}` — удалить студента

### Faculty
- `GET /api/faculty` — список факультетов
- `GET /api/faculty/{id}` — факультет по ID
- `POST /api/faculty` — создать факультет
- `PUT /api/faculty/{id}` — обновить факультет
- `DELETE /api/faculty/{id}` — удалить факультет

## Запуск

### Требования
- JDK 17+
- Maven 3.8+
- PostgreSQL (если используется в `application.properties`)

### Сборка и старт
```bash
# Сборка
./mvnw clean package

# Запуск
java -jar target/*.jar

# Или через Maven Wrapper
./mvnw spring-boot:run
```

### Конфигурация БД
В `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lab3_db
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## Структура проекта

```
src/main/java/SR/Lab3/
├── config/
│   └── WebSecurityConfig.java      # Spring Security конфигурация
├── controller/
│   ├── GroupController.java        # REST API для групп
│   ├── StudentController.java      # REST API для студентов
│   └── FacultyController.java      # REST API для факультетов
├── entity/
│   ├── AbstractEntity.java         # Базовый класс с ID
│   ├── Group.java                  # Сущность группы
│   ├── Student.java                # Сущность студента
│   ├── Faculty.java                # Сущность факультета
│   └── User.java                   # Сущность пользователя
├── repository/
│   ├── GroupRepository.java        # JPA репозиторий групп
│   ├── StudentRepository.java      # JPA репозиторий студентов
│   ├── FacultyRepository.java      # JPA репозиторий факультетов
│   └── UserRepository.java         # JPA репозиторий пользователей
├── security/
│   └── CustomUserDetailService.java # Загрузка пользователей из БД
└── service/
    ├── Service.java                # Общий интерфейс CRUD
    ├── GroupService.java           # Бизнес-логика групп
    ├── StudentService.java         # Бизнес-логика студентов
    └── FacultyService.java         # Бизнес-логика факультетов
```

## Примеры запросов

### Создать группу (ADMIN)
```bash
curl -X POST http://localhost:8080/api/group   -u admin:admin   -H "Content-Type: application/json"   -d '{"gr_name":"ИВТ-101","faculty_id":1}'
```

### Получить всех студентов (USER)
```bash
curl -u user:user http://localhost:8080/api/student
```

### Найти студентов по фамилии
```bash
curl -u admin:admin http://localhost:8080/api/student/surname/Иванов
```

## Особенности реализации

- **Bidirectional JSON** — используются `@JsonManagedReference` / `@JsonBackReference` для предотвращения циклических ссылок при сериализации.
- **LAZY loading** — на связях `@ManyToOne` для оптимизации запросов.
- **Пароли** — хешируются через `BCryptPasswordEncoder`.
- **Гибкая архитектура** — общий интерфейс `Service<T>` для всех сущностей.

## Лицензия

Учебный проект.
