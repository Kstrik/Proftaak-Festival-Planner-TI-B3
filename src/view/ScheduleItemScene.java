package view;

import controller.ScheduleItemUpdate;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.ScheduleItem;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScheduleItemScene {

    private Agenda agenda;

    private ScheduleItemUpdate observer;
    private ScheduleItem scheduleItem;

    private VBox main;

    private ComboBox<String> group;
    private DatePicker date;
    private TextField name;
    private ComboBox<String> teacher;
    private ComboBox<Integer> startHour, startMinute;
    private ComboBox<Integer> endHour,   endMinute;
    private ComboBox<String> classroom;

    // main function
    public Scene getScene(Stage primaryStage) {

        setScheduleItem();

        Scene scene = new Scene(new ScrollPane(this.main), 295, 292);
        scene.getStylesheets().add("view/style/style.css");

        primaryStage.setTitle(getTitle());

        return scene;
    }

    // setters
    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    public void setObserver(ScheduleItemUpdate observer) {

        this.observer = observer;
    }

    public void setScheduleItem(ScheduleItem scheduleItem) {

        this.scheduleItem = scheduleItem;
    }

    // scene functions
    private void setScheduleItem() {

        this.main = new VBox();

        this.setInputs();
        this.setButtons();
        this.setScheduleItemData();
    }

    private void setInputs() {

        // group
        this.group = new ComboBox<>();
        this.group.setItems(FXCollections.observableArrayList(this.agenda.getAllGroupNames()));

        Label groupLabel = new Label("Group: ");
        groupLabel.getStyleClass().add("scheduleItem-label");
        HBox groupBox = new HBox();
        groupBox.getChildren().addAll(groupLabel, this.group);

        // date
        this.date = new DatePicker();

        Label dateLabel = new Label("Date: ");
        dateLabel.getStyleClass().add("scheduleItem-label");
        HBox dateBox = new HBox();
        dateBox.getChildren().addAll(dateLabel, this.date);

        // name
        this.name = new TextField("Test");

        Label nameLabel = new Label("Name: ");
        nameLabel.getStyleClass().add("scheduleItem-label");
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, this.name);

        // teacher
        this.teacher = new ComboBox<>();
        this.teacher.setItems(FXCollections.observableArrayList(this.agenda.getAllTeacherNames()));

        Label teacherLabel = new Label("Teacher: ");
        teacherLabel.getStyleClass().add("scheduleItem-label");
        HBox teacherBox = new HBox();
        teacherBox.getChildren().addAll(teacherLabel, this.teacher);

        // start time
        this.startHour = new ComboBox<>();
        this.startHour.getStyleClass().add("time-comboBox");
        this.startHour.setItems(FXCollections.observableArrayList(this.getNumbers(24)));

        this.startMinute = new ComboBox<>();
        this.startMinute.getStyleClass().add("time-comboBox");
        this.startMinute.setItems(FXCollections.observableArrayList(this.getNumbers(60)));

        Label startTimeLabel = new Label("Start time: ");
        startTimeLabel.getStyleClass().add("scheduleItem-label");
        HBox startTimeBox = new HBox();
        startTimeBox.getChildren().addAll(startTimeLabel, this.startHour, this.startMinute);

        // end time
        this.endHour = new ComboBox<>();
        this.endHour.getStyleClass().add("time-comboBox");
        this.endHour.setItems(FXCollections.observableArrayList(this.getNumbers(24)));

        this.endMinute = new ComboBox<>();
        this.endMinute.getStyleClass().add("time-comboBox");
        this.endMinute.setItems(FXCollections.observableArrayList(this.getNumbers(60)));

        Label endTimeLabel = new Label("Start time: ");
        endTimeLabel.getStyleClass().add("scheduleItem-label");
        HBox endTimeBox = new HBox();
        endTimeBox.getChildren().addAll(endTimeLabel, this.endHour, this.endMinute);

        // classroom
        this.classroom = new ComboBox<>();
        this.classroom.setItems(FXCollections.observableArrayList(this.agenda.getAllClassroomNames()));

        Label classroomLabel = new Label("Classroom: ");
        classroomLabel.getStyleClass().add("scheduleItem-label");
        HBox classroomBox = new HBox();
        classroomBox.getChildren().addAll(classroomLabel, this.classroom);

        this.main.getChildren().addAll(groupBox, dateBox, nameBox, startTimeBox, endTimeBox, teacherBox, classroomBox);
        this.main.getStyleClass().addAll("main");
    }

    private void setButtons() {

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button");
        cancel.setOnMouseClicked(e -> this.observer.onScheduleItemCancel());

        Button apply = new Button("Apply");
        apply.getStyleClass().add("button");
        apply.setOnMouseClicked(e -> this.observer.onScheduleItemChange(

            this.agenda.getGroupByName(this.group.getValue()),
            this.parseTime(0, 0),
            this.getScheduleItem()
        ));

        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("button-box");

        if (this.scheduleItem != null) {

            Button delete = new Button("delete");
            delete.getStyleClass().add("button");
            delete.setOnMouseClicked(e -> this.observer.onScheduleItemDelete(this.getScheduleItem()));

            buttonBox.getChildren().addAll(cancel, delete, apply);
        } else {

            buttonBox.getChildren().addAll(cancel, apply);
        }

        this.main.getChildren().add(buttonBox);
    }

    private void setScheduleItemData() {

        if (this.scheduleItem != null) {

            this.group.setValue(this.agenda.getGroupNameOfScheduleItem(scheduleItem));
            this.date.setValue(LocalDate.from(this.agenda.getDateOfScheduleItem(scheduleItem)));
            this.name.setText(this.scheduleItem.getName());
            this.teacher.setValue(this.scheduleItem.getTeacher().getName());
            this.startHour.setValue(this.scheduleItem.getStart().getHour());
            this.startMinute.setValue(this.scheduleItem.getStart().getMinute());
            this.endHour.setValue(this.scheduleItem.getEnd().getHour());
            this.endMinute.setValue(this.scheduleItem.getEnd().getMinute());
            this.classroom.setValue(this.scheduleItem.getClassroom().getName());
        }
    }

    private ScheduleItem getScheduleItem() {

        ScheduleItem scheduleItem = new ScheduleItem();

        scheduleItem.setId((this.scheduleItem != null) ? this.scheduleItem.getId() : -1);
        scheduleItem.setName(this.name.getText());
        scheduleItem.setTeacher(this.agenda.getTeacherByName(this.teacher.getValue()));
        scheduleItem.setStart(this.parseTime(this.startHour.getValue(), this.startMinute.getValue()));
        scheduleItem.setEnd(this.parseTime(this.endHour.getValue(), this.endMinute.getValue()));
        scheduleItem.setClassroom(this.agenda.getClassroomByName(this.classroom.getValue()));

        return scheduleItem;
    }

    private LocalDateTime parseTime(int hour, int minute) {

        LocalDate date = this.date.getValue();

        return LocalDateTime.parse(date + "T" +
                ((Integer.toString(hour).length() == 1) ? "0" + hour : hour) + ":" +
                ((Integer.toString(minute).length() == 1) ? "0" + minute : minute) + ":00"
        );
    }

    // other methods
    private String getTitle() {

        return (this.scheduleItem != null) ? "Update: " + this.scheduleItem.getName() : "Create Schedule Item";
    }

    private ArrayList<Integer> getNumbers(int amount) {

        ArrayList<Integer> number = new ArrayList<>();

        for (int i = 0; i < amount; i++)
            number.add(i);

        return number;
    }
}