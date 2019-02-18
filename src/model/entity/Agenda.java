package model.entity;

import java.util.ArrayList;

public class Agenda {

    private int id;
    private String name;
    private ArrayList<Group> groups;

    public Agenda(int id, String name, ArrayList<Group> groups) {

        this.id = id;
        this.name = name;
        this.groups = groups;
    }

    public Agenda() {

    }

    // methods
    public void addGroup(Group group) {

        this.groups.add(group);
    }

    public Group getGroup(int key) {

        return this.groups.get(key);
    }

    // setters
    public void setId(int id) {

        this.id = id;
    }

    public void setName(String name) {

        this.name = name;
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

    public ArrayList<Group> getGroups() {

        return this.groups;
    }
}
