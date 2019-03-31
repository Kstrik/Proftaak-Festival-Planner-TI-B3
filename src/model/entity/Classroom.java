package model.entity;

public class Classroom {

    private int id;
    private String name;

    public Classroom () {

        this.id = -1;
        this.name = "";
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    // getters
    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder classroom = new StringBuilder();

        classroom.append("{\n");
        classroom.append("\t\"id\": ")     .append(this.id)   .append(",\n");
        classroom.append("\t\"name\": \"") .append(this.name) .append("\"\n");
        classroom.append("}");

        return classroom.toString();
    }
}
