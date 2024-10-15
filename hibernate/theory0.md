# Hibernate

## Конфигурация подключения для встроенной базы H2.

```java
package dev.folomkin.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "dev.folomkin.app")
@EnableTransactionManagement
public class AppConfig {

    private static final Logger logger =
            LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public DataSource dataSource() {
        try {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScripts("classpath:sql/schema.sql",
                            "classpath:sql/test-data.sql")
                    .build();
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            return null;
        }
    }


    private Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
        return hibernateProp;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("dev.folomkin.app.entities");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        return new HibernateTransactionManager(sessionFactory());
    }
}



```

### DataSource dataSource()

Настройка подключения. Для удаленного сервера:

```java
    public DataSource dataSource() {
    try {
        SimpleDriverDataSource dataSource =
                new SimpleDriverDataSource();
        Class<? extends Driver> driver =
                (Class<? extends Driver>)
                        Class.forName(driverClassName);
        dataSource.setDriverClass(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    } catch (Exception exception) {
        return null;
    }
}
```

### Компоненты в коде

- **transactionManager** - Для транзакционного доступа к данным в фабрике
  сеансов Hibernate требуется
  диспетчер транзакций. В каркасе Spring предоставляется диспетчер транзакций
  специально для версии Hibernate 5
  `(org.springframework.orm.hibernateS.HibernateTransactionManager)`.
  Этот компонент объявлен с присвоенным ему идентификатором transactionManager.
  Всякий раз, когда потребуется диспетчер транзакций, каркас Spring будет по
  умолчанию искать в контексте типа ApplicationContext компонент
  transactionManager. Кроме того, в дескрипторе <tx: annotation-driven>
  объявляется поддержка требований к установлению границ транзакций с помощью
  аннотации. Эквивалентное конфигурирование на языке Java осуществляется с
  помощью аннотации`@EnableTransactionManagement.`
- **Просмотр компонентов** - Аннотация @ComponentScan предписывает каркасу
  Spring просмотреть компоненты в пакете проекта `dev.folomkin.app`, чтобы
  обнаружить в нем те компоненты Spring Beans, которые снабжены аннотацией
  `@Repository`.
- **Компонент sessionFactory** - В этом компоненте определен ряд важных свойств.
  Во-первых, необходимо внедрить компонент источника данных в фабрику сеансов.
  Во-вторых, библиотеке Hibernate предписывается просмотреть объекты
  предметной области в пакете `dev.folomkin.app.entities`. И, в-третьих,
  свойство hibernateProperties предоставляет подробности конфигурирования
  Hibernate. Имеется немало конфигурационных параметров, но в данном случае
  определяются лишь несколько важных свойств, которые должны предоставляться
  для каждого приложения.

### Свойства Hibernate

- **hibernate.dialect** - Обозначает диалект базы данных для обработки запросов,
  который должен использоваться в Hibernate. В библиотеке Hibernate
  поддерживаются диалекты SQL для многих баз данных. Эти диалекты являются
  подклассами, производными от класса org.hibernate.dialect.Dialect. К числу
  основных диалектов Hibernate относятся H2Dialect, Oracle109Dialect,
  PostgreSQLDialect, MySQLDialect, SQLServerDialect и т.д.
- **hibernate.max_fetch_depth** - Объявляет "глубину" для внешних соединений,
  когда одни преобразующие объекты связаны с другими преобразуемыми объектами.
  Позволяет предотвратить выборку средствами Hibernate слишком большого объема
  данных при наличии многих вложенных ассоциаций. Обычно принимает значение З.
- **hibernate.jdbc.fetch_size** - Обозначает количество записей из базового
  результирующего набора JDBC типа ResultSet, который должен использоваться в
  Hibernate для извлечения записей из базы данных в каждой выборке. Допустим,
  что по запросу, направленному базе данных, получен результирующий набор из 500
  записей. Если размер выборки равен 50, то для получения всех требующихся
  данных библиотеке Hibernate придется произвести 1О выборок
- **hibernate.jdbc.batch_size** - Указывает библиотеке Hibernate количество
  операций обновления, которые должны быть сгруппированы в пакет. Это очень
  удобно для выполнения пакетных заданий в Hibernate. Очевидно, что когда
  выполняется пакетное задание, обновляющее сотни записей, было бы желательно,
  чтобы библиотека Hibernate сгруппировала запросы в пакеты, а не отправляла
  запросы на обновление по одному
- **hibernate.show_sql** - Указывает, должна ли библиотека Hibernate направлять
  запросы SQL в файл регистрации или на консоль. Этот режим имеет смысл включать
  в среде разработки, потому что он оказывает помощь в тестировании и устранении
  ошибок
- **hibernate.format_sql** - Указывает, следует ли форматировать вывод запросов
  SQL на консоль или файл регистрации
- **hibernate.use_sql_comments** - Если в этом свойстве установлено логическое
  значение true, библиотека Hibernate сформирует комментарии в запросах SQL,
  чтобы упростить их отладку

## Объектно-реляционное преобразование с помощью аннотаций Hibernate

### Простые преобразования

**Простые преобразования класса в класс сущности:**
в класс POJO добавляются аннотации.

