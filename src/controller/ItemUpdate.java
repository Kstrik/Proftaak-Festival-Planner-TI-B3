package controller;

import model.entity.Item;

import java.time.LocalDateTime;

public interface ItemUpdate {

    void onItemCancel();
    void onItemChange(int groupId, LocalDateTime date, Item item);
    void onItemDelete(int scheduleId);
}
