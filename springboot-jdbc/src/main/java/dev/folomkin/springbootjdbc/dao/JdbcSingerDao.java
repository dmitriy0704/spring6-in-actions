package dev.folomkin.springbootjdbc.dao;

import dev.folomkin.springbootjdbc.entities.Singer;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcSingerDao implements SingerDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }


    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set dataSource on SingerDao");
        }
    }


    @Override
    public String findNameById(Long id) {
        return  null;
//                jdbcTemplate.queryForObject(
//                "select first_name || ' ' || " +
//                "last_name from singer where id = ?",
//                new Object{id}, String.class
//        );
    }


    @Override
    public List<Singer> findAll() {
        return List.of();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return List.of();
    }


    @Override
    public String findLastNameById(Long id) {
        return "";
    }

    @Override
    public String findFirstNameById(Long id) {
        return "";
    }

    @Override
    public void insert(Singer singer) {

    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {

    }

    @Override
    public List<Singer> findAllWithAlbums() {
        return List.of();
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }
}
