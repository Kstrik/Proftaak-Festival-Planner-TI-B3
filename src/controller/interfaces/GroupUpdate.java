package controller.interfaces;

import model.entity.Group;

public interface GroupUpdate extends BaseUpdate {

    void onGroupChange(Group group);
    void onGroupDelete(int groupId);
}
