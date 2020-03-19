package model;

import java.io.Serializable;

public class Proba implements HasId<Integer>, Serializable {
    private Integer id;
    private String nume;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Proba(Integer id, String nume) {
        this.id = id;
        this.nume = nume;
    }
}
