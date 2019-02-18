package controller;

import model.entity.ScheduleItem;

import java.time.LocalDateTime;

public interface AgendaUpdate {

    void onScheduleSelectByDate(LocalDateTime localDateTime);

    void onAgendaScheduleItemCreate();
    void onAgendaScheduleItemRead(ScheduleItem scheduleItem);
}
