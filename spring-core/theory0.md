# Введение в Spring

## Модули

- **Spring Core**. Контейнер компонентов Spring Beans и поддерживающие утилиты.
- **Spring Context**. Интерфейс ApplicationContext, пользовательский интерфейс,
  проверка достоверности, интерфейс JNDI, компоненты Eпterprise JavaBeaпs (EJB),
  удаленное взаимодействие и поддержка почты.
- **Spring ОАО**. Поддержка инфраструктуры обработки транзакций, интерфейса Java
  Database Coппectivity (JDBC) и объектов доступа к данным (DAO).
- **Spring ORM**. Поддержка технологий Hibernate, iBATIS и Java Data Objects (
  JDO).
- **Spring АОР**. Реализация аспектно-ориентированного программирования (АОП),
  согласованная с набором интерфейсов АОР Alliance.
- **Spring Web**. Базовые средства интеграции, включая составные функциональные
  средства, инициализацию контекста с помощью приемников сервлетов и
  неб-ориентированный контекст приложений.
- **Spring Web МVС**. Каркас для построения веб-приложений по проектному
  шаблону "модель-представление-контроллер" (Model-View-Controller - МУС).

## Инверсия управления и внедрение зависимостей

### Типы инверсии управления

- **_Поиск зависимостей_** - является намного более традиционным подходом и на  
  первый взгляд выглядит более знакомым тем, кто программирует на Java.
- **_Внедрения зависимостей_** - в действительности обеспечивает более высокую
  гибкость и удобство применения по сравнению с поиском зависимостей, хотя
  поначалу он кажется нелогичным.

Если инверсия управления реализуется как поиск зависимостей, то компонент
должен получить ссылку на зависимость, тогда как при внедрении зависимостей
последние внедряются в компонент контейнером инверсии управления.
У **_поиска зависимостей_** имеются две разновидности:

- извлечение зависимостей и
- контекстный поиск зависимостей (CDL).

И у **_внедрения зависимостей_** имеются две разновидности:

- через конструктор и
- через метод установки.

### Извлечение зависимостей

Извлечение зависимостей предоставляется и в каркасе Spring как механизм для
извлечения компонентов, которыми он управляет.

```java
public class DependencyPull {
    public static void main(String... args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext
                ("spring/app-context.xml");
        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
        mr.render();
    }
}
```

### Контекстный поиск зависимостей

В известной мере контекстный поиск зависимостей (CDL) подобен извлечению
зависимостей, но в этом случае поиск осуществляется в контейнере, управляющем
ресурсом, а не только в каком-то центральном реестре. Как правило, контекстный
поиск зависимостей производится в установленной точке.
Механизм контекстного поиска приводится в действие через реализацию в компоненте
интерфейса:

```java
public interface ManagedComponent {
    void performLookup(Container container);
}
```

Реализуя этот интерфейс, компонент извещает контейнер, что ему требуется
получить зависимость. Контейнер обычно предоставляется базовым сервером
приложений (например, Tomcat или JBoss) или каркасом (в частности, Spring).
Интерфейс Container, предоставляющий услуги поиска зависимостей:

```java
public interface Container {
    Object getDependency(String key);
}
```

Как только контейнер будет готов передать зависимости компоненту, он вызовет
метод performLookup() по очереди для каждого компонента. И тогда компонент
сможет искать свои зависимости, используя интерфейс Container:

```java
    public class ContextualizedDependencyLookup implements ManagedComponent {
    private Dependency dependency;

    @Override
    public void performLookup(Container container) {
        this.dependency = (Dependency)
                container.getDependency("myDep");
    }

    @Override
    public String toString() {
        return dependency.toString();
    }
}

```

### Внедрение зависимостей через конструктор

Внедрение зависимостей через конструктор происходит в том случае, когда
зависимости предоставляются компоненту в его конструкторе (или нескольких
конструкторах). С этой целью в компоненте объявляется один или ряд
конструкторов,
получающих в качестве аргументов его зависимости, а контейнер инверсии
управления передает зависимости компоненту при получении его экземпляра,
как показано в приведенном ниже фрагменте кода. Очевидно, что вследствие
внедрения зависимостей через конструктор объект не может быть создан без
зависимостей, а следовательно, они обязательны.

