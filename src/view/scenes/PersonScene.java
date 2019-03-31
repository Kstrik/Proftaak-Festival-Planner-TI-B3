package view.scenes;

import controller.interfaces.PersonUpdate;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.Person;

import java.util.ArrayList;

public class PersonScene extends BaseScene {

    private PersonUpdate observer;
    private Person selected;
    private Agenda agenda;

    private HBox container;

    private ComboBox<String> group;
    private TextField name;
    private ComboBox<String> gender;
    private TextField personID;
    private ComboBox<Boolean> isTeacher;
    private Label error;

    // main function
    @Override
    public Scene getScene(Stage stage) {

        VBox main = this.getMain();

        this.container = new HBox();

        this.setPersonTable();
        this.setCreateMenu();

        main.getChildren().add(this.container);

        this.selected = new Person();

        Scene scene = new Scene(new ScrollPane(main), 1000, 500);
        scene.getStylesheets().add("view/style/style.css");

        return scene;
    }

    // setters
    public void setObserver(PersonUpdate observer) {

        this.observer = observer;
        this.setBaseObserver(observer);
    }

    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    // scene functions
    private void setPersonTable() {

        GridPane personTable = new GridPane();

        Label groupColumn = new Label("Group");
        Label nameColumn = new Label("Name");
        Label genderColumn = new Label("Gender");
        Label personIDColumn = new Label("Person ID");
        Label isTeacherColumn = new Label("Is teacher");
        Label buttonsColumn = new Label();

        groupColumn.getStyleClass().add("-column");
        nameColumn.getStyleClass().add("-column");
        genderColumn.getStyleClass().add("-column");
        personIDColumn.getStyleClass().add("-column");
        isTeacherColumn.getStyleClass().add("-column");
        buttonsColumn.getStyleClass().add("-column-button");

        GridPane.setConstraints(groupColumn, 0, 0);
        GridPane.setConstraints(nameColumn, 1, 0);
        GridPane.setConstraints(genderColumn, 2, 0);
        GridPane.setConstraints(personIDColumn, 3, 0);
        GridPane.setConstraints(isTeacherColumn, 4, 0);
        GridPane.setConstraints(buttonsColumn, 5, 0);

        personTable.getChildren().addAll(groupColumn, nameColumn, genderColumn, personIDColumn, isTeacherColumn, buttonsColumn);
        personTable.getStyleClass().add("-table");

        ArrayList<Person> persons = this.agenda.getAllPersons();

        for (int i = 0; i < persons.size(); i++) {

            Label group = new Label(this.agenda.getGroupByPerson(persons.get(i)).getName());
            Label name = new Label(persons.get(i).getName());
            Label gender = new Label(persons.get(i).getGender());
            Label personID = new Label(Long.toString(persons.get(i).getPersonId()));
            Label isTeacher = new Label(Boolean.toString(persons.get(i).isTeacher()));

            group.getStyleClass().add("-row");
            name.getStyleClass().add("-row");
            gender.getStyleClass().add("-row");
            personID.getStyleClass().add("-row");
            isTeacher.getStyleClass().add("-row");

            HBox buttons = this.getTableButtons(persons.get(i));

            GridPane.setConstraints(group, 0, i + 1);
            GridPane.setConstraints(name, 1, i + 1);
            GridPane.setConstraints(gender, 2, i + 1);
            GridPane.setConstraints(personID, 3, i + 1);
            GridPane.setConstraints(isTeacher, 4, i + 1);
            GridPane.setConstraints(buttons, 5, i + 1);

            personTable.getChildren().addAll(group, name, gender, personID, isTeacher, buttons);
        }

        this.container.getChildren().add(personTable);
    }

    private HBox getTableButtons(Person person) {

        Button delete = new Button("Delete");
        delete.setOnMouseClicked(e -> {

            if (!this.observer.onPersonDelete(person.getId()))
                this.error.setText("This person is still used in some items!");
        });

        Button select = new Button("Select");
        select.setOnMouseClicked(e -> this.selectPerson(person));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(delete, select);

        return hBox;
    }

