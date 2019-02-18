package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AgendaModel;
import model.entity.Agenda;
import model.entity.Group;
import model.entity.Schedule;
import model.entity.ScheduleItem;
import view.AgendaScene;
import view.ScheduleItemScene;

import java.sql.SQLOutput;
import java.time.LocalDateTime;

public class AgendaController extends Application implements AgendaUpdate, ScheduleItemUpdate {

    private AgendaModel agendaModel;

    private ScheduleItemScene scheduleItemScene;
    private AgendaScene agendaScene;
    private Stage stage;

    public void startup() {

        launch(AgendaController.class);
    }

    // overrides
    @Override
    public void start(Stage stage) {

        this.agendaModel = new AgendaModel();
        this.stage = stage;

        this.scheduleItemScene = new ScheduleItemScene();
        this.scheduleItemScene.setObserver(this);

        this.agendaScene = new AgendaScene();
        this.agendaScene.setObserver(this);
        this.agendaScene.setSchedule(this.agendaModel.getFirstSchedule());

        this.setAgendaScene();
    }

    @Override
    public void onScheduleSelectByDate(LocalDateTime date) {

        this.agendaScene.setSchedule(this.agendaModel.getCombinedScheduleByDate(date));

        System.out.println("Selected " + date.toString());

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

        System.out.println("Read " + scheduleItem.getName() + " with id " + scheduleItem.getId());

        this.setScheduleItemScene();
    }

    @Override
    public void onScheduleItemDelete(ScheduleItem scheduleItem) {

//        this.agenda.removeScheduleItem(scheduleItem);
        System.out.println("Delete " + scheduleItem.getName() + " with id " + scheduleItem.getId());

        this.setAgendaScene();
    }

    @Override
    public void onScheduleItemChange(Group group, LocalDateTime date, ScheduleItem scheduleItem) {

        System.out.println("Create/Update " + scheduleItem.getName() + " with id " + scheduleItem.getId());

        this.setAgendaScene();
    }

    @Override
    public void onScheduleItemCancel() {

        System.out.println("Create new Schedule item");

        this.setAgendaScene();
    }

    // methods
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