```java
    public class ConstructionInjection {
    private Dependency dependency;

    public ConstructorInjection(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public String toString() {
        return dependency.toString();
    }
}

```

### Внедрение зависимостей через метод установки

При внедрении зависимостей через метод установки контейнер инверсии управления
внедряет зависимости компонента через методы установки в стиле компонентов
JavaBeans. Методы установки компонента отражают зависимости, которыми может
управлять контейнер инверсии управления. В приведенном ниже фрагменте кода
показан типичный компонент, основанный на внедрении зависимостей через метод
установки. Очевидно, что вследствие внедрения зависимостей через метод установки
объект может быть создан без зависимостей, которые могут быть предоставлены в
дальнейшем через вызов метода установки.

```java
class SetterInjection {
    private Dependency dependency;

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    @Override
    public String toString() {
        return dependency.toString;
    }
}
```

## Инверсия управления в Spring

Ядро реализации каркаса Spring основано на внедрения зависимостей, хотя в нем
обеспечиваются также возможности для поиска зависимостей.
Каркас Spring автоматически предоставляет взаимодействующие объекты зависимому
объекту, используя внедрение зависимостей. В приложении, основанном на Spring,
всегда предпочтительнее применять внедрение зависимостей для передачи
взаимодействующих объектов зависимым объектам, а не заставлять зависимые объекты
получать взаимодействующие объекты через поиск зависимостей.

Несмотря на то что внедрение зависимостей является предпочтительным механизмом
связывания вместе взаимодействующих и зависимых объектов, для доступа к
зависимым объектам понадобится и поиск зависимостей. Во многих средах Spring не
может автоматически связать все компоненты приложения с помощью внедрения
зависимостей, поэтому для доступа к первоначальному набору компонентов придется
прибегнуть к поиску зависимостей. Например, в автономных приложениях на Java
необходимо выполнить начальную загрузку контейнера Spring в методе main() и
получить зависимости (через интерфейс ApplicationContext) для программной
обработки. Но при построении веб-приложений с поддержкой проектного шаблона
MVC в Spring этого можно избежать благодаря автоматическому связыванию всего
приложения. Пользоваться внедрением зависимостей вместе с платформой Spring
следует при всякой возможности. А в крайнем случае можно обратиться к
возможностям поиска зависимостей.

Контейнер инверсии управления в Spring примечателен тем, что он может выполнять
функцию адаптера между его собственным контейнером внедрения зависимостей и
внешними контейнерами поиска зависимостей.
В каркасе Spring поддерживается внедрение зависимостей как через конструктор,
так и через метод установки, подкрепляя стандартный набор средств инверсии
управления рядом полезных дополнений и упрощая разработку в целом.

## Внедрение зависимостей в Spring

### Компоненты Spring Beans и их фабрики

Ядром контейнера внедрения зависимостей в Spring служит интерфейс BeanFactory,
который отвечает за управление компонентами Spring Beans, в том числе их
зависимостями и жизненными циклами. Термин компонент Spring Bean употребляется
в Spring для обозначения любого компонента, управляемого контейнером.  
Если в приложении требуется лишь поддержка внедрения зависимостей, то с
контейнером внедрения зависимостей в Spring можно взаимодействовать через
интерфейс BeanFactory. В этом случае в приложении необходимо создать экземпляр
класса, реализующего интерфейс BeanFactory, и сконфигурировать его на основании
сведений о компонентах Spring Beans и зависимостях. Как только это будет
сделано, компоненты Spring Beans могут быть доступны в приложении через
интерфейс BeanFactory для последующей обработки.  
В ряде случаев вся подобного рода настройка производится автоматически (
например, в веб-приложении экземпляр типа ApplicationContext будет загружаться
веб-контейнером во время начальной загрузки приложения с помощью класса
ContextLoaderListener, предоставляемого в Spring и объявленного в дескрипторном
файле web.xml). Но зачастую программировать настройку приходится вручную.   
Несмотря на то что интерфейс BeanFactory можно сконфигурировать программно,
более распространено внешнее конфигурирование с помощью определенного рода
файла конфигурации. Внутренне конфигурация компонентов Spring Beans
представлена экземплярами классов, реализующих интерфейс BeanDefinition.
Компоненты Spring Beans можно идентифицировать в интерфейсе BeanFactory, и
каждому компоненту Spring Bean может быть назначен идентификатор, имя или то и
другое.

