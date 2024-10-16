# JPA и Spring Data JPA

Java Persistence API (JPA) — это стандарт языка Java для управления реляционными
данными в Java-приложениях. Этот стандарт обеспечивает удобный механизм
взаимодействия с базой данных через объектно-реляционное отображение (ORM).

Spring Data JPA упрощает работу с JPA путём автоматизации написания
повторяющегося кода. Этот фреймворк построен на основе JPA и предлагает
упрощенное API для исполнения CRUD-операций и создания запросов на основе имён
методов.

Пример JPA-сущности:

```java
@Entity
public class FunExampleEntity {
  @Id
  private Long id;
  // Поля, которые также требуются.
}
```

Используя Spring Data JPA, мы можем определить репозиторий следующим образом:

```java
public interface FunExampleEntityRepository extends 
        JpaRepository<FunExampleEntity, Long> {
    List<FunExampleEntity> findByFunPropertyName(String funPropertyName);
}
```

## Особенности Spring Data JPA

### Реализация шаблона "репозиторий" в Spring Data JPA


