package agenda;

import java.util.ArrayList;

public class Group {
    private ArrayList<Person> members;
    private boolean isTeacherGroup = false;

    public Group(ArrayList<Person> members) {
        this.members = members;
    }

    void addMember(Person member) {
        this.members.add(member);
    }

    public Person getPerson(int index) {
        if(!this.CanGetIndex(index)) return null;

        return this.members.get(index);
    }

    private boolean CanGetIndex(int index) {
        //TODO: implement in GUI
        if(index >= this.members.toArray().length) {
            System.out.println("index is higher then amount of members");
            return false;
        }

        if(index < 0) {
            System.out.println("index cannot be lower then 0");
            return false;
        }

        return true;
    }

    public void setGroupAsStudent() {
        this.isTeacherGroup = false;
    }

    public void setGroupAsTeacher() {
        this.isTeacherGroup = true;
    }

    public boolean isGroupTeacher() {
        return this.isTeacherGroup;
    }

    public boolean isGroupStudent() {
        return !this.isTeacherGroup;
    }
}
