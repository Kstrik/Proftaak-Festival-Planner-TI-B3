package controller.interfaces;

import model.entity.Item;

import java.time.LocalDateTime;

public interface AgendaUpdate extends BaseUpdate {

    void onAgendaSelectByDate(LocalDateTime localDateTime);

    void onAgendaItemCreate();
    void onAgendaItemSelect(Item item);
}
