package model.entity;

public class Person {

    private int id;
    private String name;
    private String gender;
    private long personID;
    private boolean isTeacher;

    public Person(int id, String name, String gender, int personID, boolean isTeacher) {

        this.id = id;
        this.name = name;
        this.gender = gender;
        this.personID = personID;
        this.isTeacher = isTeacher;
    }

    public Person() {

    }

    // getters
    public int getId() {

        return this.id;
    }

    public long getPersonID() {

        return this.personID;
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

    public void setMemberID(long personID) {

        this.personID = personID;
    }

    public void setIsTeacher(boolean isTeacher) {

        this.isTeacher = isTeacher;
    }

    // toString
    @Override
    public String toString() {

        return
            "{\n" +
                "\"id\": \"" + this.id + "\",\n" +
                "\"isTeacher\": \"" + this.isTeacher + "\",\n" +
                "\"name\": \"" + this.name + "\",\n" +
                "\"gender\": \"" + this.gender + "\",\n" +
                "\"personID\": \"" + this.personID + "\"\n" +
            "}"
        ;
    }
}
