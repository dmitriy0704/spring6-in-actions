# Hibernate

## Введение

Hibernate Framework — это фреймворк для языка Java, предназначенный для работы с
базами данных. Он реализует объектно-реляционную модель — технологию, которая
«соединяет» программные сущности и соответствующие записи в базе.

Объектно-реляционная модель, или ORM, позволяет создать программную
«виртуальную» базу данных из объектов. Объекты описываются на языках
программирования с применением принципов ООП. Java Hibernate — популярное
воплощение этой модели.

Hibernate построен на спецификации JPA 2.1 — наборе правил, который описывает
взаимодействие программных объектов с записями в базах данных. JPA поясняет, как
управлять сохранением данных из кода на Java в базу. Но сама по себе
спецификация — только теоретические правила, а в «чистой» Java ее реализации
нет.

### Особенности Hibernate Framework

Идея фреймворка — создать «виртуальную» базу данных из программных объектов и
работать с ней как с реальной базой. Поэтому им часто пользуются для упрощения
работы. Он берет на себя взаимодействие с реляционной БД, а разработчику
остается работать с кодом.
В Hibernate есть возможность, которую называют «ленивой загрузкой». Объекты в
фреймворке не подгружают всю информацию из базы изначально. Вместо этого они
просто «знают», как ее получить, и при первом обращении к информации загружают
ее в фоне. Это нужно для оптимизации производительности.

Внутри Hibernate — собственный язык запросов Hibernate Query Language, или HQL.
Это SQL-подобный язык, но полностью объектно-ориентированный и более краткий —
не приходится так много работать с шаблонным кодом, как в «чистом» SQL.

### Преимущества использования Hibernate

**Популярность.** Hibernate — популярный фреймворк, который фактически считают
золотым стандартом. Это влечет за собой сразу три преимущества:

- по нему много учебников, туториалов и обсуждений на специализированных сайтах;
- большинство технологий, работающих с Java, поддерживают Hibernate. Это базы
  данных, фреймворки, библиотеки. Существуют плагины и для других языков или
  платформ, например порт на платформу .NET;
- разработчик, умеющий работать с этим фреймворком, всегда найдет работу.
  **Устранение лишнего кода.** Повторяющийся шаблонный код, который делает
  программу менее читаемой, называют «спагетти». Если программа взаимодействует
  с базой, то в ней по определению много «спагетти»-кода, в том числе
  низкоуровневого. Использование Hibernate позволяет сократить количество
  «спагетти», соответственно, сделать программу более лаконичной и хорошо
  читаемой.

**Возможность сосредоточиться на логике.** Разработчику не приходится писать
множество запросов, он избавлен от написания большого количества «технического»
низкоуровневого кода. Поэтому можно сосредоточиться на логике работы программы и
не отвлекаться на шаблонные задачи, поручив их фреймворку. Это облегчает и
ускоряет разработку.

**Независимость от базы данных.** Hibernate может работать с любой базой и не
имеет жесткой привязки к какой-то конкретной базе или СУБД. Благодаря этому он
гибкий, его можно использовать в связке с другими технологиями. Язык запросов у
него тоже свой, независимый от СУБД, хотя Hibernate поддерживает и «чистый» SQL.

**Объектно-ориентированный подход.** Hibernate реализует парадигму
объектно-ориентированного программирования, которая очень распространена и
хорошо знакома разработчикам. Поэтому работать с ним относительно просто, если
вы уже знаете основы: не приходится постоянно отвлекаться на совершенно другую
логику работы с базами данных. Можно реализовать все управление на ООП — этому
способствует и наличие собственного SQL-подобного объектно-ориентированного
языка запросов.

### Недостатки Hibernate Framework

**Сложность в освоении.** Эта проблема актуальна в основном для новичков. Для
работы с фреймворком нужно понимать теорию реляционных БД. Понадобится знать,
что такое транзакция, по каким принципам работают базы данных, как с ними
взаимодействовать и многое другое. Естественно, надо знать Java: осваивать
фреймворки советуют после изучения основных принципов «чистого» языка.

**Проблемы с производительностью.** Несмотря на возможности, которые дает
«ленивая загрузка», спецификацию JPA и в частности Hibernate часто критикуют за
низкую производительность. Есть мнение, которое частично подкрепляется на
практике, что такой тип взаимодействия с базой замедляет и утяжеляет код.

