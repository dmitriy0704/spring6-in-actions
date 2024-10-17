# Hibernate
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
