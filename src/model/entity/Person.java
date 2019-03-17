package model.entity;

public class Person {

    private int id;
    private String name;
    private String gender;
    private int personId;
    private boolean isTeacher;

    public Person(int id, String name, String gender, int personId, boolean isTeacher) {

        this.id = id;
        this.name = name;
        this.gender = gender;
        this.personId = personId;
        this.isTeacher = isTeacher;
    }

    public Person() {

        this.id = -1;
        this.name = "";
        this.gender = "other";
        this.personId = -1;
        this.isTeacher = false;
    }

    // getters
    public int getId() {

        return this.id;
    }

    public int getPersonId() {

        return this.personId;
    }

    public String getName() {

        return this.name;
    }

    public String getGender() {

        return this.gender;
    }

    public boolean isTeacher() {

        return isTeacher;
    }

    // setters
    public void setId(int id) {

        this.id= id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setGender(String gender) {

        this.gender = gender;
    }

    public void setMemberId(int personId) {

        this.personId = personId;
    }

    public void setIsTeacher(boolean isTeacher) {

        this.isTeacher = isTeacher;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder person = new StringBuilder();

        person.append("{\n");
        person.append("\"id\": \"")        .append(this.id)        .append("\",\n");
        person.append("\"isTeacher\": \"") .append(this.isTeacher) .append("\",\n");
        person.append("\"name\": \"")      .append(this.name)      .append("\",\n");
        person.append("\"gender\": \"")    .append(this.gender)    .append("\",\n");
        person.append("\"personId\": \"")  .append(this.personId)  .append("\"\n");
        person.append("}");

        return person.toString();
    }
}
