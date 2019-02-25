package view.scenes;

import controller.interfaces.GroupUpdate;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.entity.Agenda;
import model.entity.Group;

import java.util.ArrayList;

public class GroupScene extends BaseScene {

    private GroupUpdate observer;
    private Group selected;
    private Agenda agenda;

    private HBox container;

    private TextField name;
    private ComboBox<Boolean> isTeacherGroup;
    private Label error;

    // main function
    @Override
    public Scene getScene(Stage stage) {

        VBox main = this.getMain();

        this.container = new HBox();
        this.container.getStyleClass().addAll("paddingless", "borderless");

        this.setGroupTable();
        this.setCreateMenu();

        main.getChildren().add(this.container);

        Scene scene = new Scene(new ScrollPane(main), 698, 289);
        scene.getStylesheets().add("view/style/style.css");

        return scene;
    }

    // setters
    public void setObserver(GroupUpdate observer) {

        this.observer = observer;
        this.setBaseObserver(observer);
    }

    public void setAgenda(Agenda agenda) {

        this.agenda = agenda;
    }

    // scene functions
    private void setGroupTable() {

        GridPane groupTable = new GridPane();

        Label nameColumn = new Label("Name");
        Label isTeacherGroupColumn = new Label("Is teacher group");
        Label buttonsColumn = new Label();

        nameColumn.getStyleClass().add("column");
        isTeacherGroupColumn.getStyleClass().add("column");
        buttonsColumn.getStyleClass().add("button-column");

        GridPane.setConstraints(nameColumn, 0, 0);
        GridPane.setConstraints(isTeacherGroupColumn, 1, 0);
        GridPane.setConstraints(buttonsColumn, 2, 0);

        groupTable.getChildren().addAll(nameColumn, isTeacherGroupColumn, buttonsColumn);

        ArrayList<Group> groups = this.agenda.getAllGroups();

        for (int i = 0; i < groups.size(); i++) {

            Label name = new Label(groups.get(i).getName());
            Label isTeacherGroup = new Label(Boolean.toString(groups.get(i).isTeacherGroup()));

            name.getStyleClass().add("row");
            isTeacherGroup.getStyleClass().add("row");

            HBox buttons = this.getTableButtons(groups.get(i));

            GridPane.setConstraints(name, 0, i + 1);
            GridPane.setConstraints(isTeacherGroup, 1, i + 1);
            GridPane.setConstraints(buttons, 2, i + 1);

            groupTable.getChildren().addAll(name, isTeacherGroup, buttons);
        }

        this.container.getChildren().add(groupTable);
    }

    private HBox getTableButtons(Group group) {

        Button delete = new Button("Delete");
        delete.getStyleClass().addAll("button", "table-button");
        delete.setOnMouseClicked(e -> this.observer.onGroupDelete(group.getId()));

        Button select = new Button("Select");
        select.getStyleClass().addAll("button", "table-button");
        select.setOnMouseClicked(e -> this.selectGroup(group));

        HBox hBox = new HBox();
        hBox.getStyleClass().addAll("paddingless", "borderless");
        hBox.getChildren().addAll(delete, select);

        return hBox;
    }

    private void setCreateMenu() {

        // name
        this.name = new TextField();
        Label nameLabel = new Label("Name: ");
        nameLabel.getStyleClass().add("item-label");
        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(nameLabel, this.name);

        // isTeacherGroup
        this.isTeacherGroup = new ComboBox<>();
        this.isTeacherGroup.setItems(FXCollections.observableArrayList(new ArrayList<Boolean>() {{ add(true); add(false); }}));
        Label isTeacherGroupLabel = new Label("Is teacher group: ");
        isTeacherGroupLabel.getStyleClass().add("item-label");
        HBox isTeacherGroupBox = new HBox();
        isTeacherGroupBox.getChildren().addAll(isTeacherGroupLabel, this.isTeacherGroup);

        // other
        HBox error = this.setError();
        HBox buttons = this.getCreateMenuButtons();

        VBox vBox = new VBox();
        vBox.getStyleClass().add("createMenu");
        vBox.getChildren().addAll(nameBox, isTeacherGroupBox, error, buttons);

        this.container.getChildren().add(vBox);
    }

    private HBox getCreateMenuButtons() {

        Button reset = new Button("Reset");
        reset.getStyleClass().addAll("button", "createMenu-reset");
        reset.setOnMouseClicked(e -> this.selectGroup(new Group()));

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
    private void selectGroup(Group group) {

        this.selected = group;

        this.name.setText(group.getName());

        if (group.getId() != -1)
            this.isTeacherGroup.setValue(group.isTeacherGroup());
        else
            this.isTeacherGroup.getSelectionModel().clearSelection();
    }

    private void setErrorMessage(String keyWord) {

        this.error.setText("The " + keyWord + " has not been filled in!");
    }

    private void validateInput() {

        boolean valid = true;

        if (this.isTeacherGroup.getValue() == null) {this.setErrorMessage("\"Is teacher group\""); valid = false;}
        if (this.name.getText()         .isEmpty()) {this.setErrorMessage("name");                 valid = false;}

        if (valid)
            this.observer.onGroupChange(this.getGroup());
    }

    private Group getGroup() {

        this.selected.setName(this.name.getText());
        this.selected.setTeacherGroup(this.isTeacherGroup.getValue());

        return selected;
    }
}