    private void setCreateMenu() {

        // group
        this.group = new ComboBox<>();
        this.group.setItems(FXCollections.observableArrayList(this.agenda.getAllGroupNames()));
        Label groupLabel = new Label("Group: ");
        HBox groupBox = new HBox();
        groupBox.getChildren().addAll(groupLabel, this.group);

        // name
        this.name = new TextField();
        Label nameLabel = new Label("Name: ");
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, this.name);

        // gender
        this.gender = new ComboBox<>();
        this.gender.setItems(FXCollections.observableArrayList(new ArrayList<String>() {{ add("male"); add("female"); add("other"); }}));
        Label genderLabel = new Label("Gender: ");
        HBox genderBox = new HBox();
        genderBox.getChildren().addAll(genderLabel, this.gender);

        // personID
        this.personID = new TextField();
        Label personIDLabel = new Label("PersonID: ");
        HBox personIDBox = new HBox();
        personIDBox.getChildren().addAll(personIDLabel, this.personID);

        // isTeacher
        this.isTeacher = new ComboBox<>();
        this.isTeacher.setItems(FXCollections.observableArrayList(new ArrayList<Boolean>() {{ add(true); add(false); }}));
        Label isTeacherLabel = new Label("Is teacher: ");
        HBox isTeacherBox = new HBox();
        isTeacherBox.getChildren().addAll(isTeacherLabel, this.isTeacher);

        // other
        HBox error = this.setError();
        HBox buttons = this.getCreateMenuButtons();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(groupBox, nameBox, genderBox, personIDBox, isTeacherBox, error, buttons);

        this.container.getChildren().add(vBox);
    }

    private HBox getCreateMenuButtons() {

        Button reset = new Button("Reset");
        reset.getStyleClass().add("-create-menu-button");
        reset.setOnMouseClicked(e -> this.selectPerson(new Person()));

        Button apply = new Button("Apply");
        apply.getStyleClass().add("-create-menu-button");
        apply.setOnMouseClicked(e -> this.validateInput());

        HBox buttons = new HBox();
        buttons.getChildren().addAll(reset, apply);

        return buttons;
    }

    private HBox setError() {

        this.error = new Label();
        this.error.getStyleClass().add("-error-label");

        return new HBox(this.error);
    }

    // methods
    private void selectPerson(Person person) {

        this.selected = person;

        this.name.setText(person.getName());

        if (this.agenda.getGroupByPerson(person).getId() != -1)
            this.group.setValue(this.agenda.getGroupByPerson(person).getName());
        else
            this.group.getSelectionModel().clearSelection();

        if (person.getId() != -1) {

            this.gender.setValue(person.getGender());
            this.personID.setText(Long.toString(person.getPersonId()));
            this.isTeacher.setValue(person.isTeacher());
        } else {

            this.gender.getSelectionModel().clearSelection();
            this.personID.setText("");
            this.isTeacher.getSelectionModel().clearSelection();
        }
    }

    private void setErrorMessage(String keyWord) {

        this.error.setText("The " + keyWord + " has not been filled in!");
    }

    private void validateInput() {

        boolean valid = true;

        if (this.isTeacher.getValue() == null) {this.setErrorMessage("\"Is teacher\""); valid = false;}
        if (this.personID.getText().isEmpty()) {this.setErrorMessage("personID");       valid = false;}
        if (this.gender.getValue()    == null) {this.setErrorMessage("gender");         valid = false;}
        if (this.name.getText()    .isEmpty()) {this.setErrorMessage("name");           valid = false;}
        if (this.group.getValue()     == null) {this.setErrorMessage("group");          valid = false;}

        if (valid)
            this.observer.onPersonChange(this.agenda.getGroupByName(this.group.getValue()), this.getPerson());
    }

    private Person getPerson() {

        if (this.selected.getId() == -1)
            this.selected.setId(this.agenda.getNewPersonId());

        this.selected.setName(this.name.getText());
        this.selected.setGender(this.gender.getValue());
        this.selected.setMemberId(Integer.parseInt(this.personID.getText()));
        this.selected.setIsTeacher(this.isTeacher.getValue());

        return selected;
    }
}
