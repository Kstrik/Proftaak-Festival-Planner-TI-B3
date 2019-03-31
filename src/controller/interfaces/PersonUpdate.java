package controller.interfaces;

import model.entity.Group;
import model.entity.Person;

public interface PersonUpdate extends BaseUpdate {

    void onPersonChange(Group group, Person person);
    boolean onPersonDelete(int personId);
}
