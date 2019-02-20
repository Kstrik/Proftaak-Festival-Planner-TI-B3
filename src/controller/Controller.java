package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.HTTPModel;
import model.JSONModel;
import model.entity.Agenda;
import model.entity.Group;
import model.entity.ScheduleItem;
import view.AgendaScene;
import view.ScheduleItemScene;

import java.time.LocalDateTime;

public class Controller extends Application implements AgendaUpdate, ScheduleItemUpdate {

    private HTTPModel httpModel;

    private Agenda agenda;

    private ScheduleItemScene scheduleItemScene;
    private AgendaScene agendaScene;
    private Stage stage;

    public void startup() {

        launch(Controller.class);
    }

    // overrides
    @Override
    public void start(Stage stage) {

        this.httpModel = new HTTPModel();

        try {

            this.agenda = this.httpModel.getAgenda();

            this.stage = stage;

            this.setSceneVariables();
            this.setAgendaScene();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onScheduleSelectByDate(LocalDateTime date) {

        this.agendaScene.setSchedule(this.agenda.getCombinedScheduleByDate(date));

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
    private void setSceneVariables() {

        this.scheduleItemScene = new ScheduleItemScene();
        this.scheduleItemScene.setAgenda(this.agenda);
        this.scheduleItemScene.setObserver(this);

        this.agendaScene = new AgendaScene();
        this.agendaScene.setAgenda(this.agenda);
        this.agendaScene.setSchedule(this.agenda.getFirstSchedule());
        this.agendaScene.setObserver(this);
    }

    private void setAgendaScene() {

        this.setScene(this.agendaScene.getScene(this.stage));
    }

    private void setScheduleItemScene() {

        this.setScene(this.scheduleItemScene.getScene(this.stage));
    }

    private void setScene(Scene scene) {

        this.stage.setScene(scene);
        this.stage.show();
    }
}
