import agenda.Group;
import agenda.Person;
import agenda.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    static private ArrayList<Person> studentList;
    static private ArrayList<Person> teacherList;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        studentList = new ArrayList<>();
        studentList.add(new Student("Luuk Wauben", "Male", 128256));
        studentList.add(new Student("Daan Makkerbach", "Male", 128257));
        studentList.add(new Student("Daphne Zooi", "Female", 128258));
        studentList.add(new Student("Ad van het land", "Male", 128259));

        Group studentGroup = new Group(studentList);
        Student requestUser = (Student) studentGroup.getPerson(0);
        System.out.println(requestUser.getName());
//        launch(args);
    }
}
