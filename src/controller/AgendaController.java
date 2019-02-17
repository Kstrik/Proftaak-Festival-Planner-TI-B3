package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.JSONModel;
import model.agendaEntity.Agenda;
import model.agendaEntity.Group;
import model.agendaEntity.Schedule;
import model.agendaEntity.ScheduleItem;
import view.AgendaScene;
import view.ScheduleItemScene;

import java.time.LocalDateTime;

public class AgendaController extends Application implements AgendaUpdate, ScheduleItemUpdate {

    private ScheduleItemScene scheduleItemScene;
    private AgendaScene agendaScene;
    private Stage stage;

    private Agenda agenda;

    public void startup() {

        launch(AgendaController.class);
    }

    // overrides
    @Override
    public void start(Stage stage) {

        JSONModel jsonModel = new JSONModel();

        this.scheduleItemScene = new ScheduleItemScene();
        this.agendaScene = new AgendaScene();
        this.stage = stage;
        this.agenda = jsonModel.getAgendaWithJSONFile("testAgenda");

        this.setSceneVariables();
        this.setAgendaScene();
    }

    @Override
    public void onScheduleSelectByDate(LocalDateTime date) {

        this.agendaScene.setSchedule(this.agenda.getScheduleByDate(date));

        this.setAgendaScene();
    }

    @Override
    public void onAgendaScheduleItemCreate() {

        this.scheduleItemScene.setScheduleItem(null);

        this.setScheduleItemScene();
    }

    @Override
    public void onAgendaScheduleItemRead(ScheduleItem scheduleItem) {

        this.scheduleItemScene.setScheduleItem(scheduleItem);

        this.setScheduleItemScene();
    }

    @Override
    public void onAgendaScheduleItemDelete(ScheduleItem scheduleItem) {

        this.agenda.removeScheduleItem(scheduleItem);

        this.setSceneVariables();
        this.setAgendaScene();
    }

    @Override
    public void onScheduleItemChange(Group group, LocalDateTime date, ScheduleItem scheduleItem) {

        if (group.getScheduleByDate(date) == null)
            group.addSchedule(new Schedule(date));

        if (scheduleItem.getId() != -1)
            this.agenda.removeScheduleItem(scheduleItem);

        group.getScheduleByDate(date).addScheduleItem(scheduleItem);

        this.setAgendaScene();
    }

    @Override
    public void onScheduleItemCancel() {

        this.setAgendaScene();
    }

    // methods
    public void setSceneVariables() {

        this.agendaScene.setObserver(this);
        this.agendaScene.setAgenda(this.agenda);
        this.agendaScene.setSchedule(this.agenda.getFirstSchedule());

        this.scheduleItemScene.setAgenda(this.agenda);
        this.scheduleItemScene.setObserver(this);
    }

    public void setAgendaScene() {

        this.setScene(this.agendaScene.getScene(this.stage));
    }

    public void setScheduleItemScene() {

        this.setScene(this.scheduleItemScene.getScene(this.stage));
    }

    public void setScene(Scene scene) {

        stage.setScene(scene);
        stage.show();
    }
}
