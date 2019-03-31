package model.entity;

public class Person {

    private int id;
    private String name;
    private String gender;
    private int personId;
    private boolean isTeacher;

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
        person.append("\t\"id\": ")        .append(this.id)        .append(",\n");
        person.append("\t\"isTeacher\": ") .append(this.isTeacher) .append(",\n");
        person.append("\t\"name\": \"")    .append(this.name)      .append("\",\n");
        person.append("\t\"gender\": \"")  .append(this.gender)    .append("\",\n");
        person.append("\t\"personId\": ")  .append(this.personId)  .append("\n");
        person.append("}");

        return person.toString();
    }
}