### Реализации интерфейса BeanFactory

...

### Интерфейс ApplicationContext

Интерфейс ApplicationContext служит расширением интерфейса BeanFactory
Spring. Помимо услуг no внедрению зависимостей, интерфейс Application
Context предоставляет такие услуги, как транзакции и АОП, источник сообщений
для интернационализации (i 18n), обработка событий в приложениях и пр.
При разработке приложений, основанных на Spring, рекомендуется взаимодействовать
с Spring через интерфейс ApplicationContext. Начальная загрузка интерфейса
ApplicationContext поддерживается в Spring посредством ручного
программирования (получения экземпляра вручную и загрузки подходящей
конфигурации) или в среде веб-контейнера через класс ContextLoaderListener.

### Конфигурирование интерфейса ApplicationContext

#### СПОСОБЫ КОНФИГУРИРОВАНИЯ ПРИЛОЖЕНИЙ SPRING

Первоначально определение компонентов Spring Beans
поддерживалось через свойства или через ХМL-файл. После выпуска версии JDK 5 и
появления в Spring, начиная с версии 2.5, поддержки аннотаций Java последние
можно также применять при конфигурировании интерфейса ApplicationContext.  
Применение ХМL-файла позволяет вынести всю конфигурацию за пределы кода Java,
тогда как аннотации дают разработчику возможность определять и видеть настройку
внедрения зависимостей в самом коде. В каркасе Spring допускается также сочетать
оба эти способа в одном интерфейсе ApplicationContext.

#### КРАТКОЕ ОПИСАНИЕ ПРОСТОЙ КОНФИГУРАЦИИ

Для конфигурирования с помощью разметки в формате XML необходимо объявить
обязательное для приложения базовое пространство имен, предоставляемое в
Spring. Ниже приведен простой пример ХМL-файла, в котором объявлено только
пространство имен beans для определения компонентов Spring Beans. В последую
щих примерах делается ссылка app-context-xml. xml на этот ХМL-файл для
конфигурирования с помощью ХМL-разметки:

```xml
<?xrnl version="l.0" encoding="UTF-8"?>
<beans xrnlns="http://www.springfrarnework.org/scherna/beans"
       xrnlns:xsi="http://www.wЗ.org/2001/XMLScherna-instance"
       xrnlns:c="http://www.springfrarnework.org/scherna/c"
       xsi:schernaLocation=
               "http://www.springfrarnework.org/scherna/beans
http://www.springfrarnework.org/scherna/beans
/spring-beans.xsd">
</beans>
```

Чтобы воспользоваться поддержкой аннотаций в Spring при разработке приложения,
необходимо объявить соответствующие дескрипторы в ХМL-файле конфигурации, как
демонстрируется в приведенном ниже примере. В последующих примерах делается
ссылка app-context-xml. xml на этот ХМL-файл для конфигурирования с помощью
ХМL-разметки и с поддержкой аннотаций.

```xml
<?xml version="l.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.wЗ.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org
/schema/context"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org
/schema/beans
http://www.springframework.org/schema/beans
/spring-beans.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context
/spring-context.xsd">
    <context:component-scan
            base-package="com.apress.prospring5.ch3.annotation"/>
</beans>
```

Дескриптор <context: component-scan> сообщает Spring о необходимости
просмотра исходного кода на предмет внедряемых компонентов Spring Beans,
снабженных аннотациями @Component, @Controller, @Repository и @Service, а
также поддерживающих аннотации @Autowired и @Inject в указанном пакете
(и всех его подчиненных пакетах). В дескрипторе <context: component-scan>
можно определить целый ряд пакетов, используя в качестве разделителя запятую,
точку с запятой или пробел. А для более точного управления в этом дескрипторе
поддерживается включение и исключение средств просмотра компонентов.

