package model;

import model.entity.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JSONModel {

    // TODO: delete when database connection is ready
    public JSONObject parseJSON(String json) {

        JSONObject file = null;
        JSONParser parser = new JSONParser();

        try {

            file = (JSONObject) parser.parse(json);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return file;
    }

    public Agenda convertJSONAgenda(JSONObject jsonAgenda) {

        Agenda agenda = new Agenda();
        agenda.setId(Math.toIntExact((long) jsonAgenda.get("id")));
        agenda.setName((String) jsonAgenda.get("name"));
        agenda.setGroups(this.convertJSONGroups((JSONArray) jsonAgenda.get("groups")));

        return agenda;
    }

    private ArrayList<Group> convertJSONGroups(JSONArray jsonGroups) {

        ArrayList<Group> groups = new ArrayList<>();

        for (Object objectGroup : jsonGroups)
            groups.add(this.convertJSONGroup((JSONObject) objectGroup));

        return groups;
    }

    private Group convertJSONGroup(JSONObject jsonGroup) {

        Group group = new Group();
        group.setId(Math.toIntExact((long) jsonGroup.get("id")));
        group.setName((String) jsonGroup.get("name"));
        group.setMembers(this.convertJSONMembers((JSONArray) jsonGroup.get("members")));
        group.setSchedules(this.convertJSONSchedules((JSONArray) jsonGroup.get("schedules")));
        group.setTeacherGroup((boolean) jsonGroup.get("isTeacherGroup"));

        return group;
    }

    private ArrayList<Person> convertJSONMembers(JSONArray jsonMembers) {

        ArrayList<Person> people = new ArrayList<>();

        for (Object objectMember : jsonMembers)
            people.add(this.convertJSONMember((JSONObject) objectMember));

        return people;
    }

    private Person convertJSONMember(JSONObject jsonMember) {

        Person person = new Person();
        person.setId(Math.toIntExact((long) jsonMember.get("id")));
        person.setIsTeacher((boolean) jsonMember.get("isTeacher"));
        person.setName((String) jsonMember.get("name"));
        person.setGender((String) jsonMember.get("gender"));
        person.setMemberID((long) jsonMember.get("personID"));

        return person;
    }

    private ArrayList<Schedule> convertJSONSchedules(JSONArray jsonSchedules) {

        ArrayList<Schedule> schedules = new ArrayList<>();

        for (Object objectSchedule : jsonSchedules)
            schedules.add(this.convertJSONSchedule((JSONObject) objectSchedule));

        return schedules;
    }

    private Schedule convertJSONSchedule(JSONObject jsonSchedule) {

        Schedule schedule = new Schedule();
        schedule.setId(Math.toIntExact((long) jsonSchedule.get("id")));
        schedule.setDate(LocalDateTime.parse((String) jsonSchedule.get("date")));
        schedule.setScheduleItems(this.convertJSONScheduleItems((JSONArray) jsonSchedule.get("scheduleItems")));

        return schedule;
    }

    private ArrayList<ScheduleItem> convertJSONScheduleItems(JSONArray jsonScheduleItems) {

        ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();

        for (Object objectScheduleItem : jsonScheduleItems)
            scheduleItems.add(this.convertJSONScheduleItem((JSONObject) objectScheduleItem));

        return scheduleItems;
    }

    private ScheduleItem convertJSONScheduleItem(JSONObject jsonScheduleItem) {

        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setId(Math.toIntExact((long) jsonScheduleItem.get("id")));
        scheduleItem.setName((String) jsonScheduleItem.get("name"));
        scheduleItem.setClassroom(this.convertJSONClassroom((JSONObject) jsonScheduleItem.get("classroom")));
        scheduleItem.setStart(LocalDateTime.parse((String) jsonScheduleItem.get("start")));
        scheduleItem.setEnd(LocalDateTime.parse((String) jsonScheduleItem.get("end")));
        scheduleItem.setTeacher(this.convertJSONMember((JSONObject) jsonScheduleItem.get("teacher")));

        return scheduleItem;
    }

    private ArrayList<Classroom> convertJSONClassrooms(JSONArray jsonClassrooms) {

        ArrayList<Classroom> classrooms = new ArrayList<>();

        for (Object objectClassroom : jsonClassrooms)
            classrooms.add(this.convertJSONClassroom((JSONObject) objectClassroom));

        return classrooms;
    }

    private Classroom convertJSONClassroom(JSONObject jsonClassroom) {

        Classroom classroom = new Classroom();
        classroom.setId(Math.toIntExact((long) jsonClassroom.get("id")));
        classroom.setName((String) jsonClassroom.get("name"));

        return classroom;
    }
}