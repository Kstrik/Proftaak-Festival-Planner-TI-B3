package controller;

import model.entity.Agenda;
import view.SimulatieView;

public class SimulatieController {
    public SimulatieController(Agenda agenda) {
        SimulatieView simulatieView = new SimulatieView(agenda);
    }
}
