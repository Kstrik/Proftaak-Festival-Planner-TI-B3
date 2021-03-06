package controller.interfaces;

import model.entity.Group;
import model.entity.Schedule;

public interface ScheduleUpdate extends BaseUpdate {

    void onScheduleChange(Group group, Schedule schedule);
    boolean onScheduleDelete(int scheduleId);
}
