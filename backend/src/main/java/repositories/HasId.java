package repositories;

public interface HasId<Datatype> {
    Datatype getId();

    void setId(Datatype value);
}
