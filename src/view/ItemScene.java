package view;

import controller.ItemUpdate;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ItemScene {

    private Agenda agenda;

    private ItemUpdate observer;
    private Item item;

    private VBox main;

    private ComboBox<String> group;
    private DatePicker date;
    private TextField name;
    private ComboBox<String> teacher;
    private ComboBox<Integer> startHour, startMinute;
    private ComboBox<Integer> endHour,   endMinute;
    private ComboBox<String> classroom;

    private Label error;

    // main function
    public Scene getScene(Stage primaryStage) {

        setItem();

        Scene scene = new Scene(new ScrollPane(this.main), 295, 292);
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

    public void setItem(Item item) {

        this.item = item;
    }

    // scene functions
    private void setItem() {

        this.main = new VBox();

        this.setInputs();
        this.setErrorLabel();
        this.setButtons();
        this.setItemData();
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

        if (this.item != null)
            buttonBox.getChildren().add(this.getDeleteButton());

        this.main.getChildren().add(buttonBox);
    }

    private Button getCancelButton() {

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("button");
        cancel.setOnMouseClicked(e -> this.observer.onItemCancel());

        return cancel;
    }

    private Button getApplyButton() {

        Button apply = new Button("Apply");
        apply.getStyleClass().add("button");
        apply.setOnMouseClicked(e -> {

            if (this.validateInput())
                this.observer.onItemChange(
                    this.agenda.getGroupByName(this.group.getValue()).getId(),
                    this.parseTime(0, 0),
                    this.getItem()
                );
        });

        return apply;
    }

    private Button getDeleteButton() {

        Button delete = new Button("delete");
        delete.getStyleClass().add("button");
        delete.setOnMouseClicked(e -> this.observer.onItemDelete(this.item.getId()));

        return delete;
    }

    private void setItemData() {

        if (this.item != null) {

            this.group.setValue(this.agenda.getGroupNameOfItem(item));
            this.date.setValue(LocalDate.from(this.agenda.getDateOfItem(item)));
            this.name.setText(this.item.getName());
            this.teacher.setValue(this.item.getTeacher().getName());
            this.startHour.setValue(this.item.getStart().getHour());
            this.startMinute.setValue(this.item.getStart().getMinute());
            this.endHour.setValue(this.item.getEnd().getHour());
            this.endMinute.setValue(this.item.getEnd().getMinute());
            this.classroom.setValue(this.item.getClassroom().getName());
        }
    }

    private boolean validateInput() {

        if (this.group.getValue()     .equals("")) {this.setError("group");           return false;}
        if (this.date.getValue()          == null) {this.setError("date");            return false;}
        if (this.name.getText()       .equals("")) {this.setError("name");            return false;}
        if (this.teacher.getValue()   .equals("")) {this.setError("teacher");         return false;}
        if (this.startHour.getValue()     == null) {this.setError("starting hour");   return false;}
        if (this.startMinute.getValue()   == null) {this.setError("starting minute"); return false;}
        if (this.endHour.getValue()       == null) {this.setError("ending hour");     return false;}
        if (this.endMinute.getValue()     == null) {this.setError("ending minute");   return false;}
        if (this.classroom.getValue() .equals("")) {this.setError("classroom");       return false;}

        return true;
    }

    private Item getItem() {

        Item item = new Item();

        item.setId((this.item != null) ? this.item.getId() : -1);
        item.setName(this.name.getText());
        item.setTeacher(this.agenda.getTeacherByName(this.teacher.getValue()));
        item.setStart(this.parseTime(this.startHour.getValue(), this.startMinute.getValue()));
        item.setEnd(this.parseTime(this.endHour.getValue(), this.endMinute.getValue()));
        item.setClassroom(this.agenda.getClassroomByName(this.classroom.getValue()));

        return item;
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

        return (this.item != null) ? "Update: " + this.item.getName() : "Create Schedule Item";
    }

    private ArrayList<Integer> getNumbers(int amount) {

        ArrayList<Integer> number = new ArrayList<>();

        for (int i = 0; i < amount; i++)
            number.add(i);

        return number;
    }

    private void setError(String keyWord) {

        this.error.setText("The " + keyWord + " has not been filled in!");
    }
}