package model;

import model.entity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AgendaModel {

    private JSONModel jsonModel;

    public AgendaModel() {

        this.jsonModel = new JSONModel();
    }

    // agenda
    public Agenda getAgenda() {

        JSONObject jsonAgenda = jsonModel.readJSONObject("agenda");

        return jsonModel.convertJSONAgenda(jsonAgenda);
    }

    // group
    public ArrayList<Group> getAllGroups() {

        JSONArray jsonGroups = jsonModel.readJSONArray("group");

        return jsonModel.convertJSONGroups(jsonGroups);
    }

    public ArrayList<String> getAllGroupNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Group group : this.getAllGroups())
            if (!names.contains(group.getName()))
                names.add(group.getName());

        return names;
    }

    public Group getGroupByName(String name) {

        for (Group group : this.getAllGroups())
            if (group.getName().equals(name))
                return group;

        return null;
    }

    // schedule
    public ArrayList<Schedule> getAllSchedules() {

        JSONArray jsonSchedules = jsonModel.readJSONArray("schedule");

        return jsonModel.convertJSONSchedules(jsonSchedules);
    }

    public Schedule getCombinedScheduleByDate(LocalDateTime date) {

        Schedule combinedSchedule = new Schedule(-1, date);

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.getDate().equals(date))
                combinedSchedule.addScheduleItems(schedule.getScheduleItems());

        return combinedSchedule;
    }

    public Schedule getScheduleByDateFromGroup(Group group, LocalDateTime date) {

        for (Schedule schedule : group.getSchedules())
            if (schedule.getDate().equals(date))
                return schedule;

        return null;
    }

    public ArrayList<LocalDateTime> getAllScheduleDates() {

        ArrayList<LocalDateTime> dates = new ArrayList<>();

        for (Schedule schedule : this.getAllSchedules())
            if (!dates.contains(schedule.getDate()))
                dates.add(schedule.getDate());

        return dates;
    }

    public LocalDateTime getFirstScheduleDate() {

        LocalDateTime firstDate = LocalDateTime.now();

        for (LocalDateTime localDateTime : this.getAllScheduleDates())
            if (firstDate.isAfter(localDateTime))
                firstDate = localDateTime;

        return firstDate;
    }

    public Schedule getFirstSchedule() {

        return this.getCombinedScheduleByDate(this.getFirstScheduleDate());
    }

    // scheduleItem
    public ArrayList<ScheduleItem> getAllScheduleItems() {

        JSONArray jsonScheduleItems = jsonModel.readJSONArray("scheduleItem");

        return jsonModel.convertJSONScheduleItems(jsonScheduleItems);
    }

    public String getGroupNameOfScheduleItem(ScheduleItem scheduleItem) {

        for (Group group : this.getAllGroups())
            if (group.containsScheduleItem(scheduleItem))
                return group.getName();

        return null;
    }

    public LocalDateTime getDateOfScheduleItem(ScheduleItem scheduleItem) {

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.containsScheduleItem(scheduleItem))
                return schedule.getDate();

        return null;
    }

    public ScheduleItem getScheduleItemById(int id) {

        for (ScheduleItem scheduleItem : this.getAllScheduleItems())
            if (scheduleItem.getId() == id)
                return scheduleItem;

        return null;
    }

    // classroom
    public ArrayList<Classroom> getAllClassrooms() {

        JSONArray jsonClassrooms = jsonModel.readJSONArray("classroom");

        return jsonModel.convertJSONClassrooms(jsonClassrooms);
    }

    public ArrayList<String> getAllClassroomNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Classroom classroom : this.getAllClassrooms())
            if (!names.contains(classroom.getName()))
                names.add(classroom.getName());

        return names;
    }

    public Classroom getClassroomByName(String name) {

        for (Classroom classroom : this.getAllClassrooms())
            if (classroom.getName().equals(name))
                return classroom;

        return null;
    }

    public int getAmountOfClassrooms() {

        return this.getAllClassrooms().size();
    }

    public int getClassRoomKey(Classroom searchedClassroom) {

        ArrayList<Classroom> classrooms = this.getAllClassrooms();

        for (Classroom room : classrooms)
            if (room.getId() == searchedClassroom.getId())
                return classrooms.indexOf(room);

        return -1;
    }

    // student
    public ArrayList<Member> getAllStudents() {

        JSONArray jsonTeachers = jsonModel.readJSONArray("student");

        return jsonModel.convertJSONMembers(jsonTeachers);
    }

    public Member getStudentByName(String name) {

        for (Member student : this.getAllTeachers())
            if (student.getName().equals(name))
                return student;

        return null;
    }

    // teachers
    public ArrayList<Member> getAllTeachers() {

        JSONArray jsonTeachers = jsonModel.readJSONArray("teacher");

        return jsonModel.convertJSONMembers(jsonTeachers);
    }

    public ArrayList<String> getAllTeacherNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Member teacher : this.getAllTeachers())
            if (!names.contains(teacher.getName()))
                names.add(teacher.getName());

        return names;
    }

    public Member getTeacherByName(String name) {

        for (Member teacher : this.getAllTeachers())
            if (teacher.getName().equals(name))
                return teacher;

        return null;
    }
}
