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

    // TODO: Remove on complete database connection
    public JSONObject parseJSONFile(String fileName) {

        JSONObject file = null;
        JSONParser parser = new JSONParser();

        try {

            file = (JSONObject) parser.parse(new FileReader("json/" + fileName + ".json"));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return file;
    }

    public JSONObject parseJSON(String jsonString) {

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
        schedule.setItems(this.convertJSONItems((JSONArray) jsonSchedule.get("items")));

        return schedule;
    }

    private ArrayList<Item> convertJSONItems(JSONArray jsonItems) {

        ArrayList<Item> items = new ArrayList<>();

        for (Object objectItem : jsonItems)
            items.add(this.convertJSONItem((JSONObject) objectItem));

        return items;
    }

    private Item convertJSONItem(JSONObject jsonItem) {

        Item item = new Item();
        item.setId(Math.toIntExact((long) jsonItem.get("id")));
        item.setName((String) jsonItem.get("name"));
        item.setClassroom(this.convertJSONClassroom((JSONObject) jsonItem.get("classroom")));
        item.setStart(LocalDateTime.parse((String) jsonItem.get("start")));
        item.setEnd(LocalDateTime.parse((String) jsonItem.get("end")));
        item.setTeacher(this.convertJSONMember((JSONObject) jsonItem.get("teacher")));

        return item;
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