**Непредсказуемость кода.** Это еще один частый пункт критики JPA и Hibernate
как ее реализации. Спецификация построена на объектно-ориентированной модели
программирования, но не полностью соблюдает ее принципы. Это приводит к тому,
что в коде могут появиться побочные эффекты — так называется явление, когда во
время выполнения программы какие-то значения неявно изменяются. Побочные эффекты
могут влиять на правильность работы программы, так что их надо избегать. А при
использовании JPA избежать их сложно, и к Hibernate это тоже относится.

**Сложности с кэшированием.** В целом информацию в Hibernate можно кэшировать,
то есть сохранять в специальном участке памяти, очень быстром и компактном. Это
один из плюсов фреймворка — кэширование важно для производительности. Оно нужно
для быстрого доступа к важным данным.

На практике кэширование самих сущностей JPA работает своеобразно:

- изменяемые сущности кэшировать не получится — инструментарий не дает
  возможности синхронизировать изменения с кэшем;
- неизменяемые сущности кэшировать можно, но не полноценно. Сущность загружается
  из базы через транзакцию — последовательность запросов в БД. Когда транзакция
  закрывается, на кэшированную сущность становится нельзя сослаться — только
  получить из нее данные.

## Архитектура

Hibernate состоит из таких частей как:

- **Transaction** - Этот объект представляет собой рабочую единицу работы с БД.
  В
  Hibernate транзакции обрабатываются менеджером транзакций.
- **SessionFactory** - Самый важный и самый тяжёлый объект (обычно создаётся в
  единственном экземпляре, при запуске приложения). Нам необходима как минимум
  одна SessionFactory для каждой БД, каждый из которых конфигурируется отдельным
  конфигурационным файлом.
- **Session** - Сессия используется для получения физического соединения с БД.
  Обычно, сессия создаётся при необходимости, а после этого закрывается. Это
  связано с тем, что эти объекты крайне легковесны. Чтобы понять, что это такое,
  модно сказать, что создание, чтение, изменение и удаление объектов происходит
  через объект Session.
- **Query** - Этот объект использует HQL или SQL для чтения/записи данных из/в
  БД.
  Экземпляр запроса используется для связывания параметров запроса, ограничения
  количества результатов, которые будут возвращены и для выполнения запроса.
- **Configuration** - Этот объект используется для создания объекта
  SessionFactory и
  конфигурирует сам Hibernate с помощью конфигурационного XML-файла, который
  объясняет, как обрабатывать объект Session.
- **Criteria** - Используется для создания и выполнения объекто-ориентированного
  запроса для получения объектов.

## Конфигурирование

Для корректной работы, мы должны передать Hibernate подробную информацию,
которая связывает наши Java-классы c таблицами в базе данных (далее – БД). Мы,
также, должны указать значения определённых свойств Hibernate.

Обычно, вся эта информация помещена в отдельный файл, либо XML-файл –
hibernate.cfg.xml, либо – hibernate.properties.

### Ключевые свойства:

#### hibernate.dialect

Указывает Hibernate диалект БД. Hibernate, в свою очередь, генерирует
необходимые SQL-запросы (например, org.hibernate.dialect.MySQLDialect,
если мы используем MySQL).

#### hibernate.connection-driver_class

Указывает класс JDBC драйвера.

#### hibernate.connection.url

