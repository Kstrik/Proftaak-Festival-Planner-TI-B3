package controller.interfaces;

import model.entity.Classroom;

public interface ClassroomUpdate extends BaseUpdate {

    void onClassroomChange(Classroom classroom);
    boolean onClassroomDelete(int classroomID);
}
