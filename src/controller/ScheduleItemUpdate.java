package controller;

import model.entity.ScheduleItem;

import java.time.LocalDateTime;

public interface ScheduleItemUpdate {

    void onScheduleItemCancel();
    void onScheduleItemChange(int groupId, LocalDateTime date, ScheduleItem scheduleItem);
    void onScheduleItemDelete(int scheduleId);
}
