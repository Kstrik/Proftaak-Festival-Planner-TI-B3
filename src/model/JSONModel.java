package model;

import model.entity.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class JSONModel {

    // TODO: Remove on complete database connection
    public JSONObject parseJSONFile(String fileName) {

        JSONObject file = null;
        JSONParser parser = new JSONParser();

        try {

            file = (JSONObject) parser.parse(new FileReader("src/json/" + fileName + ".json"));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return file;
    }

    public JSONObject parseJSON(String jsonString) {

        System.out.println(jsonString);

        JSONObject json = null;
        JSONParser parser = new JSONParser();

        try {

            json = (JSONObject) parser.parse(jsonString);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return json;
    }

    public Agenda convertJSONAgenda(JSONObject jsonAgenda) {

        Agenda agenda = new Agenda();
//        agenda.setId(Math.toIntExact((long) jsonAgenda.get("id")));
//        agenda.setName((String) jsonAgenda.get("name"));
        agenda.setGroups(this.convertJSONGroups((JSONArray) jsonAgenda.get("groups")));

        return agenda;
    }

    private ArrayList<Group> convertJSONGroups(JSONArray jsonGroups) {

        ArrayList<Group> groups = new ArrayList<>();

        if (jsonGroups == null)
            return groups;

        for (Object objectGroup : jsonGroups)
            groups.add(this.convertJSONGroup((JSONObject) objectGroup));

        return groups;
    }

    private Group convertJSONGroup(JSONObject jsonGroup) {

        Group group = new Group();
        group.setId(Math.toIntExact((long) jsonGroup.get("id")));
        group.setName((String) jsonGroup.get("name"));
        group.setMembers(this.convertJSONMembers((JSONArray) jsonGroup.get("members"), (long) jsonGroup.get("isTeacherGroup") > 0));
        group.setSchedules(this.convertJSONSchedules((JSONArray) jsonGroup.get("schedules")));
        group.setTeacherGroup((long) jsonGroup.get("isTeacherGroup") > 0);

        return group;
    }

    private ArrayList<Person> convertJSONMembers(JSONArray jsonMembers, boolean isTeacher) {

        ArrayList<Person> people = new ArrayList<>();

        if (jsonMembers == null)
            return people;

        for (Object objectMember : jsonMembers)
            people.add(this.convertJSONMember((JSONObject) objectMember, isTeacher));

        return people;
    }

    private Person convertJSONMember(JSONObject jsonMember, boolean isTeacher) {

        Person person = new Person();
        person.setId(Math.toIntExact((long) jsonMember.get("id")));
        person.setName((String) jsonMember.get("name"));
        person.setGender((String) jsonMember.get("gender"));
        person.setMemberID((long) jsonMember.get("person_id"));

        if (isTeacher)
            person.setIsTeacher(true);

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
        schedule.setDate(LocalDateTime.parse(((String) jsonSchedule.get("date")).replace(" ", "T")));
        schedule.setItems(this.convertJSONItems((JSONArray) jsonSchedule.get("items")));

        return schedule;
    }

    private ArrayList<Item> convertJSONItems(JSONArray jsonItems) {

        ArrayList<Item> items = new ArrayList<>();

        if (jsonItems == null)
            return items;

        for (Object objectItem : jsonItems)
            items.add(this.convertJSONItem((JSONObject) objectItem));

        return items;
    }

    private Item convertJSONItem(JSONObject jsonItem) {

        Item item = new Item();
        item.setId(Math.toIntExact((long) jsonItem.get("id")));
        item.setName((String) jsonItem.get("name"));
        item.setClassroom(this.convertJSONClassroom((JSONObject) jsonItem.get("classroom")));
        item.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.parse((String) jsonItem.get("start"))));
        item.setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.parse((String) jsonItem.get("end"))));
        item.setTeacher(this.convertJSONMember((JSONObject) jsonItem.get("teacher"), true));

        return item;
    }

    private ArrayList<Classroom> convertJSONClassrooms(JSONArray jsonClassrooms) {

        ArrayList<Classroom> classrooms = new ArrayList<>();

        if (jsonClassrooms == null)
            return classrooms;

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