```xml
<?xml version="l.O" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.wЗ.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org
/schema/context"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org
/schema/beans
http://www.springframework.org/schema/beans
/spring-beans.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context
/spring-context.xsd">
    <context:component-scan
            base-package="com.apress.prospring5.ch3.annotation">
        <context:exclude-filter type="assignaЫe"
                                expression="com.example.NotAService"/>
    </context:component-scan>
</beans>
```

Приведенный выше дескриптор сообщает Spring о необходимости просмотра
указанного пакета, но с пропуском классов, для которых был назначен тип,
заданный в выражении (им может быть класс или интерфейс). Помимо исключающего
фильтра, можно применять и включающий фильтр. В качестве критерия фильтрации
в атрибуте type можно указать annotation, regex, assignable, aspectj или custom
(с собственным классом фильтра, реализующим интерфейс
org.springframework.core.type.filter.TypeFilter). Формат выражения в атрибуте
expression зависит от заданного типа.

### Объявление компонентов Spring

**_Объявление бинов с помощью XML_**

Разработав класс какой-нибудь службы, чтобы использовать его в приложении,
основанном на Spring, необходимо сообщить каркасу Spring, что компоненты Spring
Beans пригодны для внедрения в другие компоненты, и позволить ему управлять ими.

```java
// Интерфейс средства воспроизведения
public interface MessageRenderer {
    void render();

    void setMessageProvider(MessageProvider messageProvider);

    MessageProvider getMessageProvider;
}

// Реализация средства воспроизведения
class StandardOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    @Override
    void render() {
        if (messageProvider == null) {
            throw new RuntimeException("You must set the "
                    + "property messageProvider of class:"
                    + StandardOutMessageRenderer.class.getName());
        }
        System.out.println(messageProvider.getMessage());
    }

    @Override
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }
}

//интерфейс поставщика услуг
public interface MessageProvider {
    String getMessage();
}

// Реализация поставщика услуг
public class HelloWorldMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Hello World!";
    }
}
```

Чтобы объявить определения компонентов Spring Beans в ХМL-файле, следует
воспользоваться дескриптором <bean.. />,

```
<?xml version="l.O" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.wЗ.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org
/schema/beans
http://www.springframework.org/schema/beans
/spring-beans.xsd">
    <bean id="provider" class="`path/to/class/NameClass`"/>
    <bean id="renderer"
          class="`path/to/class/NameClass`"/>
</beans>
```

**_Объявление бинов с помощью аннотаций_**

Чтобы определить компоненты Spring Beans с помощью аннотаций, классы этих
компонентов необходимо снабдить соответствующими стереотипными аннотациями,
а методы и конструкторы - аннотацией @Autowired. Контейнер инверсии управления
уведомляется, где именно следует искать компонент Spring Bean конкретного типа,
чтобы задать его в качестве аргументов при вызове данного метода. В приведенном
ниже фрагменте кода аннотации, применяемые для определения компонента Spring
Bean, не определены. В качестве параметра стереотипных аннотаций можно задать
имя результирующего компонента Spring Bean.

```java
// Простой компонент Spring Bean

@Component("provider")
public class HelloWorldMessageProvider implements MessageProvider {
    @Override
    public String getMessage() {
        return "Hello World!";
    }
}

// Сложный компонент Spring Bean
@Service("renderer")
class StandardOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    @Override
    void render() {
        if (messageProvider == null) {
            throw new RuntimeException("You must set the "
                    + "property messageProvider of class:"
                    + StandardOutMessageRenderer.class.getName());
        }
        System.out.println(messageProvider.getMessage());
    }

    @Override
    @Autowired // -> Аннотация над методом внедрения зависимости
    public void setMessageProvider(MessageProvider provider) {
        this.messageProvider = provider;
    }

    @Override
    public MessageProvider getMessageProvider() {
        return this.messageProvider;
    }
}
```

При начальной загрузке контекста типа ApplicationContext в соответствии с
конфигурацией, приведенной ниже из ХМL-файла app-context-annotation. xml,
каркас Spring обнаружит определенные в ней компоненты и получит их экземпляры
с указанными именами.

