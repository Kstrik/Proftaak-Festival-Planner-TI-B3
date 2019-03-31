package controller.interfaces;

import model.entity.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AgendaUpdate extends BaseUpdate {

    void onAgendaSelectByDate(LocalDate localDate);

    void onAgendaItemCreate();
    void onAgendaItemSelect(Item item);
}