```java

@Entity  //-> Преобразуемый класс сущности
@Table(name = "singer") //-> оnределяет имя таблицы в базе данных, 
// в которую nреобразуется эта сущность.
public class Singer implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int version;

    @Id //-> является первичным ключом объекта.
    @GeneratedValue(strategy = IDENTITY)
    //-> сообщает библиотеке Hibemate, каким 
    // образом было сгенерировано значение идентификатора id. Значение IDENТITY атрибута 
    // strategy в этой аннотации означает, что  идентификатор сгенерирован СУРБД во 
    // время вставки данных.
    @Column(name = "id") //-> Имя столбца
    public Long getId() {
        return id;
    }

    @Version // -> Версия объекта.  Всякий раз, когда библиотека Hibernate 
    // обновляет запись, она сравнивает 
    //  версию экземпляра сущности с версией записи в базе данных.
    //  Если версии совпадают, значит, данные раньше не обновлялись, и поэтому
    //  Hibernate обновит данные и увеличит значение в столбце версии. Но если версии
    //  отличаются, то это означает, что кто-то уже обновил запись, и тогда Hibernate
    //  сгенерирует исключение типа StaleObjectStateException, которое Spring
    //  преобразует в исключение типа HibernateOptimisticLockingFailureException.
    @Column(name = "version")
    public int getVersion() {
        return version;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @Temporal(TemporalType.DATE)
    //-> тиn данных Java (java.util.Date) желательно 
    // nреобразовать в тиn данных SQL (java.sql.Date). Это дает возможность получить 
    // в приложении доступ к свойству birthDate из объекта тиnа Singer, используя, 
    // как обычно, тип данных java.util.Date.

    @Column(name = "birth_date")
    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Singer{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", birthDate=" + birthDate + ", version=" + version + '}';
    }
}
```

**Типы стратегии генерации id:**

- **AUTO**. Это означает, что JPA провайдер решает, как генерировать уникальные
  ID для нашей сущности. 
- **IDENTITY** - используется встроенный в БД тип данных столбца - identity - для
  генерации значения первичного ключа.
- **SEQUENCE** - используется последовательность – специальный объект БД для
  генерации уникальных значений.
- **TABLE** - для генерации уникального значения используется отдельная таблица,
  которая эмулирует последовательность. Когда требуется новое значение, JPA
  провайдер блокирует строку таблицы, обновляет хранящееся там значение и
  возвращает его обратно в приложение. Эта стратегия – наихудшая по
  производительности и ее желательно избегать.

**@Version**

Атрибут version снабжен аннотацией @Version. Тем самым библиотеке
Hibernate сообщается, что в данном случае требуется применить механизм 
оптимистичной блокировки, а для его управления - атрибут version. 
Всякий раз, когда библиотека Hibernate обновляет запись, она сравнивает 
версию экземпляра сущности с версией записи в базе данных. 
Если версии совпадают, значит, данные раньше не обновлялись, и поэтому 
Hibernate обновит данные и увеличит значение в столбце версии. Но если версии 
отличаются, то это означает, что кто-то уже обновил запись, и тогда Hibernate 
сгенерирует исключение типа StaleObjectStateException, которое Spring 
преобразует в исключение типа HibernateOptimisticLockingFailureException.
В данном примере для контроля версий используется целочисленное значение. Помимо
целых чисел, в Hibernate поддерживаются отметки времени. Тем не менее для
контроля версий рекомендуется применять именно целочисленное значение,
поскольку в этом случае Hibernate будет всегда увеличивать номер версии на 1
после каждого обновления. Когда же используется отметка времени, после
каждого обновления Hibernate будет заменять значение этой отметки текущим
показанием времени. Отметки времени менее безопасны, поскольку две параллельные 
транзакции могут загрузить и обновить один и тот же элемент данных
практически одновременно в пределах миллисекунды.


## Связи сущностей
### Один ко многим

Пример связи "один ко многим" для сущностей Singer и Album. У одного певца 
может быть несколько альбомов.

```java
@Entity
@Table(name = "singer")
public class Singer implements Serializable {

    // Поля....
  
    private Set<Album> albums = new HashSet<>();
    
    @OneToMany(mappedBy = "singer", 
            cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Album> getAlbums() {
        return albums;
    }
    
    public boolean addAlbum(Album album) {
        album.setSinger(this);
        return getAlbums().add(album);
    }
    
    // Getters and Setters, toString()
}


@Entity
@Table(name = "album")
public class Album implements Serializable {

    // поля...
  
    private Singer singer;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

  // Getters and Setters, toString()
}

```

### Многие ко многим

Для связей "многие ко многим" требуется промежуточная таблица, через которую 
осуществляется соединение.

```java
@Entity
@Table(name = "singer")
public class Singer implements Serializable {

    // ...
    private Set<Album> albums = new HashSet<>();
    private Set<Instrument> instruments = new HashSet<>();

    // -> Один певец играет на нескольких инструментах или инструмент
    // принадлежит нескольким певцам
    @ManyToMany
    @JoinTable(name = "singer_instrument",
            joinColumns = @JoinColumn(name = "singer_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument_id"))
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = new HashSet<>();
    }

    // ...
}

```

Метод получения свойства instruments из класса
Singer снабжен аннотацией @ManyToMany. В данном коде предоставляется аннотация 
@JoinTable для указания промежуточной таблицы для соединения, которую
должна искать библиотека Hibernate. 
В атрибуте "name" задается имя промежуточной
таблицы для соединения, в атрибуте joinColumns определяется столбец с внешним
ключом для таблицы SINGER, а в атрибуте inverseJoinColurnns указывается стол­
бец с внешним ключом для таблицы INSTRUMENT на другой стороне устанавливае­
мой связи. Ниже nриведен исходный код класса
Instrurnent, где доnолнительно
реализована другая сторона рассматриваемого здесь отношения. Данное nреобразо­
вание связей наnоминает nриведенное ранее nреобразование для класса Singer, но
здесь атрибуты j oinColurnns и inverseJoinColumns nоменялись местами, отра­
жая отношение "многие ко многим".