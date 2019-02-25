package view.scenes;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.Group;
import model.entity.Item;
import controller.interfaces.ItemUpdate;
import model.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemScene {

    private Agenda agenda;

    private ItemUpdate observer;
    private Item selected;

    private VBox main;

    private ComboBox<String> group;
    private ComboBox<LocalDateTime> schedule;
    private TextField name;
    private ComboBox<String> teacher;
    private ComboBox<Integer> startHour;
    private ComboBox<Integer> startMinute;
    private ComboBox<Integer> endHour;
    private ComboBox<Integer> endMinute;
    private ComboBox<String> classroom;

    private Label error;

    // main function
    public Scene getScene(Stage primaryStage) {

        this.main = new VBox();

        this.setInputs();
        this.setErrorLabel();
        this.setButtons();
        this.setItemData();

        Scene scene = new Scene(new ScrollPane(this.main), 345, 328);
        scene.getStylesheets().add("view/style/style.css");

        primaryStage.setTitle(getTitle());

        return scene;
    }

    // setters
    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    public void setObserver(ItemUpdate observer) {

        this.observer = observer;
    }

    public void setItem(Item selected) {

        this.selected = selected;
    }

    // scene functions
    private void setInputs() {

        // group
        this.group = new ComboBox<>();
        this.group.setItems(FXCollections.observableArrayList(this.agenda.getAllGroupNames()));
        this.group.setOnAction(e -> {
            Group group = this.agenda.getGroupByName(this.group.getValue());
            this.schedule.setItems(FXCollections.observableArrayList(group.getScheduleDates()));
        });
        Label groupLabel = new Label("Group: ");
        groupLabel.getStyleClass().add("item-label");
        HBox groupBox = new HBox();
        groupBox.getChildren().addAll(groupLabel, this.group);

        // date
        this.schedule = new ComboBox<>();
        Label dateLabel = new Label("Date: ");
        dateLabel.getStyleClass().add("item-label");
        HBox dateBox = new HBox();
        dateBox.getChildren().addAll(dateLabel, this.schedule);

        // name
        this.name = new TextField();
        Label nameLabel = new Label("Name: ");
        nameLabel.getStyleClass().add("item-label");
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, this.name);

        // teacher
        this.teacher = new ComboBox<>();
        this.teacher.setItems(FXCollections.observableArrayList(this.agenda.getAllTeacherNames()));
        Label teacherLabel = new Label("Teacher: ");
        teacherLabel.getStyleClass().add("item-label");
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
        startTimeLabel.getStyleClass().add("item-label");
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
        endTimeLabel.getStyleClass().add("item-label");
        HBox endTimeBox = new HBox();
        endTimeBox.getChildren().addAll(endTimeLabel, this.endHour, this.endMinute);

        // classroom
        this.classroom = new ComboBox<>();
        this.classroom.setItems(FXCollections.observableArrayList(this.agenda.getAllClassroomNames()));
        Label classroomLabel = new Label("Classroom: ");
        classroomLabel.getStyleClass().add("item-label");
        HBox classroomBox = new HBox();
        classroomBox.getChildren().addAll(classroomLabel, this.classroom);

        this.main.getChildren().addAll(groupBox, dateBox, nameBox, startTimeBox, endTimeBox, teacherBox, classroomBox);
        this.main.getStyleClass().addAll("main");
    }

    private void setErrorLabel() {

        this.error = new Label("");
        this.error.getStyleClass().add("error");
        this.main.getChildren().add(new HBox(this.error));
    }

    private void setButtons() {

        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("button-box");

        buttonBox.getChildren().add(this.getCancelButton());
        buttonBox.getChildren().add(this.getApplyButton());

        if (this.selected.getId() != -1)
            buttonBox.getChildren().add(this.getDeleteButton());

        this.main.getChildren().add(buttonBox);
    }

    private Button getCancelButton() {

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().addAll("button", "item-button");
        cancel.setOnMouseClicked(e -> this.observer.onItemCancel());

        return cancel;
    }

    private Button getApplyButton() {

        Button apply = new Button("Apply");
        apply.getStyleClass().addAll("button", "item-button");
        apply.setOnMouseClicked(e -> this.validateInput());

        return apply;
    }

    private Button getDeleteButton() {

        Button delete = new Button("delete");
        delete.getStyleClass().addAll("button", "item-button");
        delete.setOnMouseClicked(e -> this.observer.onItemDelete(this.selected.getId()));

        return delete;
    }

    private void validateInput() {

        boolean valid = true;

        if (this.classroom.getValue()   == null) {this.setErrorMessage("classroom");       valid = false;}
        if (this.endMinute.getValue()   == null) {this.setErrorMessage("ending minute");   valid = false;}
        if (this.endHour.getValue()     == null) {this.setErrorMessage("ending hour");     valid = false;}
        if (this.startMinute.getValue() == null) {this.setErrorMessage("starting minute"); valid = false;}
        if (this.startHour.getValue()   == null) {this.setErrorMessage("starting hour");   valid = false;}
        if (this.teacher.getValue()     == null) {this.setErrorMessage("teacher");         valid = false;}
        if (this.name.getText()      .isEmpty()) {this.setErrorMessage("name");            valid = false;}
        if (this.schedule.getValue()    == null) {this.setErrorMessage("date");            valid = false;}
        if (this.group.getValue()       == null) {this.setErrorMessage("group");           valid = false;}

        if (valid) {

            Group group = this.agenda.getGroupByName(this.group.getValue());
            Schedule schedule = group.getScheduleByDate(this.schedule.getValue());
            Item item = this.getItem();

            if (item.getStart().isAfter(item.getEnd())) {this.setCustomErrorMessage("Start time is after End time");   valid = false;}
            if (schedule.overlaps(item))                {this.setCustomErrorMessage("Time overlaps with other Items"); valid = false;}

            if (valid)
                this.observer.onItemChange(group, schedule, item);
        }
    }

    private void setItemData() {

        if (this.selected.getId() != -1) {

            Group group = this.agenda.getGroupByItem(this.selected);

            this.group.setValue(group.getName());
            this.schedule.setValue(group.getScheduleByItem(this.selected).getDate());
            this.name.setText(this.selected.getName());
            this.teacher.setValue(this.selected.getTeacher().getName());
            this.startHour.setValue(this.selected.getStart().getHour());
            this.startMinute.setValue(this.selected.getStart().getMinute());
            this.endHour.setValue(this.selected.getEnd().getHour());
            this.endMinute.setValue(this.selected.getEnd().getMinute());
            this.classroom.setValue(this.selected.getClassroom().getName());
        }
    }

    private Item getItem() {

        this.selected.setName(this.name.getText());
        this.selected.setTeacher(this.agenda.getTeacherByName(this.teacher.getValue()));
        this.selected.setStart(this.parseTime(this.startHour.getValue(), this.startMinute.getValue()));
        this.selected.setEnd(this.parseTime(this.endHour.getValue(), this.endMinute.getValue()));
        this.selected.setClassroom(this.agenda.getClassroomByName(this.classroom.getValue()));

        return this.selected;
    }

    private LocalDateTime parseTime(int hour, int minute) {

        LocalDate date = LocalDate.from(this.schedule.getValue());

        return LocalDateTime.parse(date + "T" +
            ((Integer.toString(hour).length() == 1) ? "0" + hour : hour) + ":" +
            ((Integer.toString(minute).length() == 1) ? "0" + minute : minute) + ":00"
        );
    }

    // other methods
    private String getTitle() {

        return (this.selected.getId() != -1) ? "Update: " + this.selected.getName() : "Create Schedule Item";
    }

    private ArrayList<Integer> getNumbers(int amount) {

        ArrayList<Integer> number = new ArrayList<>();

        for (int i = 0; i < amount; i++)
            number.add(i);

        return number;
    }

    private void setCustomErrorMessage(String message)  {

        this.error.setText(message);
    }

    private void setErrorMessage(String keyWord) {

        this.setCustomErrorMessage("The " + keyWord + " has not been filled in!");
    }
}