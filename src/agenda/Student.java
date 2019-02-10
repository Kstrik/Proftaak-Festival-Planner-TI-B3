package agenda;

public class Student extends Person {
    private int studentID;
    public Student(String name, String gender, int studentID) {
        super(name, gender);

        this.studentID = studentID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getStudentID() {
        return this.studentID;
    }
}
