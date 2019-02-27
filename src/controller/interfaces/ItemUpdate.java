package controller.interfaces;

import model.entity.Group;
import model.entity.Item;
import model.entity.Schedule;

public interface ItemUpdate {

    void onItemCancel();
    void onItemChange(Group group, Schedule schedule, Item item);
    void onItemDelete(int scheduleId);
}
