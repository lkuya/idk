package com.model;

public interface HasId<Datatype> {
    Datatype getId();

    void setId(Datatype value);
}
