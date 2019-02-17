package controller;

import model.agendaEntity.Group;
import model.agendaEntity.ScheduleItem;

import java.time.LocalDateTime;

public interface ScheduleItemUpdate {

    void onScheduleItemCancel();
    void onScheduleItemChange(Group group, LocalDateTime date, ScheduleItem scheduleItem);
}
