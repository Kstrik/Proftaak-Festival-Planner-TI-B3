package model;

import model.entity.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JSONModel {

    public JSONArray readJSONArray(String fileName) {

        JSONArray file = null;

        JSONParser parser = new JSONParser();

        String path = "src/data/" + fileName + ".json";

        try                 { file = (JSONArray) parser.parse(new FileReader(path)); }
        catch (Exception e) { e.printStackTrace(); }

        return file;
    }

    public JSONObject readJSONObject(String fileName) {

        JSONObject file = null;
        JSONParser parser = new JSONParser();

        String path = "src/data/" + fileName + ".json";

        try                 { file = (JSONObject) parser.parse(new FileReader(path)); }
        catch (Exception e) { e.printStackTrace(); }

        return file;
    }

    public Agenda convertJSONAgenda(JSONObject jsonAgenda) {

        Agenda agenda = new Agenda();
        agenda.setId(Math.toIntExact((long) jsonAgenda.get("id")));
        agenda.setName((String) jsonAgenda.get("name"));
        agenda.setGroups(this.convertJSONGroups((JSONArray) jsonAgenda.get("groups")));

        return agenda;
    }

    public ArrayList<Group> convertJSONGroups(JSONArray jsonGroups) {

        ArrayList<Group> groups = new ArrayList<>();

        for (Object objectGroup : jsonGroups)
            groups.add(this.convertJSONGroup((JSONObject) objectGroup));

        return groups;
    }

    public Group convertJSONGroup(JSONObject jsonGroup) {

        Group group = new Group();
        group.setId(Math.toIntExact((long) jsonGroup.get("id")));
        group.setName((String) jsonGroup.get("name"));
        group.setMembers(this.convertJSONMembers((JSONArray) jsonGroup.get("members")));
        group.setSchedules(this.convertJSONSchedules((JSONArray) jsonGroup.get("schedules")));
        group.setTeacherGroup((boolean) jsonGroup.get("isTeacherGroup"));

        return group;
    }

    public ArrayList<Member> convertJSONMembers(JSONArray jsonMembers) {

        ArrayList<Member> members = new ArrayList<>();

        for (Object objectMember : jsonMembers)
            members.add(this.convertJSONMember((JSONObject) objectMember));

        return members;
    }

    public Member convertJSONMember(JSONObject jsonMember) {

        Member member = new Member();
        member.setId(Math.toIntExact((long) jsonMember.get("id")));
        member.setName((String) jsonMember.get("name"));
        member.setGender((String) jsonMember.get("gender"));
        member.setMemberID((long) jsonMember.get("memberNumber"));
        member.setIsTeacher((boolean) jsonMember.get("isTeacher"));

        return member;
    }

    public ArrayList<Schedule> convertJSONSchedules(JSONArray jsonSchedules) {

        ArrayList<Schedule> schedules = new ArrayList<>();

        for (Object objectSchedule : jsonSchedules)
            schedules.add(this.convertJSONSchedule((JSONObject) objectSchedule));

        return schedules;
    }

    public Schedule convertJSONSchedule(JSONObject jsonSchedule) {

        Schedule schedule = new Schedule();
        schedule.setId(Math.toIntExact((long) jsonSchedule.get("id")));
        schedule.setDate(LocalDateTime.parse((String) jsonSchedule.get("date")));
        schedule.setScheduleItems(this.convertJSONScheduleItems((JSONArray) jsonSchedule.get("scheduleItems")));

        return schedule;
    }

    public ArrayList<ScheduleItem> convertJSONScheduleItems(JSONArray jsonScheduleItems) {

        ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();

        for (Object objectScheduleItem : jsonScheduleItems)
            scheduleItems.add(this.convertJSONScheduleItem((JSONObject) objectScheduleItem));

        return scheduleItems;
    }

    public ScheduleItem convertJSONScheduleItem(JSONObject jsonScheduleItem) {

        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setId(Math.toIntExact((long) jsonScheduleItem.get("id")));
        scheduleItem.setName((String) jsonScheduleItem.get("name"));
        scheduleItem.setClassroom(this.convertJSONClassroom((JSONObject) jsonScheduleItem.get("classroom")));
        scheduleItem.setStart(LocalDateTime.parse((String) jsonScheduleItem.get("start")));
        scheduleItem.setEnd(LocalDateTime.parse((String) jsonScheduleItem.get("end")));
        scheduleItem.setTeacher(this.convertJSONMember((JSONObject) jsonScheduleItem.get("teacher")));

        return scheduleItem;
    }

    public ArrayList<Classroom> convertJSONClassrooms(JSONArray jsonClassrooms) {

        ArrayList<Classroom> classrooms = new ArrayList<>();

        for (Object objectClassroom : jsonClassrooms)
            classrooms.add(this.convertJSONClassroom((JSONObject) objectClassroom));

        return classrooms;
    }

    public Classroom convertJSONClassroom(JSONObject jsonClassroom) {

        Classroom classroom = new Classroom();
        classroom.setId(Math.toIntExact((long) jsonClassroom.get("id")));
        classroom.setName((String) jsonClassroom.get("name"));

        return classroom;
    }
}