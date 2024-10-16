package dev.folomkin.app.test_app;


import dev.folomkin.app.config.AppConfig;
import dev.folomkin.app.dao.SingerDao;
import dev.folomkin.app.entities.Album;
import dev.folomkin.app.entities.Instrument;
import dev.folomkin.app.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;
@EnableAutoConfiguration
public class SpringHibernateDemo {
    private static Logger logger = LoggerFactory.getLogger(SpringHibernateDemo.class);

    public static void main(String... args) {
        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
        Singer singer = singerDao.findById(2l);
        singerDao.delete(singer);

        logger.info(singer.toString());

        listSingers(singerDao.findAllWithAlbum());
        ctx.close();
    }

    private static void listSingers(List<Singer> singers) {
        logger.info(" ---- Listing singers with instruments:");
        for (Singer singer : singers) {
            logger.info(singer.toString());
        }
    }
}
