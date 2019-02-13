package model;

import model.agendaEntity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private Agenda convertJSONAgenda(JSONObject jsonAgenda) throws ParseException {

        Agenda agenda = new Agenda();
        agenda.setName((String) jsonAgenda.get("name"));
        agenda.setGroups(this.convertJSONGroups((JSONArray) jsonAgenda.get("groups")));
        agenda.setSchedules(this.convertJSONSchedules((JSONArray) jsonAgenda.get("schedules")));

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
        group.setScheduleItemIDs((ArrayList) jsonGroup.get("scheduledItems_ids"));
        group.setMembers(this.convertJSONMembers((JSONArray) jsonGroup.get("members")));
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
        member.setMemberID((int) jsonMember.get("memberID"));
        member.setIsTeacher((boolean) jsonMember.get("isMember"));

        return member;
    }

    private ArrayList<Schedule> convertJSONSchedules(JSONArray jsonSchedules) throws ParseException {

        ArrayList<Schedule> schedules = new ArrayList<>();

        for (Object objectSchedule : jsonSchedules)
            schedules.add(this.convertJSONSchedule((JSONObject) objectSchedule));

        return schedules;
    }

    private Schedule convertJSONSchedule(JSONObject jsonSchedule) throws ParseException {

        Schedule schedule = new Schedule();
        schedule.setName((String) jsonSchedule.get("name"));
        schedule.setDate(new SimpleDateFormat("yyyy-MM-dd").parse((String) jsonSchedule.get("date")));
        schedule.setScheduleItems(this.convertJSONScheduleItems((JSONArray) jsonSchedule.get("scheduledItems")));

        return schedule;
    }

    private ArrayList<ScheduleItem> convertJSONScheduleItems(JSONArray jsonScheduleItems) throws ParseException {

        ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();

        for (Object objectScheduleItem : jsonScheduleItems)
            scheduleItems.add(this.convertJSONScheduleItem((JSONObject) objectScheduleItem));

        return scheduleItems;
    }

    private ScheduleItem convertJSONScheduleItem(JSONObject jsonScheduleItem) throws ParseException {

        ScheduleItem scheduleItem = new ScheduleItem();
        scheduleItem.setName((String) jsonScheduleItem.get("name"));
        scheduleItem.setGroupID((int) jsonScheduleItem.get("group_id"));
        scheduleItem.setClassroom(this.convertJSONClassroom((JSONObject) jsonScheduleItem.get("classroom")));
        scheduleItem.setStart((Time) new SimpleDateFormat("hh:mm:ss").parse((String) jsonScheduleItem.get("start")));
        scheduleItem.setEnd((Time) new SimpleDateFormat("hh:mm:ss").parse((String) jsonScheduleItem.get("end")));
        scheduleItem.setTeacher(this.convertJSONMember((JSONObject) jsonScheduleItem.get("teacher")));

        return scheduleItem;
    }

    private Classroom convertJSONClassroom(JSONObject jsonClassroom) {

        Classroom classroom = new Classroom();
        classroom.setName((String) jsonClassroom.get("name"));

        return classroom;
    }
}