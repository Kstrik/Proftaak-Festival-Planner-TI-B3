package model.entity;

public class Classroom {

    private int id;
    private String name;

    public Classroom (int id, String name) {

        this.id = id;
        this.name = name;
    }

    public Classroom () {

    }

    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    // toString
    @Override
    public String toString() {

        return
            "{\n" +
                "\"id\": \"" + this.id + "\",\n" +
                "\"name\": \"" + this.name + "\"\n" +
            "}"
        ;
    }
}
