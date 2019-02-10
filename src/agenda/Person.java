package agenda;

public abstract class Person {
    String name = "";
    String gender = "";

    Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public abstract String getName();
}
