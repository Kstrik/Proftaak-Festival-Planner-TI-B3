package model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Agenda {

    private int id;
    private String name;
    private ArrayList<Classroom> classrooms;
    private ArrayList<Group> groups;

    public Agenda (int id, String name, ArrayList<Group> groups) {

        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public Agenda () {

        this.id = -1;
        this.name = "";
        this.groups = new ArrayList<>();
    }

    // group
    public ArrayList<Group> getAllGroups() {

        return this.groups;
    }

    public ArrayList<String> getAllGroupNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Group group : this.groups)
            if (!names.contains(group.getName()))
                names.add(group.getName());

        return names;
    }

    public Group getGroupBySchedule(Schedule schedule) {

        for (Group group : this.groups)
            if (group.getSchedules().contains(schedule))
                return group;

        return new Group();
    }

    public Group getGroupByPerson(Person person) {

        for (Group group : this.groups)
            if (group.getMembers().contains(person))
                return group;

        return new Group();
    }

    public Group getGroupByItem(Item item) {

        for (Group group : this.groups)
            if (group.containsItem(item))
                return group;

        return new Group();
    }

    public Group getGroupByName(String name) {

        for (Group group : this.groups)
            if (group.getName().equals(name))
                return group;

        return new Group();
    }

    public void setGroup(Group setGroup) {

        for (Group group : this.groups)
            if (group.getId() == setGroup.getId()) {

                setGroup.setMembers(group.getMembers());
                setGroup.setSchedules(group.getSchedules());
                this.groups.remove(group);
            }

        this.groups.add(setGroup);
    }

    public void deleteGroup(int groupId) {

        for (Group group : this.groups)
            if (group.getMembers().size() == 0 && group.getSchedules().size() == 0 && group.getId() == groupId)
                this.groups.remove(group);
    }

    public int getNewGroupId() {

        int highestId = 0;

        for (Group group : this.groups)
            if (group.getId() > highestId)
                highestId = group.getId();

        return highestId + 1;
    }

    // schedule
    public ArrayList<Schedule> getAllSchedules() {

        HashMap<Integer, Schedule> schedules = new HashMap<>();

        for (Group group : this.groups)
            for (Schedule schedule : group.getSchedules())
                schedules.put(schedule.getId(), schedule);

        return new ArrayList<Schedule>() {{ addAll(schedules.values()); }};
    }

    public Schedule getCombinedScheduleByDate(LocalDateTime date) {

        Schedule combinedSchedule = new Schedule(-1, date, new ArrayList<>());

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.getDate().equals(date))
                combinedSchedule.setItems(schedule.getItems());

        return combinedSchedule;
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

    public void setSchedule(Group setGroup, Schedule setSchedule) {

        for (Group group : this.groups)
            for (Schedule schedule : group.getSchedules())
                if (schedule.getId() == setSchedule.getId()) {

                    setSchedule.setItems(schedule.getItems());
                    group.getSchedules().remove(schedule);
                }

        for (Group group : this.groups)
            if (group.getId() == setGroup.getId())
                group.getSchedules().add(setSchedule);
    }

    public void deleteSchedule(int scheduleId) {

        for (Group group : this.groups)
            for (Schedule schedule : group.getSchedules())
                if (schedule.getItems().size() == 0 && schedule.getId() == scheduleId)
                    group.getSchedules().remove(schedule);
    }

    public int getNewScheduleId() {

        int highest = 0;

        for (Schedule schedule : this.getAllSchedules())
            if (schedule.getId() > highest)
                highest = schedule.getId();

        return highest + 1;
    }

    // Item
    public ArrayList<Item> getAllItems() {

        HashMap<Integer, Item> items = new HashMap<>();

        for (Schedule schedule : this.getAllSchedules())
            for (Item item : schedule.getItems())
                    items.put(item.getId(), item);

        return new ArrayList<Item>() {{ addAll(items.values()); }};
    }

    public void setItem(Group setGroup, Schedule setSchedule, Item setItem) {

        for (Schedule schedule : this.getAllSchedules())
            for (Item item : schedule.getItems())
                if (item.getId() == setItem.getId())
                    schedule.getItems().remove(item);

        for (Group group : this.groups)
            if (group.getId() == setGroup.getId())
                for (Schedule schedule : group.getSchedules())
                    if (schedule.getId() == setSchedule.getId())
                        schedule.getItems().add(setItem);
    }

    public void deleteItem(int itemId) {

        for (Schedule schedules : this.getAllSchedules())
            for (Item item : schedules.getItems())
                if (item.getId() == itemId)
                    schedules.getItems().remove(item);
    }

    public int getNewItemId() {

        int highest = 0;

        for (Item item : this.getAllItems())
            if (item.getId() > highest)
                highest = item.getId();

        return highest + 1;
    }

    // classroom
    public ArrayList<Classroom> getAllClassrooms() {

        return this.classrooms;
    }

    public ArrayList<String> getAllClassroomNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Classroom classroom : this.classrooms)
            if (!names.contains(classroom.getName()))
                names.add(classroom.getName());

        return names;
    }

    public Classroom getClassroomByName(String name) {

        for (Classroom classroom : this.classrooms)
            if (classroom.getName().equals(name))
                return classroom;

        return new Classroom();
    }

    public int getAmountOfClassrooms() {

        return this.classrooms.size();
    }

    public int getClassRoomKey(int classroomId) {

        ArrayList<Classroom> classrooms = this.classrooms;

        for (Classroom room : classrooms)
            if (room.getId() == classroomId)
                return classrooms.indexOf(room);

        return -1;
    }

    public Classroom getClassroomById(int classroomId) {

        for (Classroom classroom : this.getAllClassrooms())
            if (classroom.getId() == classroomId)
                return classroom;

        return new Classroom();
    }

    public void setClassroom(Classroom setClassroom) {

        for (Classroom classroom : this.classrooms)
            if (classroom.getId() == setClassroom.getId())
                this.classrooms.remove(classroom);

        this.classrooms.add(setClassroom);
    }

    public void deleteClassroom(int classroomId) {

        boolean contains = false;

        for (Item item : this.getAllItems())
            if (item.getClassroomId() == classroomId)
                contains = true;

        if (!contains)
            for (Classroom classroom : this.classrooms)
                if (classroom.getId() == classroomId)
                    this.classrooms.remove(classroom);
    }

    public int getNewClassroomId() {

        int highest = 0;

        for (Classroom classroom : this.classrooms)
            if (classroom.getId() > highest)
                highest = classroom.getId();

        return highest + 1;
    }

    // person
    public ArrayList<Person> getAllPersons() {

        HashMap<Integer, Person> persons = new HashMap<>();

        for (Group group : this.groups)
            for (Person person : group.getMembers())
                    persons.put(person.getId(), person);

        return new ArrayList<Person>() {{ addAll(persons.values()); }};
    }

    public void setPerson(Group setGroup, Person setPerson) {

        for (Group group : this.groups)
            for (Person person : group.getMembers())
                if (person.getId() == setPerson.getId())
                    group.getMembers().remove(person);

        this.getGroupByName(setGroup.getName()).getMembers().add(setPerson);
    }

    public void deletePerson(int personId) {

        boolean contains = false;

        for (Item item : this.getAllItems())
            if (item.getId() == personId)
                contains = true;

        if (!contains)
            for (Group group : this.groups)
                for (Person person : group.getMembers())
                    if (person.getId() == personId)
                        group.getMembers().remove(person);
    }

    public int getNewPersonId() {

        int highest = 0;

        for (Person person : this.getAllPersons())
            if (person.getId() > highest)
                highest = person.getId();

        return highest + 1;
    }

    // teachers
    public ArrayList<Person> getAllTeachers() {

        ArrayList<Person> teachers = new ArrayList<>();

        for (Person teacher : this.getAllPersons())
            if (teacher.isTeacher())
                teachers.add(teacher);

        return teachers;
    }

    public ArrayList<String> getAllTeacherNames() {

        ArrayList<String> names = new ArrayList<>();

        for (Person teacher : this.getAllTeachers())
            if (!names.contains(teacher.getName()))
                names.add(teacher.getName());

        return names;
    }

    public Person getTeacherByName(String name) {

        for (Person teacher : this.getAllTeachers())
            if (teacher.getName().equals(name))
                return teacher;

        return new Person();
    }

    public Person getTeacherById(int teacherId) {

        for (Person teacher : this.getAllTeachers())
            if (teacher.getId() == teacherId)
                return teacher;

        return new Person();
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public void setClassrooms(ArrayList<Classroom> classrooms) {

        this.classrooms = classrooms;
    }

    public void setGroups(ArrayList<Group> groups) {

        this.groups = groups;
    }

    // getters
    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    // toString
    @Override
    public String toString() {

        StringBuilder agenda = new StringBuilder();

        agenda.append("{\n");
        agenda.append("\t\"id\": \"")       .append(this.id)                   .append("\",\n");
        agenda.append("\t\"name\": \"")     .append(this.name)                 .append("\",\n");
        agenda.append("\t\"classrooms\": ") .append(this.classroomsToString()) .append(",\n");
        agenda.append("\t\"groups\": ")     .append(this.groupsToString())     .append("\n");
        agenda.append("}");

        return agenda.toString();
    }

    private String classroomsToString() {

        StringBuilder classrooms = new StringBuilder();

        classrooms.append("[\n");
        for (int i = 0; i < this.classrooms.size(); i++)
            classrooms.append(this.classrooms.get(i).toString()).append(i == (this.classrooms.size() - 1) ? "\n" : ",\n");
        classrooms.append("]");

        return classrooms.toString().replace("\n", "\n\t\t");
    }

    private String groupsToString() {

        StringBuilder groups = new StringBuilder();

        groups.append("[\n");
        for (int i = 0; i < this.groups.size(); i++)
            groups.append(this.groups.get(i).toString()).append(i == (this.groups.size() - 1) ? "\n" : ",\n");
        groups.append("]");

        return groups.toString().replace("\n", "\n\t\t");
    }
}
