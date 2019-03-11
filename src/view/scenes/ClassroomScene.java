package view.scenes;

import controller.interfaces.ClassroomUpdate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.Classroom;

import java.util.ArrayList;

public class ClassroomScene extends BaseScene {

    private ClassroomUpdate observer;
    private Classroom selected;
    private Agenda agenda;

    private HBox container;

    private TextField name;
    private Label error;

    // main function
    @Override
    public Scene getScene(Stage stage) {

        this.selected = new Classroom();

        this.container = new HBox();
        this.container.getStyleClass().addAll("paddingless", "borderless");

        this.setClassroomTable();
        this.setCreateMenu();

        VBox main = this.getMain();
        main.getChildren().add(this.container);

        this.selected = new Classroom();

        Scene scene = new Scene(new ScrollPane(main), 573, 289);
        scene.getStylesheets().add("view/style/style.css");

        return scene;
    }

    // setters
    public void setObserver(ClassroomUpdate observer) {

        this.observer = observer;
        this.setBaseObserver(observer);
    }

    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    // scene functions
    private void setClassroomTable() {

        GridPane classroomTable = new GridPane();

        Label nameColumn = new Label("Name");
        Label buttonsColumn = new Label();

        nameColumn.getStyleClass().add("column");
        buttonsColumn.getStyleClass().add("button-column");

        GridPane.setConstraints(nameColumn, 0, 0);
        GridPane.setConstraints(buttonsColumn, 1, 0);

        classroomTable.getChildren().addAll(nameColumn, buttonsColumn);

        ArrayList<Classroom> classrooms = this.agenda.getAllClassrooms();

        for (int i = 0; i < classrooms.size(); i++) {

            Label name = new Label(classrooms.get(i).getName());

            name.getStyleClass().add("row");

            HBox buttons = this.getTableButtons(classrooms.get(i));

            GridPane.setConstraints(name, 0, i + 1);
            GridPane.setConstraints(buttons, 1, i + 1);

            classroomTable.getChildren().addAll(name, buttons);
        }

        this.container.getChildren().add(classroomTable);
    }

    private HBox getTableButtons(Classroom classroom) {

        Button delete = new Button("Delete");
        delete.getStyleClass().addAll("button", "table-button");
        delete.setOnMouseClicked(e -> this.observer.onClassroomDelete(classroom.getId()));

        Button select = new Button("Select");
        select.getStyleClass().addAll("button", "table-button");
        select.setOnMouseClicked(e -> this.selectClassroom(classroom));

        HBox hBox = new HBox();
        hBox.getStyleClass().addAll("paddingless", "borderless");
        hBox.getChildren().addAll(delete, select);

        return hBox;
    }

    private void setCreateMenu() {

        this.name = new TextField();

        Label nameLabel = new Label("Name: ");
        nameLabel.getStyleClass().add("item-label");
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, this.name);

        HBox error = this.setError();
        HBox buttons = this.getCreateMenuButtons();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("createMenu");
        vBox.getChildren().addAll(nameBox, error, buttons);

        this.container.getChildren().add(vBox);
    }

    private HBox getCreateMenuButtons() {

        Button reset = new Button("Reset");
        reset.getStyleClass().addAll("button", "createMenu-reset");
        reset.setOnMouseClicked(e -> this.selectClassroom(new Classroom()));

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
    private void selectClassroom(Classroom classroom) {

        this.selected = classroom;

        this.name.setText(classroom.getName());
    }

    private void setErrorMessage() {

        this.error.setText("The " + "name" + " has not been filled in!");
    }

    private void validateInput() {

        boolean valid = true;

        if (this.name.getText().isEmpty()) {this.setErrorMessage(); valid = false;}

        if (valid)
            this.observer.onClassroomChange(this.getClassroom());
    }

    private Classroom getClassroom() {

        if (this.selected.getId() == -1)
            this.selected.setId(this.agenda.getNewClassroomId());

        this.selected.setName(this.name.getText());

        return selected;
    }
}
