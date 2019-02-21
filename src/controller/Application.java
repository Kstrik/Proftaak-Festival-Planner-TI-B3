package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import model.HTTPModel;
import model.JSONModel;
import model.entity.Agenda;
import model.entity.Item;
import view.AgendaScene;
import view.ItemScene;

import java.io.IOException;
import java.time.LocalDateTime;

public class Application extends javafx.application.Application implements AgendaUpdate, ItemUpdate, StartApplication {

    private HTTPModel httpModel;

    private Agenda agenda;

    private ItemScene itemScene;
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

        this.itemScene = new ItemScene();
        this.itemScene.setObserver(this);

        this.agendaScene = new AgendaScene();
        this.agendaScene.setObserver(this);

        this.setAgendaScene();
    }

    // overrides
    @Override
    public void onScheduleSelectByDate(LocalDateTime date) {

        this.agendaScene.setSchedule(this.agenda.getCombinedScheduleByDate(date));

        this.setAgendaScene();
    }

    @Override
    public void onAgendaItemCreate() {

        this.itemScene.setItem(null);

        this.setItemScene();
    }

    @Override
    public void onAgendaItemRead(Item item) {

        this.itemScene.setItem(item);

        this.setItemScene();
    }

    @Override
    public void onItemDelete(int scheduleItemId) {

        try {

            this.httpModel.deleteItem(scheduleItemId);
            this.setAgendaScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onItemChange(int groupId, LocalDateTime date, Item item) {

        try {

            if (item.getId() == -1)
                this.httpModel.createItem(groupId, date, item);
            else
                this.httpModel.updateItem(groupId, date, item);

            this.setAgendaScene();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onItemCancel() {

        this.setAgendaScene();
    }

    // methods
    private void setAgenda() {

        // TODO: delete get from json file when database is complete
        JSONModel jsonModel = new JSONModel();
        this.agenda = jsonModel.convertJSONAgenda(jsonModel.parseJSONFile("agenda"));

        try {

            this.agenda = this.httpModel.getAgenda();

        } catch (Exception e) {

            e.printStackTrace();
        }

        this.itemScene.setAgenda(this.agenda);
        this.agendaScene.setAgenda(this.agenda);
    }

    private void setAgendaScene() {

        this.setAgenda();

        this.setScene(this.agendaScene.getScene(this.stage));
    }

    private void setItemScene() {

        this.setAgenda();

        this.setScene(this.itemScene.getScene(this.stage));
    }

    private void setScene(Scene scene) {

        this.stage.setScene(scene);
        this.stage.show();
    }
}
