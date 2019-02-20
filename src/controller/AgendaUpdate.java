package controller;

import model.entity.Item;

import java.time.LocalDateTime;

public interface AgendaUpdate {

    void onScheduleSelectByDate(LocalDateTime localDateTime);

    void onAgendaItemCreate();
    void onAgendaItemRead(Item item);
}
