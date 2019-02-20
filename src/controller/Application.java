package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.HTTPModel;
import model.entity.Agenda;
import model.entity.Group;
import model.entity.ScheduleItem;
import view.AgendaScene;
import view.ScheduleItemScene;

import java.io.IOException;
import java.time.LocalDateTime;

public class Application extends javafx.application.Application implements AgendaUpdate, ScheduleItemUpdate, StartApplication {

    private HTTPModel httpModel;

    private Agenda agenda;

    private ScheduleItemScene scheduleItemScene;
    private AgendaScene agendaScene;
    private Stage stage;

    // startup
    @Override
    public void startup() {

        launch(Application.class);
    }

    @Override
    public void start(Stage stage) {

        this.httpModel = new HTTPModel();

        this.stage = stage;

        this.setAgendaScene();
    }

    // overrides
    @Override
    public void onScheduleSelectByDate(LocalDateTime date) {

        this.agendaScene.setSchedule(this.agenda.getCombinedScheduleByDate(date));

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
    public void onScheduleItemDelete(int scheduleItemId) {

        try {

            this.httpModel.deleteScheduleItem(scheduleItemId);
            this.setAgendaScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onScheduleItemChange(int groupId, LocalDateTime date, ScheduleItem scheduleItem) {

        try {

            if (scheduleItem.getId() == -1)
                this.httpModel.createScheduleItem(groupId, date, scheduleItem);
            else
                this.httpModel.updateScheduleItem(groupId, date, scheduleItem);

            this.setAgendaScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onScheduleItemCancel() {

        this.setAgendaScene();
    }

    // methods
    private void updateAgenda() {

        this.setAgenda();

        this.scheduleItemScene = new ScheduleItemScene();
        this.scheduleItemScene.setAgenda(this.agenda);
        this.scheduleItemScene.setObserver(this);

        this.agendaScene = new AgendaScene();
        this.agendaScene.setAgenda(this.agenda);
        this.agendaScene.setSchedule(this.agenda.getFirstSchedule());
        this.agendaScene.setObserver(this);
    }

    private void setAgenda() {

        try {

            this.agenda = this.httpModel.getAgenda();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void setAgendaScene() {

        this.setScene(this.agendaScene.getScene(this.stage));
    }

    private void setScheduleItemScene() {

        this.setScene(this.scheduleItemScene.getScene(this.stage));
    }

    private void setScene(Scene scene) {

        this.updateAgenda();

        this.stage.setScene(scene);
        this.stage.show();
    }
}