```
<?xml version="l.O" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.wЗ.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org
/schema/context"
       xsi:schemaLocation="http://www.springframework.org
/schema/beans
http://www.springframework.org/schema/beans
/spring-beans.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context
<context:component-scan
      base-package="path/to/package_with_beans`"/ >
</beans>
```

### Конфигурирование на языке Java

Файл конфигурации app-context-xml.xml можно
заменить конфигурационным классом, не видоизменяя классы, представляющие
типы создаваемых компонентов Spring Beans. В таком случае конфигурационный класс
снабжается аннотацией @Configuration и содержит методы, объявляемые с аннотацией
@Bean и вызываемые непосредственно из контейнера инверсии управления для
получения экземпляров компонентов Spring Beans. Имя компонента Spring Bean
будет совпадать с именем метода, применяемого для его создания.

```java

@Configuration
public class HelloWorldConfiguration {

    @Bean
    public MessageProvider provider() {
        return new HelloWorldMessageProvider();
    }

    @Bean
    public MessageRenderer renderer() {
        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}
```

Для чтения конфигурации из этого класса потребуется другая реализация интерфейса
ApplicationContext:

```java
public class HelloWorldSpringAnnotated {
    public static void main(String... args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext
                (HelloWorldConfiguration.class);
        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
        mr.render();
    }
}
```

Вместо экземпляра класса DefaultListableBeanFactory в данном случае получается
экземпляр класса AnnotationConfigApplicationContext. Класс
AnnotationConfigApplicationContext реализует интерфейс Application
Context и способен выполнить его начальную загрузку из конфигураций,
определенных в классе HelloWorldConfiguration.

Конфигурационный класс может служить и для чтения определений компонентов
Spring Beans, снабженных аннотациями. В данном случае в конфигурационном классе
не потребуются методы с аннотациями @Bean, поскольку конфигурирование
определения компонента Spring Bean является составной частью его класса.
Но, для того чтобы найти определения компонентов Spring Beans в классах Java,
придется активизировать просмотр этих компонентов. Это делается в
конфигурационном классе с помощью аннотации @ComponentScanning, равнозначной
элементу разметки `<context: component-scanning../>`. Параметры этой аннотации
такие же, как и у равнозначного ей элемента ХМL-разметки.

```java

@ComponentScan(basePackages = {"path/to/search_beans"})
@Configuration
public class HelloWorldConfiguration {
}
```

### Конфигурирование внедрения зависимостей через метод установки

Для конфигурирования внедрения зависимостей через метод установки с помощью
разметки в формате XML необходимо ввести дескрипторы <property> в дескриптор
<bean> для каждого свойства, в котором должна быть внедрена зависимость.
Например, чтобы присвоить компонент, реализующий поставщика сообщений
(provider), свойству messageProvider компонента, реализующего средство
воспроизведения (renderer), достаточно внести следующие изменения в дескриптор
<bean> разметки этого компонента:

```xml

<beans>
    <bean id="renderer"
          class="com.apress.prospring5.ch2.decoupled
.StandardOutMessageRenderer">
        <property name="messageProvider" ref="provider"/>
    </bean>
    <bean id="provider"
          class="com.apress.prospring5.ch2.decoupled
.HelloWorldМessageProvider"/>
</beans>
```

В приведенном выше коде разметки компонент provider присваивается свойству
messageProvider.

В объявление метода установки достаточно ввести аннотацию @Autowired, как
показано в следующем фрагменте кода:

```java

@Service("render")
public class StandardOutMessageRenderer implements MessageRenderer {
    private MessageProvider messageProvider;

    // ...	
    @Override
    @Autowired
    public void setMessageProvider(MessageProvider provider) {
        System.out.println(" --> StandardOutMessageRenderer: setting the provider");
        this.messageProvider = provider;
    }
}
```

При инициализации контекста типа ApplicationContext каркас Spring обнаружит
подобные аннотации @Autowired и внедрит зависимость по мере необходимости,
поскольку в ХМL-файле конфигурации объявлен дескриптор
`<context:component-scan>`.

### Конфигурирование внедрения зависимостей через конструктор

В файле конфигурации
Spring можно легко создать конфигурируемую реализацию интерфейса Message
Provider, которая позволит определять сообщение внешним образом, как демон­
стрируется в следующем фрагменте кода:

```java
class MessageProviderImpl implements MessageProvider {
    private String message;

