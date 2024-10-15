package dev.folomkin.app.entities;

import jakarta.persistence.*;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "album")
public class Album implements Serializable {

    private Long id;
    private String title;
    private Date releaseDate;
    private int version;

    private Singer singer;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return this.id;
    }

    @Version
    @Column(name = "version")
    public int getVersion() {
        return this.version;
    }

    @Column
    public String getTitle() {
        return this.title;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVersion(int version) {
        this.version = version;
    }




    @Override
    public String toString() {
        return "Album - Id: " + id + ", Version: " + version
                + ", Title: " + title + ", Release Date: " + releaseDate;
    }

}
