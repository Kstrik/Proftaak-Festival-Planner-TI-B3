import agenda.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.Date;
import java.util.ArrayList;

public class Main extends Application {

    static private ArrayList<Person> studentList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        dummyStudentList();

        Group studentGroup = new Group(studentList);

        agenda.createSchedule(new Date(), dummyScheduleItems());
        Schedule fetchedSchedule = agenda.getSchedule(0);

        fetchedSchedule.setDate(new Date());
        fetchedSchedule.addScheduleItem(new ScheduleItem());

        System.out.println(fetchedSchedule.getDate());
    }

    //TODO: remove once everything is tested
    private static void dummyStudentList() {
        studentList.add(new Student("Luuk Wauben", "Male", 128256));
        studentList.add(new Student("Daan Makkerbach", "Male", 128257));
        studentList.add(new Student("Daphne Zooi", "Female", 128258));
        studentList.add(new Student("Ad van het land", "Male", 128259));
    }
    private static ArrayList<ScheduleItem> dummyScheduleItems() {
        ArrayList<ScheduleItem> dummy = new ArrayList<>();
        dummy.add(new ScheduleItem());
        dummy.add(new ScheduleItem());
        dummy.add(new ScheduleItem());
        dummy.add(new ScheduleItem());

        return dummy;
    }
}