Указывает URL (ссылку) необходимой нам БД (например,
jdbc:mysql://localhost:3306/database).

#### hibernate.connection.username

Указывает имя пользователя БД (например, root).

#### hibernate.connection.password

Указывает пароль к БД (например, password).

#### hibernate.connection.pool_size

Ограничивает количество соединений, которые находятся в пуле соединений
Hibernate.

#### hibernate.connection.autocommit

Указывает режим autocommit для JDBC-соединения.

_**Пример конфигурационного XML-файла.**_    
**Исходные данные:**
Тип БД: MySQL  
Имя базы данных: database  
Имя пользователя: root  
Пароль: password

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration SYSTEM
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <property name="hibernate.dialect">
            org.hibernate.dialect.MySQLDialect
        </property>
        <property name="hibernate.connection.driver_class">
            com.mysql.jdbc.Driver
        </property>

        <!-- Assume test is the database name -->
        <property name="hibernate.connection.url">
            jdbc:mysql://localhost/database
        </property>
        <property name="hibernate.connection.username">
            root
        </property>
        <property name="hibernate.connection.password">
            password
        </property>

    </session-factory>
</hibernate-configuration>
```

## Сессии

Сессия используется для получения физического соединения с базой данных.  
Благодаря тому, что сессия является легковесны объектом, его создают
(открывают сессию) каждый раз, когда возникает необходимость, а потом, когда
необходимо, уничтожают (закрывают сессию). Мы создаём, читаем, редактируем и
удаляем объекты с помощью сессий.

Мы стараемся создавать сессии при необходимости, а затем уничтожать их из-за
того, что ни не являются потоко-защищёнными и не должны быть открыты в течение
длительного времени.

Экземпляр класса может находиться в одном из трёх состояний:

- transient - Это новый экземпляр устойчивого класса, который не привязан к
  сессии и ещё не представлен в БД. Он не имеет значения, по которому может быть
  идентифицирован.
- persistent - Мы можем создать переходный экземпляр класса, связав его с
  сессией. Устойчивый экземпляр класса представлен в БД, а значение
  идентификатора связано с сессией.
- detached - После того как сессия закрыта, экземпляр класса становится
  отдельным, независимым экземпляром класса.

Пример:

```java
class Demo {
    public void demo() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            /**
             * Here we make some work.
             * */

            transaction.commit();
        } catch (Exception e) {
            // В случае исключенич - откат транзакции
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
```

Методы интерфейса Session:

- **Transaction beginTransaction()** - Начинает транзакцию и возвращает объект
  Transaction.
- **void cancelQuery()** - Отменяет выполнение текущего запроса.
- **void clear()** - Полностью очищает сессию
- **Connection close()** - Заканчивает сессию, освобождает JDBC-соединение и
  выполняет очистку.
- **Criteria createCriteria(String entityName)** - Создание нового экземпляра
  Criteria для объекта с указанным именем.
- **Criteria createCriteria(Class persistentClass)** - Создание нового
  экземпляра Criteria для указанного класса.
- **Serializable getIdentifier(Object object)** - Возвращает идентификатор
  данной сущности, как сущности, связанной с данной сессией.
- **void update(String entityName, Object object)** - Обновляет экземпляр с
  идентификатором, указанном в аргументе.
- **void update(Object object)** - Обновляет экземпляр с идентификатором,
  указанном в аргументе.
- **void saveOrUpdate(Object object)** - Сохраняет или обновляет указанный
  экземпляр.
- **Serializable save(Object object)** - Сохраняет экземпляр, предварительно
  назначив сгенерированный идентификатор.
- **boolean isOpen()** - Проверяет открыта ли сессия.
- **boolean isDirty()** - Проверят, есть ли в данной сессии какие-либо
  изменения, которые должны быть синхронизованы с базой данных (далее – БД).
- **boolean isConnected()** - Проверяет, подключена ли сессия в данный момент.
- **Transaction getTransaction()** - Получает связанную с этой сессией
  транзакцию.
- **void refresh(Object object)** - Обновляет состояние экземпляра из БД.
- **SessionFactory getSessionFactory()** - Возвращает фабрику сессий (
  SessionFactory), которая создала данную сессию.
- **Session get(String entityName, Serializable id)** - Возвращает сохранённый
  экземпляр с указанными именем сущности и идентификатором. Если таких
  сохранённых экземпляров нет – возвращает null.
- **void delete(String entityName, Object object)** - Удаляет сохранённый
  экземпляр из БД.
- **void delete(Object object)** - Удаляет сохранённый экземпляр из БД.
- **SQLQuery createSQLQuery(String queryString)** - Создаёт новый экземпляр
  SQL-запроса (SQLQuery) для данной SQL-строки.
- **Query createQuery(String queryString)** - Создаёт новый экземпляр запроса (
  Query) для данной HQL-строки.
- **Query createFilter(Object collection, String queryString)** - Создаёт новый
  экземпляр запроса (Query) для данной коллекции и фильтра-строки.

## Сохраняемые классы

Ключевая функция Hibernate заключается в том, что мы можем взять значения из
нашего Java-класса и сохранить их в таблице базы данных. С помощью
конфигурационных файлов мы указываем Hibernate как извлечь данные из класса и
соединить с определённым столбцами в таблице БД.

Если мы хотим, чтобы экземпляры (объекты) Java-класса в будущем сохранялся в
таблице БД, то мы называем их “сохраняемые классы” (persistent class).
Чтобы сделать работу с Hibernate максимально удобной и эффективной, нам следует
использовать программную модель Простых _Старых Java Объектов_ (Plain Old Java
Object – POJO).

Существуют определённые требования к POJO классам. Вот самые главные из них:

- Все классы должны иметь ID для простой идентификации наших объектов в БД и в
  Hibernate. Это поле класса соединяется с первичным ключом (primary key)
  таблицы БД.
- Все POJO – классы должны иметь конструктор по умолчанию (пустой).
- Все поля POJO – классов должны иметь модификатор доступа private иметь набор
  getter-ов и setter-ов в стиле JavaBean.
- POJO – классы не должны содержать бизнес-логику.

Мы называем классы POJO для того, чтобы подчеркнуть тот факт, что эти объекты
являются экземплярами обычных Java-классов.

Ниже приведён пример POJO – класса, которые соответствует условиям, написанным
выше:

```java

package net.proselyte.hibernate.pojo;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private String specialty;
    private int experience;

    /**
     * Default Constructor
     */
    public Developer() {
    }

    /**
     * Plain constructor
     */
    public Developer(int id, String firstName, String lastName, String specialty, int experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.experience = experience;
    }

    /**
     * Getters and Setters
     */


    /**
     * toString method (optional)
     */
    @Override
    public String toString() {
        return "Developer{" Поля "}";
    }
}

```

## Соединяющие файлы

Чаще всего, когда мы имеем дело с ORM фреймворком, связи между объектами и
таблицами в базе данных (далее – БД) указываются в XML – файле.

Для класса Developer из предыдущего раздела создается таблица в БД:

```sql
CREATE TABLE HIBERNATE_DEVELOPERS
(
    ID         INT NOT NULL AUTO_INCREMENT,
    FIRST_NAME VARCHAR(50) DEFAULT NULL,
    LAST_NAME  VARCHAR(50) DEFAULT NULL,
    SPECIALTY  VARCHAR(50) DEFAULT NULL,
    EXPERIENCE INT         DEFAULT NULL,
    PRIMARY KEY (ID)
);
```

На данный момент у нас есть две независимых друг от друга сущности:
POJO – класс Developer.java и таблица в БД HIBERNATE_DEVELOPERS.

Чтобы связать их друг с другом и получить возможность сохранять значения полей
класса, нам необходимо объяснить, как именно это делать Hibernate фреймворку.
Чтобы это сделать, мы создаём конфигурационной XML – файл Developer.hbm.xml
Вот этот файл:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="net.proselyte.hibernate.pojo.Developer"
           table="HIBERNATE_DEVELOPERS">
        <meta attribute="class-description">
            This class contains developer's details.
        </meta>
        <id name="id" type="int" column="id">
            <generator class="native"/>
        </id>
        <property name="firstName" column="first_name" type="string"/>
        <property name="lastName" column="last_name" type="string"/>
        <property name="specialty" column="last_name" type="string"/>
        <property name="experience" column="salary" type="int"/>
    </class>
</hibernate-mapping>
```

**_Свойтва:_**

- **\<hibernate-mapping>**  
  Это ключевой тег, который должен быть в каждом XML – фалйе для связывания
  (mapping). Внутри этого тега мы и конфигурируем наши связи.
- **\<class>**  
  Тег \<class> используется для того, чтоы указать связь между POJO – классов
  и таблицей в БД. Имя класса указывается с помощью свойства name, имя таблицы в
  БД – с помощью свойства table.
- **\<meta>**  
  Опциональный (необязательный) тег, внутри которого мы можем добавить описание
  класса.
- **\<id>**  
  Тег \<id> связывает уникальный идентификатор ID в POJO – классе и первичный
  ключ (primary key) в таблице БД. Свойство name соединяет поле класса со
  свойством column, которое указывает нам колонку в таблице БД. Свойство type
  определяет тип связывания (mapping) и используется для конвертации типа
  данных Java в тип данных SQL.
- **\<generator>**  
  Этот тег внутри тега <id> используется для того, что генерировать первичные
  ключи автоматически. Если мы указываем это свойство native, как в примере,
  приведённом выше, то Hibernate сам выберет алгоритм (identity, hilo, sequence)
  в зависимости от возможностей БД.
- **\<property>**  
  Мы используем этот тег для того, чтобы связать (map) конкретное поле POJO –
  класса с конкретной колонкой в таблице БД. Свойство name указывает поле в
  классе, в то время как свойство column указывает на колонку в таблице БД.
  Свойство type указывает тип связывания (mapping) и конвертирует тип данных
  Java в тип данных SQL.

## Создание простого приложения:

Этапы:

1. Создать POJO Developer

```java
package dev.folomkin;

public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private String specialty;
    private int experience;

    /**
     * Default Constructor
     */
    public Developer() {
    }

    public Developer(int id, String firstName, String lastName, String specialty, int experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialty='" + specialty + '\'' +
                ", experience=" + experience +
                '}';
    }
}
```

2. Cоздание таблицы HIBERNATE_DEVELOPERS

```sql
CREATE TABLE HIBERNATE_DEVELOPERS
(
    ID         INT NOT NULL AUTO_INCREMENT,
    FIRST_NAME VARCHAR(50) DEFAULT NULL,
    LAST_NAME  VARCHAR(50) DEFAULT NULL,
    SPECIALTY  VARCHAR(50) DEFAULT NULL,
    EXPERIENCE INT         DEFAULT NULL,
    PRIMARY KEY (ID)
);

```

3. Cоздание конфигурационного файла hibernate.cfg.xml

```xml
hibernate.cfg.xml


        <?xml version="1.0" encoding="utf-8"?>
        <!DOCTYPE hibernate-configuration SYSTEM
                "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <property name="hibernate.dialect">
            org.hibernate.dialect.MySQLDialect
        </property>
        <property name="hibernate.connection.driver_class">
            com.mysql.jdbc.Driver
        </property>

        <!-- Assume ИМЯ ВАШЕЙ БД is the database name -->
        <property name="hibernate.connection.url">
            jdbc:mysql://localhost/ИМЯ_ВАШЕЙ_БАЗЫ_ДАННЫХ
        </property>
        <property name="hibernate.connection.username">
            ВАШЕ ИМЯ ПОЛЬЗОВАТЕЛЯ
        </property>
        <property name="hibernate.connection.password">
            ВАШ ПАРОЛЬ
        </property>

        <!-- List of XML mapping files -->
        <mapping resource="Developer.hbm.xml"/>

    </session-factory>
</hibernate-configuration>
```

```xml
Developer.hbm.xml

        <?xml version="1.0" encoding="utf-8"?>
        <!DOCTYPE hibernate-mapping PUBLIC
                "-//Hibernate/Hibernate Mapping DTD//EN"
                "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">
<hibernate-mapping>
    <class name="net.proselyte.hibernate.example.model.Developer"
           table="HIBERNATE_DEVELOPERS">
        <meta attribute="class-description">
            This class contains developer's details.
        </meta>
        <id name="id" type="int" column="ID">
            <generator class="native"/>
        </id>
        <property name="firstName" column="FIRST_NAME" type="string"/>
        <property name="lastName" column="LAST_NAME" type="string"/>
        <property name="specialty" column="SPECIALTY" type="string"/>
        <property name="experience" column="EXPERIENCE" type="int"/>
    </class>
</hibernate-mapping>

```

4. Создание основного класса приложения DeveloperRunner.java

```java
package net.proselyte.hibernate.example;

import net.proselyte.hibernate.example.model.Developer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DeveloperRunner {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();

        DeveloperRunner developerRunner = new DeveloperRunner();

        System.out.println("Adding developer's records to the DB");
        /**
         *  Adding developer's records to the database (DB)
         */
        developerRunner.addDeveloper("Proselyte", "Developer", "Java Developer", 2);
        developerRunner.addDeveloper("Some", "Developer", "C++ Developer", 2);
        developerRunner.addDeveloper("Peter", "UI", "UI Developer", 4);

        System.out.println("List of developers");
        /**
         * List developers
         */
        List developers = developerRunner.listDevelopers();
        for (Developer developer : developers) {
            System.out.println(developer);
        }
        System.out.println("===================================");
        System.out.println("Removing Some Developer and updating Proselyte");
        /**
         * Update and Remove developers
         */
        developerRunner.updateDeveloper(10, 3);
        developerRunner.removeDeveloper(11);

        System.out.println("Final list of developers");
        /**
         * List developers
         */
        developers = developerRunner.listDevelopers();
        for (Developer developer : developers) {
            System.out.println(developer);
        }
        System.out.println("===================================");

    }

    public void addDeveloper(String firstName, String lastName, String specialty, int experience) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Developer developer = new Developer(firstName, lastName, specialty, experience);
        session.save(developer);
        transaction.commit();
        session.close();
    }

    public List listDevelopers() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        List developers = session.createQuery("FROM Developer").list();

        transaction.commit();
        session.close();
        return developers;
    }

    public void updateDeveloper(int developerId, int experience) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, developerId);
        developer.setExperience(experience);
        session.update(developer);
        transaction.commit();
        session.close();
    }

    public void removeDeveloper(int developerId) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Developer developer = (Developer) session.get(Developer.class, developerId);
        session.delete(developer);
        transaction.commit();
        session.close();
    }

}


```

## Виды связей

Связи в ORM деляся на 3 гурппы:

- Связывание коллекций
- Ассоциативное связывание
- Связывание компонентов

### Связывание коллекций

Если среди значений класса есть коллекции (collections) каких-либо значений, мы
можем связать (map) их с помощью любого интерфейса коллекций, доступных в Java.

В Hibernate мы можем оперировать следующими коллекциями:

- java.util.List - Связывается (mapped) с помощью элемента \<list> и
  инициализируется с помощью java.util.ArrayList
- java.util.Collection - Связывается (mapped) с помощью элементов \<bag> или
  \<ibag> и инициализируется с помощью java.util.ArrayList
- java.util.Set - Связывается (mapped) с помощью элемента \<set> и
  инициализируется с помощью java.util.HashSet
- java.util.SortedSet - Связывается (mapped) с помощью элемента \<set> и
  инициализируется с помощью java.util.TreeSet. В качестве параметра для
  сравнения может выбрать либо компаратор, либо естественный порядок.
- java.util.Map - Связывается (mapped) с помощью элемента \<map> и
  инициализируется с помощью java.util.HashMap.
- java.util.SortedMap - Связывается (mapped) с помощью элемента <map> и
  инициализируется с помощью java.util.TreeMap. В качестве параметра для
  сравнения может выбрать либо компаратор, либо естественный порядок.

### Ассоциативное связывание

Связывание ассоциаций – это связывание (mapping) классов и отношений между
таблицами в БД. Существует 4 типа таких зависимостей:

- Many-to-One
- One-to-One
- One-to-Many
- Many-to-Many

Связывание компонентов

Возможна ситуация, при которой наш Java – класс имеет ссылку на другой класс,
как одну из переменных. Если класс, на который мы ссылаемся не имеет своего
собственного жизненного цикла и полностью зависит от жизненного цикла класса,
который на него ссылается, то класс, на который ссылаются называется классом
Компонентом (Component Class).

## Анотации

в Hibernate предусмотрена возможность конфигурирования прилоения с помощью
аннотаций.

### Обязательными аннотациями являются следующие:

**@Entity**  
Эта аннотация указывает Hibernate, что данный класс является сущностью (entity
bean). Такой класс должен иметь конструктор по-умолчанию (пустой конструктор).

**@Table**  
С помощью этой аннотации мы говорим Hibernate, с какой именно таблицей
необходимо связать (map) данный класс. Аннотация @Table имеет различные
аттрибуты, с помощью которых мы можем указать имя таблицы, каталог, БД и
уникальность столбцов в таблец БД.

**@Id**  
С помощью аннотации @Id мы указываем первичный ключ (Primary Key) данного
класса.

**@GeneratedValue**  
Эта аннотация используется вместе с аннотацией @Id и определяет такие паметры,
как strategy и generator.

**@Column**  
Аннотация @Column определяет к какому столбцу в таблице БД относится конкретное
поле класса (аттрибут класса).
Наиболее часто используемые аттрибуты аннотации @Column такие:

- name - Указывает имя столбца в таблице
- unique - Определяет, должно ли быть данноезначение уникальным
- nullable - Определяет, может ли данное поле быть NULL, или нет.
- length - Указывает, какой размер столбца (например колчиство символов, при
  использовании String).

## Язык запросов Hibernate (HQL).

HQL (Hibernate Query Language) – это объекто-ориентированный (далее – ОО) язык
запросов, который крайне похож на SQL.

Отличие между HQL и SQL состоит в том, что SQL работает таблицами в базе
данных (далее – БД) и их столбацами, а HQL – с сохраняемыми объектами (
Persistent Objects) и их полями (аттрибутами класса).

Hibernate трнаслирует HQL – запросы в понятные для БД SQL – запросы, которые и
выполняют необходимые нам действия в БД.

Мы также имеем возможность испольщовать обычные SQL – запросы в Hibernate
используя Native SQL, но использование HQL является более предпочтительным.

Основные ключевые слова языка HQL:

**FROM**

Если мы хотим загрузить в память наши сохраняемые объекты, то мы будем
использовать ключевое слово FROM. Вот пример его использования:

    Query query = session.createQuery("FROM Developer");
    List developers = query.list();

Developer – это POJO – класс Developer.java, который ассоцииргван с таблицей в
шагей БД.

**INSERT**

Мы используем ключевое слово INSERT, в том случае, если хотим добавить запись в
таблицу нашей БД.  
Вот пример использования этого ключевого слова:

    Query query =
    session.createQuery("INSERT INTO Developer (firstName, lastName, specialty, experience)");

**UPDATE**

Ключевое слово UPDATE используется для обновления одного или нескольких полей
объекта. Вот так это выглядит на практике:

    Query query =
    session.createQuery(UPDATE Developer SET experience =: experience WHERE id =: developerId);
    query.setParameter("expericence", 3);

**DELETE**

Это ключевоеслово используется для удаления одного или нескольких объектов.
Пример использования:

    Query query = session.createQuery("DELETE FROM Developer WHERE id = :
    developerId");
    query.setParameter("developerId", 1);

**SELECT**

Если мы хотим получить запись из таблицы нашей БД, то мы должны использоваьб
ключевое слово SELECT. Пример использования:

    Query query = session.createQuery("SELECT D.lastName FROM Developer D");
    List developers = query.list();

**AS**

В предыдущем примере мы использовали запись формы Developer D. С использованием
опционального ключевого слова AS это будет выглядеть так:

    Query query = session.createQuery("FROM Developer AS D");
    List developers = query.list();

**WHERE**

В том случае, если мы хотим получить объекты, которые соответствуют опредлённым
параметрам, то мы должны использовать ключевое слово WHERE. На практике это
выглядит следующим образом:

    Query query = session.createQuery("FROM Developer D WHERE D.id = 1");
    List developer = query.list();

**ORDER BY**

Жля того, чтобы отсортировать список обхектов, полученных в результате запроса,
мы должны применить ключевое слово ORDER BY. Нам необходимо укаать параметр, по
которому список будет отсортирован и тип сортировки – по возрастанию (ASC) или
по убыванию (DESC). В виде кода это выгллядит так:

    Query query =
    session.createQuery("FROM Developer D WHERE experience > 3 ORDER BY D.experience
    DESC");

**GROUP BY**

С помощью ключевого слова GROUP BY мы можем группировать данные, полученные из
БД по какому-либо признаку. Вот простой пример применения данного ключевого
слова:

    Query query = session.createQuery("SELECT MAX(D.experience), D.lastName,
    D.specialty FROM Developer D GROUP BY D.lastName");
    List developers = query.list();

### Методы аггрегации

Язык запросов Hibernate (HQL) пожжерживает различные методы аггрегации, которые
доступны и в SQL. HQL поддреживает слудующие методы:

min(имя свойства)  
Мнимальное значение данного свойства.

max(имя свойства)  
Максимальное занчение данного свойтсва.

sum(имя свойства)  
Сумма всех занчений данного свойтсва.

avg(имя свойства)  
Среднее арифметическое всех значений данного свойства

count(имя свойства)  
Какое количество раз данное свойство встречается в результате.

## Запросы с использованием Criteria

Hibernate поддерживает различные способы манипулирования объектами и
транслирования их в таблицы баз данных (далее – БД). Одним из таких способов
является Criteria API, который позволяет нам создавать запросы с критериями,
программным методом.

Для создания Criteria используется метод createCriteria() интерфейса Session.
Этот метод возвращает экземпляр сохряаняемого класса (persistent class) в
результате его выполнения.

Вот как это выглядит на практике:

    Criteria criteria = session.createCriteria(Developer.class);
    List developers = criteria.list();

Criteria имеет два важных метода:

**public Criteria setFirstResult(int firstResult)** - Этот метод указывает
первый ряд нашего результата, который начинается с 0.

**public Criteria setMaxResults(int maxResults)** - Этот метод ограничивает
максимальное количество оъектов, которое Hibernate сможет получить в результате
запроса.

Для примера возьмем придложение состоящее из файлов:

#### CODE:

HIBERNATE_DEVELOPERS.sql

```sql
CREATE TABLE HIBERNATE_DEVELOPERS
(
    id         INT NOT NULL auto_increment,
    FIRST_NAME VARCHAR(50) default NULL,
    LAST_NAME  VARCHAR(50) default NULL,
    SPECIALTY  VARCHAR(50) default NULL,
    EXPERIENCE INT         default NULL,
    SALARY     INT         default NULL,
    PRIMARY KEY (id)
);
```

Developer.java

```java
public class Developer {
    private int id;
    private String firstName;
    private String lastName;
    private String specialty;
    private int experience;
    private int salary;

    /**
     * Default Constructor
     */
    public Developer() {
    }

    /**
     * Plain constructor
     */
    public Developer(String firstName, String lastName, String specialty, int experience, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.experience = experience;
        this.salary = salary;
    }

    /**
     * Getters and Setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * toString method (optional)
     */
    @Override
    public String toString() {
        return "id: " + id +
                "\nFirst Name: " + firstName +
                "\nLast Name: " + lastName +
                "\nSpecialty: " + specialty +
                "\nExperience: " + experience +
                "\nSalary: " + salary + "\n";
    }
}


```

hibernate.cfg.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration SYSTEM
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <property name="hibernate.dialect">
            org.hibernate.dialect.MySQLDialect
        </property>
        <property name="hibernate.connection.driver_class">
            com.mysql.jdbc.Driver
        </property>

        <!-- Assume PROSELYTE_TUTORIAL is the database name -->
        <property name="hibernate.connection.url">
            jdbc:mysql://localhost/ИМЯ_ВАШЕЙ_БД
        </property>
        <property name="hibernate.connection.username">
            ВАШЕ_ИМЯ_ПОЛЬЗОВАТЕЛЯ
        </property>
        <property name="hibernate.connection.password">
            ВАШ_ПАРОЛЬ
        </property>

        <!-- List of XML mapping files -->
        <mapping resource="Developer.hbm.xml"/>

    </session-factory>
</hibernate-configuration>

```

Developer.hbm.xml

```java
<? xml version = "1.0"
encoding="utf-8"?>
<!
DOCTYPE hibernate-
mapping PUBLIC
        "-//Hibernate/Hibernate Mapping DTD//EN"
                "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
    <

class name="net.proselyte.hibernate.criteria.Developer"table="HIBERNATE_DEVELOPERS">
        <meta attribute="class-description">
This

class contains developer details.
        </meta>
        <id name="id"type="int"column="id">
            <generator class="native"/>
        </id>
        <property name="firstName"column="FIRST_NAME"type="string"/>
        <property name="lastName"column="LAST_NAME"type="string"/>
        <property name="specialty"column="SPECIALTY"type="string"/>
        <property name="experience"column="EXPERIENCE"type="int"/>
        <property name="salary"column="SALARY"type="int"/>
    </class>

</hibernate-mapping>

```

DeveloperRunner.java

```java
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DeveloperRunner {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        DeveloperRunner developerRunner = new DeveloperRunner();

        System.out.println("Adding developer's records to the database...");
        Integer developerId1 = developerRunner.addDeveloper("Proselyte", "Developer", "Java Developer", 3, 2000);
        Integer developerId2 = developerRunner.addDeveloper("First", "Developer", "C++ Developer", 10, 2000);
        Integer developerId3 = developerRunner.addDeveloper("Second", "Developer", "C# Developer", 5, 2000);
        Integer developerId4 = developerRunner.addDeveloper("Third", "Developer", "PHP Developer", 1, 2000);

        System.out.println("List of Developers with experience more than 3 years:");
        developerRunner.listDevelopersOverThreeYears();

        System.out.println("Total Salary of all Developers:");
        developerRunner.totalSalary();
        sessionFactory.close();
    }

    public Integer addDeveloper(String firstName, String lastName, String specialty, int experience, int salary) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Integer developerId = null;

        transaction = session.beginTransaction();
        Developer developer = new Developer(firstName, lastName, specialty, experience, salary);
        developerId = (Integer) session.save(developer);
        transaction.commit();
        session.close();
        return developerId;
    }

    public void listDevelopersOverThreeYears() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Developer.class);
        criteria.add(Restrictions.gt("experience", 3));
        List developers = criteria.list();

        for (Developer developer : developers) {
            System.out.println("=======================");
            System.out.println(developer);
            System.out.println("=======================");
        }
        transaction.commit();
        session.close();
    }

    public void totalSalary() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Developer.class);
        criteria.setProjection(Projections.sum("salary"));

        List totalSalary = criteria.list();
        System.out.println("Total salary of all developers: " + totalSalary.get(0));
        transaction.commit();
        session.close();
    }


}

```

[//]: # (SPOILER: )

[//]: # (<details> )

[//]: # (  <summary>Q1: What is the best Language in the World? </summary>)

[//]: # (   A1: JavaScript )

[//]: # (</details>)
