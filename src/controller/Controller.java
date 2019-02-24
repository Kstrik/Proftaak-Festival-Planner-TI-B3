package controller;

import controller.interfaces.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.HTTPModel;
import model.JSONModel;
import model.entity.*;
import view.scenes.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class Controller extends javafx.application.Application implements AgendaUpdate, ClassroomUpdate, GroupUpdate, ItemUpdate, PersonUpdate, ScheduleUpdate {

    private HTTPModel httpModel;

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

        this.prepareScenes();

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

        try {

            if (classroom.getId() == -1)
                this.httpModel.changeClassroom(classroom, "create");
            else
                this.httpModel.changeClassroom(classroom, "update");

            this.setClassroomScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onClassroomDelete(int classroomID) {

        try                   {this.httpModel.deleteClassroom(classroomID);}
        catch (IOException e) {e.printStackTrace();}

        this.setClassroomScene();
    }

    // groupUpdate
    @Override
    public void onGroupChange(Group group) {

        try {

            if (group.getId() == -1)
                this.httpModel.changeGroup(group, "create");
            else
                this.httpModel.changeGroup(group, "update");

            this.setGroupScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onGroupDelete(int groupId) {

        try                   {this.httpModel.deleteGroup(groupId);}
        catch (IOException e) {e.printStackTrace();}

        this.setGroupScene();
    }

    // itemUpdate
    @Override
    public void onItemDelete(int itemId) {

        try                   {this.httpModel.deleteItem(itemId);}
        catch (IOException e) {e.printStackTrace();}

        this.setAgendaScene();
    }

    @Override
    public void onItemChange(Group group, Schedule schedule, Item item) {

        try {

            if (item.getId() == -1)
                this.httpModel.changeItem(group, schedule, item, "create");
            else
                this.httpModel.changeItem(group, schedule, item, "update");

            this.setAgendaScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onItemCancel() {

        this.setAgendaScene();
    }

    // personUpdate
    @Override
    public void onPersonChange(Group group, Person person) {

        try {

            if (person.getId() == -1)
                this.httpModel.changePerson(group, person, "create");
            else
                this.httpModel.changePerson(group, person, "update");

            this.setPersonScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onPersonDelete(int personId) {

        try                   {this.httpModel.deletePerson(personId);}
        catch (IOException e) {e.printStackTrace();}

        this.setPersonScene();
    }

    // scheduleUpdate
    @Override
    public void onScheduleChange(Group group, Schedule schedule) {

        try {

            if (schedule.getId() == -1)
                this.httpModel.changeSchedule(group, schedule, "create");
            else
                this.httpModel.changeSchedule(group, schedule, "update");

            this.setScheduleScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onScheduleDelete(int scheduleId) {

        try                   {this.httpModel.deleteSchedule(scheduleId);}
        catch (IOException e) {e.printStackTrace();}

        this.setScheduleScene();
    }

    // methods
    private Agenda getAgenda() {

        JSONModel jsonModel = new JSONModel();
        return jsonModel.convertJSONAgenda(jsonModel.parseJSONFile("agenda"));

//        try                 {return this.httpModel.getAgenda();}
//        catch (Exception e) {e.printStackTrace();}
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

    private void setAgendaScene() {

        this.agendaScene.setAgenda(this.getAgenda());
        this.setScene(this.agendaScene.getScene(this.stage));
    }

    private void setClassroomScene() {

        this.classroomScene.setAgenda(this.getAgenda());
        this.setScene(this.classroomScene.getScene(this.stage));
    }

    private void setGroupScene() {

        this.groupScene.setAgenda(this.getAgenda());
        this.setScene(this.groupScene.getScene(this.stage));
    }

    private void setItemScene() {

        this.itemScene.setAgenda(this.getAgenda());
        this.setScene(this.itemScene.getScene(this.stage));
    }

    private void setPersonScene() {

        this.personScene.setAgenda(this.getAgenda());
        this.setScene(this.personScene.getScene(this.stage));
    }

    private void setScheduleScene() {

        this.scheduleScene.setAgenda(this.getAgenda());
        this.setScene(this.scheduleScene.getScene(this.stage));
    }

    private void setScene(Scene scene) {

        this.stage.setScene(scene);
        this.stage.show();
    }
}
