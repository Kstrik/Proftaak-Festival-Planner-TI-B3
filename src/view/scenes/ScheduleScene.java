package view.scenes;

import controller.interfaces.ScheduleUpdate;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ScheduleScene extends BaseScene {

    private ScheduleUpdate observer;
    private Schedule selected;
    private Agenda agenda;

    private HBox container;

    private ComboBox<String> group;
    private DatePicker date;
    private Label error;

    // main function
    @Override
    public Scene getScene(Stage stage) {

        VBox main = this.getMain();

        this.container = new HBox();
        this.container.getStyleClass().addAll("paddingless", "borderless");

        this.setScheduleTable();
        this.setCreateMenu();

        main.getChildren().add(this.container);

        Scene scene = new Scene(new ScrollPane(main), 698, 289);
        scene.getStylesheets().add("view/style/style.css");

        return scene;
    }

    // setters
    public void setObserver(ScheduleUpdate observer) {

        this.observer = observer;
        this.setBaseObserver(observer);
    }

    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    // scene functions
    private void setScheduleTable() {

        GridPane schedulesTable = new GridPane();

        Label groupColumn = new Label("Group");
        Label nameColumn = new Label("Date");
        Label buttonsColumn = new Label();

        groupColumn.getStyleClass().add("column");
        nameColumn.getStyleClass().add("column");
        buttonsColumn.getStyleClass().add("button-column");

        GridPane.setConstraints(groupColumn, 0, 0);
        GridPane.setConstraints(nameColumn, 1, 0);
        GridPane.setConstraints(buttonsColumn, 2, 0);

        schedulesTable.getChildren().addAll(groupColumn, nameColumn, buttonsColumn);

        ArrayList<Schedule> schedules = this.agenda.getAllSchedules();

        for (int i = 0; i < schedules.size(); i++) {

            Label group = new Label(this.agenda.getGroupBySchedule(schedules.get(i)).getName());
            Label date = new Label(schedules.get(i).getDate().toString());

            group.getStyleClass().add("row");
            date.getStyleClass().add("row");

            HBox buttons = this.getTableButtons(schedules.get(i));

            GridPane.setConstraints(group, 0, i + 1);
            GridPane.setConstraints(date, 1, i + 1);
            GridPane.setConstraints(buttons, 2, i + 1);

            schedulesTable.getChildren().addAll(group, date, buttons);
        }

        this.container.getChildren().add(schedulesTable);
    }

    private HBox getTableButtons(Schedule schedules) {

        Button delete = new Button("Delete");
        delete.getStyleClass().addAll("button", "table-button");
        delete.setOnMouseClicked(e -> this.observer.onScheduleDelete(schedules.getId()));

        Button select = new Button("Select");
        select.getStyleClass().addAll("button", "table-button");
        select.setOnMouseClicked(e -> this.selectSchedule(schedules));

        HBox hBox = new HBox();
        hBox.getStyleClass().addAll("paddingless", "borderless");
        hBox.getChildren().addAll(delete, select);

        return hBox;
    }

    private void setCreateMenu() {

        // group
        this.group = new ComboBox<>();
        this.group.setItems(FXCollections.observableArrayList(this.agenda.getAllGroupNames()));
        Label groupLabel = new Label("Group: ");
        groupLabel.getStyleClass().add("item-label");
        HBox groupBox = new HBox();
        groupBox.getChildren().addAll(groupLabel, this.group);

        // date
        this.date = new DatePicker();
        Label dateLabel = new Label("Date: ");
        dateLabel.getStyleClass().add("item-label");
        HBox dateBox = new HBox();
        dateBox.getChildren().addAll(dateLabel, this.date);

        // other
        HBox error = this.setError();
        HBox buttons = this.getCreateMenuButtons();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("createMenu");
        vBox.getChildren().addAll(groupBox, dateBox, error, buttons);

        this.container.getChildren().add(vBox);
    }

    private HBox getCreateMenuButtons() {

        Button reset = new Button("Reset");
        reset.getStyleClass().addAll("button", "createMenu-reset");
        reset.setOnMouseClicked(e -> this.selectSchedule(new Schedule()));

        Button apply = new Button("Apply");
        apply.getStyleClass().addAll("button", "createMenu-apply");
        apply.setOnMouseClicked(e -> this.validateInput());

        HBox buttons = new HBox();
        buttons.getStyleClass().addAll("paddingless", "item-button-box");
        buttons.getChildren().addAll(reset, apply);

        return buttons;
    }

    private HBox setError() {

        this.error = new Label();
        this.error.getStyleClass().add("error");

        return new HBox(this.error);
    }

    // methods
    private void selectSchedule(Schedule schedule) {

        this.selected = schedule;

        this.group.setValue(this.agenda.getGroupBySchedule(schedule).getName());

        if (schedule.getId() != -1)
            this.date.setValue(LocalDate.from(schedule.getDate()));
        else
            this.date.getEditor().clear();
    }

    private void setErrorMessage(String keyWord) {

        this.error.setText("The " + keyWord + " has not been filled in!");
    }

    private void validateInput() {

        boolean valid = true;

        if (this.date.getValue()  == null) {this.setErrorMessage("date");  valid = false;}
        if (this.group.getValue() == null) {this.setErrorMessage("group"); valid = false;}

        if (valid)
            this.observer.onScheduleChange(this.agenda.getGroupByName(this.group.getValue()), this.getSchedule());
    }

    private Schedule getSchedule() {

        this.selected.setDate(LocalDateTime.from(this.date.getValue()));

        return selected;
    }
}
