package view.scenes;

import controller.interfaces.BaseUpdate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class BaseScene {

    private BaseUpdate observer;

    protected void setBaseObserver(BaseUpdate observer) {

        this.observer = observer;
    }

    protected VBox getMain() {

        Button save = new Button("Save Agenda");
        save.getStyleClass().add("base-button");
        save.setOnMouseClicked(e -> this.observer.onSaveAgenda());

        Button agenda = new Button("Agenda");
        agenda.getStyleClass().add("base-button");
        agenda.setOnMouseClicked(e -> this.observer.onSelectAgenda());

        Button group = new Button("Groups");
        group.getStyleClass().add("base-button");
        group.setOnMouseClicked(e -> this.observer.onSelectGroup());

        Button schedule = new Button("Schedules");
        schedule.getStyleClass().add("base-button");
        schedule.setOnMouseClicked(e -> this.observer.onSelectSchedule());

        Button classroom = new Button("Classrooms");
        classroom.getStyleClass().add("base-button");
        classroom.setOnMouseClicked(e -> this.observer.onSelectClassroom());

        Button person = new Button("Persons");
        person.getStyleClass().add("base-button");
        person.setOnMouseClicked(e -> this.observer.onSelectPerson());

        HBox hBox = new HBox();
        hBox.getChildren().addAll(save, agenda, group, schedule, classroom, person);
        hBox.getStyleClass().addAll("paddingless", "borderless-bottom");

        VBox vbox = new VBox();
        vbox.getChildren().add(hBox);
        vbox.getStyleClass().add("main");

        return vbox;
    }

    public abstract Scene getScene(Stage stage);
}
