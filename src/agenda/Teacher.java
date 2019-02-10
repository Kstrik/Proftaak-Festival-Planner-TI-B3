package agenda;

public class Teacher extends Person {
    public Teacher(String name, String gender, int studentID) {
        super(name, gender);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
