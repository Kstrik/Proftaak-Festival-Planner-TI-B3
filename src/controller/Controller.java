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
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Controller extends Application implements AgendaUpdate, ClassroomUpdate, GroupUpdate, ItemUpdate, PersonUpdate, ScheduleUpdate {

    private JSONModel jsonModel;

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

        this.jsonModel = new JSONModel();
        this.agenda = this.getAgenda();
        this.stage = stage;

        this.prepareScenes();
        this.setAgenda();
        this.setAgendaScene();
    }

    // baseUpdate
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
    @Override
    public void onSaveAgenda()      { this.jsonModel.saveJSONFile(this.agenda, ConfigModel.FILE_NAME); }

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
    public void onAgendaSelectByDate(LocalDate date) {

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
    public boolean onClassroomDelete(int classroomID) {

        boolean deleted = this.agenda.deleteClassroom(classroomID);
        this.setClassroomScene();

        return deleted;
    }

    // groupUpdate
    @Override
    public void onGroupChange(Group group) {

        this.agenda.setGroup(group);
        this.setGroupScene();
    }

    @Override
    public boolean onGroupDelete(int groupId) {

        boolean deleted = this.agenda.deleteGroup(groupId);
        this.setGroupScene();

        return deleted;
    }

    // itemUpdate
    @Override
    public void onItemChange(Group group, Schedule schedule, Item item) {

        this.agenda.setItem(group, schedule, item);
        this.setAgendaScene();
    }

    @Override
    public void onItemDelete(int itemId) {

        this.agenda.deleteItem(itemId);
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
    public boolean onPersonDelete(int personId) {

        boolean deleted = this.agenda.deletePerson(personId);
        this.setPersonScene();

        return deleted;
    }

    // scheduleUpdate
    @Override
    public void onScheduleChange(Group group, Schedule schedule) {

        this.agenda.setSchedule(group, schedule);
        this.setScheduleScene();
    }

    @Override
    public boolean onScheduleDelete(int scheduleId) {

        boolean deleted = this.agenda.deleteSchedule(scheduleId);
        this.setScheduleScene();

        return deleted;
    }

    // methods
    private Agenda getAgenda() {

        return this.jsonModel.convertJSONAgenda(this.jsonModel.parseJSONFile(ConfigModel.FILE_NAME));
    }

    private void setAgenda() {

        this.agendaScene.setAgenda(this.agenda);
        this.classroomScene.setAgenda(this.agenda);
        this.groupScene.setAgenda(this.agenda);
        this.itemScene.setAgenda(this.agenda);
        this.personScene.setAgenda(this.agenda);
        this.scheduleScene.setAgenda(this.agenda);
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

        this.stage.setTitle(this.agenda.getName());
        this.stage.setScene(scene);
        this.stage.show();
    }
}
