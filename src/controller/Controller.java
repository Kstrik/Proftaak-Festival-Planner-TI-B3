package controller;

import controller.interfaces.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ConfigModel;
import model.HTTPModel;
import model.JSONModel;
import model.entity.*;
import view.scenes.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class Controller extends Application implements AgendaUpdate, ClassroomUpdate, GroupUpdate, ItemUpdate, PersonUpdate, ScheduleUpdate {

    private HTTPModel httpModel;

    private Agenda agenda;

    private AgendaScene agendaScene;
    private ClassroomScene classroomScene;
    private GroupScene groupScene;
    private ItemScene itemScene;
    private PersonScene personScene;
    private ScheduleScene scheduleScene;

    private Stage stage;

    // startup
    public void startup() {

        launch(Controller.class);
    }

    @Override
    public void start(Stage stage) {

        this.httpModel = new HTTPModel();
        this.stage = stage;
        this.agenda = this.getAgenda();

        this.prepareScenes();
        this.setAgenda();
        this.setAgendaScene();
    }

    // baseUpdate
    @Override
    public void onSaveAgenda() {}

    @Override
    public void onSelectAgenda()    { this.setAgendaScene(); }
    @Override
    public void onSelectClassroom() { this.setClassroomScene(); }
    @Override
    public void onSelectGroup()     { this.setGroupScene(); }
    @Override
    public void onSelectPerson()    { this.setPersonScene(); }
    @Override
    public void onSelectSchedule()  { this.setScheduleScene(); }

    // agendaUpdate
    @Override
    public void onAgendaItemCreate() {

        this.itemScene.setItem(new Item());

        this.setItemScene();
    }

    @Override
    public void onAgendaItemSelect(Item item) {

        this.itemScene.setItem(item);

        this.setItemScene();
    }

    @Override
    public void onAgendaSelectByDate(LocalDateTime date) {

        this.agendaScene.setSchedule(this.getAgenda().getCombinedScheduleByDate(date));

        this.setAgendaScene();
    }

    // classroomUpdate
    @Override
    public void onClassroomChange(Classroom classroom) {

        this.agenda.setClassroom(classroom);
        this.setClassroomScene();
    }

    @Override
    public void onClassroomDelete(int classroomID) {

        this.agenda.deleteClassroom(classroomID);
        this.setClassroomScene();
    }

    // groupUpdate
    @Override
    public void onGroupChange(Group group) {

        this.agenda.setGroup(group);
        this.setGroupScene();
    }

    @Override
    public void onGroupDelete(int groupId) {

        this.agenda.deleteGroup(groupId);
        this.setGroupScene();
    }

    // itemUpdate
    @Override
    public void onItemDelete(int itemId) {

        this.agenda.deleteItem(itemId);
        this.setAgendaScene();
    }

    @Override
    public void onItemChange(Group group, Schedule schedule, Item item) {

        this.agenda.setItem(group, schedule, item);
        this.setAgendaScene();
    }

    @Override
    public void onItemCancel() {

        this.setAgendaScene();
    }

    // personUpdate
    @Override
    public void onPersonChange(Group group, Person person) {

        this.agenda.setPerson(group, person);
        this.setPersonScene();
    }

    @Override
    public void onPersonDelete(int personId) {

        this.agenda.deletePerson(personId);
        this.setPersonScene();
    }

    // scheduleUpdate
    @Override
    public void onScheduleChange(Group group, Schedule schedule) {

        this.agenda.setSchedule(group, schedule);
        this.setScheduleScene();
    }

    @Override
    public void onScheduleDelete(int scheduleId) {

        this.agenda.deleteSchedule(scheduleId);
        this.setScheduleScene();
    }

    // methods
    private Agenda getAgenda() {

        JSONModel jsonModel = new JSONModel();
        return jsonModel.convertJSONAgenda(jsonModel.parseJSONFile("agenda.old.json"));
    }

    private void setAgenda() {

        this.agendaScene.setAgenda(agenda);
        this.classroomScene.setAgenda(agenda);
        this.groupScene.setAgenda(agenda);
        this.itemScene.setAgenda(agenda);
        this.personScene.setAgenda(agenda);
        this.scheduleScene.setAgenda(agenda);
    }

    private void prepareScenes() {

        this.agendaScene = new AgendaScene();
        this.agendaScene.setObserver(this);

        this.classroomScene = new ClassroomScene();
        this.classroomScene.setObserver(this);

        this.groupScene = new GroupScene();
        this.groupScene.setObserver(this);

        this.itemScene = new ItemScene();
        this.itemScene.setObserver(this);

        this.personScene = new PersonScene();
        this.personScene.setObserver(this);

        this.scheduleScene = new ScheduleScene();
        this.scheduleScene.setObserver(this);
    }

    private void setAgendaScene()    {this.setScene(this.agendaScene.getScene(this.stage));}
    private void setClassroomScene() {this.setScene(this.classroomScene.getScene(this.stage));}
    private void setGroupScene()     {this.setScene(this.groupScene.getScene(this.stage));}
    private void setItemScene()      {this.setScene(this.itemScene.getScene(this.stage));}
    private void setPersonScene()    {this.setScene(this.personScene.getScene(this.stage));}
    private void setScheduleScene()  {this.setScene(this.scheduleScene.getScene(this.stage));}

    private void setScene(Scene scene) {

        this.stage.setScene(scene);
        this.stage.show();
    }
}
