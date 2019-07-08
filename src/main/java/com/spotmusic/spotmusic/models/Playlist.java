package com.spotmusic.spotmusic.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    private String name;
    private String description;

    @OneToMany
    private List<Music> music;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