    public MessageProviderImpl(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
```

Получение экземпляра класса с помощью конфигурации.

```xml

<beans xтlns="http://www.springfraтework.org/scheтa/beans"
       xтlns:xsi="http://www.wЗ.org/2001/XMLScheтa-instance"
       xsi:scheтaLocation=
               "http://www.springfraтework.org/scheтa/beans
http://www.springfraтework.org/scheтa/beans
/spring-beans.xsd">
    <bean id="тessageProvider"
          class="coт.apress.prospring5.ch3.xтl.ConfiguraЬleMessageProvider">
        <constructor-arg value=
                                 "I hope that soтeone gets ту тessage in а bottle"/>
    </bean>
</beans>
```

Для внедрения зависимостей через конструктор в объявлении метода-конструктора
целевого компонента Spring Bean применяется также аннотация `@Autowired`. Это
другой вариант по сравнению с внедрением зависимостей через метод установки.

```java

@Service("provider")
public class ConfigurableMessageProvider implements MessageProvider {
    private String message;

    @Autowired
    public ConfigurableMessageProvider(
            @Value("Configurable message") String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
```

Аннотация @Value - определяется значение, внедряемое в конструктор. Подобным
способом значения внедряются в компоненты Spring Beans.  
Значения, (сообщение передаваемое в @Value), предназначенные для внедрения,
рекомендуется все же выносить за пределы прикладного кода. Чтобы вынести
сообщение за пределы прикладного кода, оно определяется как компонент Spring
Bean в файле конфигурации с аннотациями.

```xml

<beans ... >
<context:component-scan base-package="com.apress.prospring5.ch3.annotated"/>
<bean id="message" class="java.lang.String"
      с: O="I hope that someone gets my message in а bottle"/>
<bean id="message2" class="java.lang.String"
      с: O="I know I won't Ье injected : ("/>
        </beans>
```

Если в классе два конструктора, в конфигурации
уточняется какой тип аргумента передавать.

```java

@Service("constructorConfusion")
public class ConstructorConfusion {
    private String someValue;

    public ConstructorConfusion(String someValue) {
        System.out.println("ConstructorConfusion(String) called");
        this.someValue = someValue;
    }

    ublic ConstructorConfusion(int someValue) {
        System.out.println("ConstructorConfusion(int) called");
        this.someValue = "Number: " + Integer.toString(someValue);
    }

    public String toString() {
        return someValue;
    }

    public static void main(String... args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring/app-context-annotation.xml");
        ctx.refresh();

        ConstructorConfusion cc = (ConstructorConfusion) ctx.getBean("constructorConfusion");
        System.out.println(cc);
        ctx.close();
    }
}
```

```xml

<beans ... >
<context:component-scan base-package="com.apress.prospring5.ch3.annotated"/>
<bean id="message" class="java.lang.String"
      с: O="I hope that someone gets my message in а bottle"/>
<bean id="message2" class="java.lang.String"
      с: O="I know I won't Ье injected : ("/>
<construtor-arg type="int">
<value>90</value>
</construtor-arg>
        </beans>
```

Или

```java

@Service("constructorConfusion")
public class ConstructorConfusion {
    private String someValue;

    public ConstructorConfusion(String someValue) {
        System.out.println("ConstructorConfusion(String) called");
        this.someValue = someValue;
    }

    @Autowired
    public ConstructorConfusion(@Value("90") int someValue) {
        System.out.println("ConstructorConfusion(int) called");
        this.someValue = "Number: " + Integer.toString(someValue);
    }

    public String toString() {
        return someValue;
    }

    public static void main(String... args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:spring/app-context-annotation.xml");
        ctx.refresh();

        ConstructorConfusion cc = (ConstructorConfusion) ctx.getBean("constructorConfusion");
        System.out.println(cc);
        ctx.close();
    }
}
```

### Конфигурирование внедрения зависимостей через поле

Третья разновидность внедрения зависимостей, поддерживаемая в Spring, называется
внедрением зависимостей через поле. Как подразумевает само название, зависимость
внедряется непосредственно в поле, и для этой цели не требуется ни конструктор,
ни метод установки, достаточно лишь снабдить аннотацией @Autowired
соответствующее поле, являющееся членом класса. Такое внедрение зависимостей
оказывается вполне практичным, поскольку разработчик избавляется от
необходимости писать код, который может не понадобиться после первоначального
создания компонента Spring Bean, если зависимость от объекта больше не нужна за
его пределами.

```java

@Component
public class Inspiration {
    private String lyric = "I can....."

    public Inspiration(@Value("For all....") String lyric) {
        this.lyric = lyric;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}

@Service("singer")
public class Singer {


    @Autowired
    private Inspiration inspirationBean;

    public void sing() {
        System.out.println("..." + inpspirationBean.getSync);
    }
}

//  <beans ... >
//      <context:cornponeпt-scan
//          base-package="corn.apress.prospring5.ch3.aппotated"/>
//  </bеапs>
```

### Автоматическое обнаружение и связывание

Контейнер Spring может автоматически устанавливать отношения между
взаимодействующими бинами. Можно дать Spring автоматически выполнить
разрешение взаимодействующих объектов (других бинов) для бина, просматривая
содержимое ApplicationContext. Автоматическое обнаружение и
связывание имеет следующие преимущества:

- Автоматическое обнаружение и связывание позволяет значительно сократить
  количество инструкций для указания свойств или аргументов конструктора (Другие
  механизмы, такие как шаблон бина, рассматриваемый в других разделах этой
  главы, также полезны в этом отношении.)

- Автоматическое обнаружение и связывание позволяет обновлять конфигурацию по
  мере развития объектов. Например, если нужно добавить зависимость к классу,
  эта зависимость может быть удовлетворена автоматически, без необходимости
  изменять конфигурацию. Таким образом, автоматическое обнаружение и связывание
  может быть особенно полезно во время разработки, что не отменяет возможность
  перехода к явному связыванию, когда кодовая база станет стабильной.

При использовании конфигурационных метаданных на основе XML можно задать
режим автоматического связывания в определении бина с помощью атрибута autowire
элемента <bean/>. Функциональность автоматического обнаружения и связывания
имеет четыре режима:

- **no** - (По умолчанию) Без автоматического обнаружения и связывания. Ссылки
  на бины должны быть определены элементами ref. Изменение параметра по
  умолчанию не рекомендуется для больших развертываний, поскольку явное указание
  взаимодействующих объектов обеспечивает больший контроль и ясность. В
  некоторой степени он документирует структуру системы.
- **byName** - Автоматическое обнаружение и связывание по имени свойства. Spring
  ищет бин с тем же именем, что и свойство, которое должно быть автоматически
  обнаружено и связано. Например, если в определении бина содержится инструкция
  на автоматическое обнаружение и связывание по имени и свойство master (то есть
  имеет метод setMaster(..)), Spring ищет определение бина с именем master и
  использует его для задания свойства.
- **byType** - Позволяет свойству быть автоматически обнаруженным и связанным,
  если в контейнере существует ровно один бин с данным типом свойства. Если
  существует более одного, то генерируется критическое исключение, которое
  указывает, что нельзя использовать режим автоматического обнаружения и
  связывания byType для этого бина. Если подходящих бинов нет, ничего не
  произойдет, свойство не будет задано.
- **constructor** - Аналогично byType, но применяется к аргументам конструктора.
  Если в контейнере нет ровно одного бина с типом аргумента конструктора, то
  возникает критическая ошибка.
- **default** - В этом режиме каркас Spring автоматически делает выбор
  между режимами constructor и ЬуТуре. Если у компонента Spring Bean имеется
  конструктор по умолчанию (т.е. без аргументов), то в Spring выбирается
  режим автосвязывания byType, а иначе - режим constructor.


