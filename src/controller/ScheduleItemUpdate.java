package controller;

import model.entity.Group;
import model.entity.ScheduleItem;

import java.time.LocalDateTime;

public interface ScheduleItemUpdate {

    void onScheduleItemCancel();
    void onScheduleItemChange(Group group, LocalDateTime date, ScheduleItem scheduleItem);
    void onScheduleItemDelete(ScheduleItem scheduleItem);
}
