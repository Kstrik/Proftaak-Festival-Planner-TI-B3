package model;

import model.agendaEntity.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AgendaModel {

    public Agenda getAgendaWithJSONFile(String fileName) {

        Agenda agenda = null;

        JSONParser parser = new JSONParser();

        String path = "src/files/" + fileName + ".json";

        try {

            agenda = this.convertJSONAgenda((JSONObject) parser.parse(new FileReader(path)));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return agenda;
    }

    private Agenda convertJSONAgenda(JSONObject jsonAgenda) {

        Agenda agenda = new Agenda();
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
        group.setName((String) jsonGroup.get("name"));
        group.setMembers(this.convertJSONMembers((JSONArray) jsonGroup.get("members")));
        group.setSchedules(this.convertJSONSchedules((JSONArray) jsonGroup.get("schedules")));
        group.setTeacherGroup((boolean) jsonGroup.get("isTeacherGroup"));

        return group;
    }

    private ArrayList<Member> convertJSONMembers(JSONArray jsonMembers) {

        ArrayList<Member> members = new ArrayList<>();

        for (Object objectMember : jsonMembers)
            members.add(this.convertJSONMember((JSONObject) objectMember));

        return members;
    }

    private Member convertJSONMember(JSONObject jsonMember) {

        Member member = new Member();
        member.setName((String) jsonMember.get("name"));
        member.setGender((String) jsonMember.get("gender"));
        member.setMemberID((long) jsonMember.get("memberNumber"));
        member.setIsTeacher((boolean) jsonMember.get("isTeacher"));

        return member;
    }

    private ArrayList<Schedule> convertJSONSchedules(JSONArray jsonSchedules) {

        ArrayList<Schedule> schedules = new ArrayList<>();

        for (Object objectSchedule : jsonSchedules)
            schedules.add(this.convertJSONSchedule((JSONObject) objectSchedule));

        return schedules;
    }

    private Schedule convertJSONSchedule(JSONObject jsonSchedule) {

        Schedule schedule = new Schedule();
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
        scheduleItem.setName((String) jsonScheduleItem.get("name"));
        scheduleItem.setGroupID((long) jsonScheduleItem.get("group_id"));
        scheduleItem.setClassroom(this.convertJSONClassroom((JSONObject) jsonScheduleItem.get("classroom")));
        scheduleItem.setStart(LocalDateTime.parse((String) jsonScheduleItem.get("start")));
        scheduleItem.setEnd(LocalDateTime.parse((String) jsonScheduleItem.get("end")));
        scheduleItem.setTeacher(this.convertJSONMember((JSONObject) jsonScheduleItem.get("teacher")));

        return scheduleItem;
    }

    private Classroom convertJSONClassroom(JSONObject jsonClassroom) {

        Classroom classroom = new Classroom();
        classroom.setName((String) jsonClassroom.get("name"));

        return classroom;
    }
}