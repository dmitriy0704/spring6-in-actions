package dev.folomkin.app;

import dev.folomkin.app.test_app.SpringHibernateDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
//        SpringHibernateDemo demo = new SpringHibernateDemo();
    }

}
