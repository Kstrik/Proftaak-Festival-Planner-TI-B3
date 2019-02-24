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
        this.container.getStyleClass().addAll("paddingless", "borderless");

        this.setPersonTable();
        this.setCreateMenu();

        main.getChildren().add(this.container);

        Scene scene = new Scene(new ScrollPane(main), 1073, 289);
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

        groupColumn.getStyleClass().add("column");
        nameColumn.getStyleClass().add("column");
        genderColumn.getStyleClass().add("column");
        personIDColumn.getStyleClass().add("column");
        isTeacherColumn.getStyleClass().add("column");
        buttonsColumn.getStyleClass().add("button-column");

        GridPane.setConstraints(groupColumn, 0, 0);
        GridPane.setConstraints(nameColumn, 1, 0);
        GridPane.setConstraints(genderColumn, 2, 0);
        GridPane.setConstraints(personIDColumn, 3, 0);
        GridPane.setConstraints(isTeacherColumn, 4, 0);
        GridPane.setConstraints(buttonsColumn, 5, 0);

        personTable.getChildren().addAll(groupColumn, nameColumn, genderColumn, personIDColumn, isTeacherColumn, buttonsColumn);

        ArrayList<Person> persons = this.agenda.getAllPersons();

        for (int i = 0; i < persons.size(); i++) {

            Label group = new Label(this.agenda.getGroupByPerson(persons.get(i)).getName());
            Label name = new Label(persons.get(i).getName());
            Label gender = new Label(persons.get(i).getGender());
            Label personID = new Label(Long.toString(persons.get(i).getPersonID()));
            Label isTeacher = new Label(Boolean.toString(persons.get(i).isTeacher()));

            group.getStyleClass().add("row");
            name.getStyleClass().add("row");
            gender.getStyleClass().add("row");
            personID.getStyleClass().add("row");
            isTeacher.getStyleClass().add("row");

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
        delete.getStyleClass().addAll("button", "table-button");
        delete.setOnMouseClicked(e -> this.observer.onPersonDelete(person.getId()));

        Button select = new Button("Select");
        select.getStyleClass().addAll("button", "table-button");
        select.setOnMouseClicked(e -> this.selectPerson(person));

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

        // name
        this.name = new TextField();
        Label nameLabel = new Label("Name: ");
        nameLabel.getStyleClass().add("item-label");
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, this.name);

        // gender
        this.gender = new ComboBox<>();
        this.gender.setItems(FXCollections.observableArrayList(new ArrayList<String>() {{ add("male"); add("female"); add("other"); }}));
        Label genderLabel = new Label("Gender: ");
        genderLabel.getStyleClass().add("item-label");
        HBox genderBox = new HBox();
        genderBox.getChildren().addAll(genderLabel, this.gender);

        // personID
        this.personID = new TextField();
        Label personIDLabel = new Label("PersonID: ");
        personIDLabel.getStyleClass().add("item-label");
        HBox personIDBox = new HBox();
        personIDBox.getChildren().addAll(personIDLabel, this.personID);

        // isTeacher
        this.isTeacher = new ComboBox<>();
        this.isTeacher.setItems(FXCollections.observableArrayList(new ArrayList<Boolean>() {{ add(true); add(false); }}));
        Label isTeacherLabel = new Label("Is teacher: ");
        isTeacherLabel.getStyleClass().add("item-label");
        HBox isTeacherBox = new HBox();
        isTeacherBox.getChildren().addAll(isTeacherLabel, this.isTeacher);

        // other
        HBox error = this.setError();
        HBox buttons = this.getCreateMenuButtons();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("createMenu");
        vBox.getChildren().addAll(groupBox, nameBox, genderBox, personIDBox, isTeacherBox, error, buttons);

        this.container.getChildren().add(vBox);
    }

    private HBox getCreateMenuButtons() {

        Button reset = new Button("Reset");
        reset.getStyleClass().addAll("button", "createMenu-reset");
        reset.setOnMouseClicked(e -> this.selectPerson(new Person()));

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
    private void selectPerson(Person person) {

        this.selected = person;

        this.name.setText(person.getName());

        if (this.agenda.getGroupByPerson(person).getId() != -1)
            this.group.setValue(this.agenda.getGroupByPerson(person).getName());
        else
            this.group.getSelectionModel().clearSelection();

        if (person.getId() != -1) {

            this.gender.setValue(person.getGender());
            this.personID.setText(Long.toString(person.getPersonID()));
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

        if (this.group.getValue()     == null) {this.setErrorMessage("group");          valid = false;}
        if (this.name.getText()    .isEmpty()) {this.setErrorMessage("name");           valid = false;}
        if (this.gender.getValue()    == null) {this.setErrorMessage("gender");         valid = false;}
        if (this.personID.getText().isEmpty()) {this.setErrorMessage("personID");       valid = false;}
        if (this.isTeacher.getValue() == null) {this.setErrorMessage("\"Is teacher\""); valid = false;}

        if (!valid)
            return;

        this.observer.onPersonChange(this.agenda.getGroupByName(this.group.getValue()), this.getPerson());
    }

    private Person getPerson() {

        this.selected.setName(this.name.getText());
        this.selected.setGender(this.gender.getValue());
        this.selected.setMemberID(Long.valueOf(this.personID.getText()));
        this.selected.setIsTeacher(this.isTeacher.getValue());

        return selected;
    }
}
