package model;

import model.entity.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HTTPModel {

    // agenda
    public Agenda getAgenda() throws IOException {

        HttpURLConnection con = this.buildConnection("agenda", "GET");

        String response = this.getResponse(con);

        JSONModel jsonModel = new JSONModel();

        return jsonModel.convertJSONAgenda(jsonModel.parseJSON(response));
    }

    // classroom
    public void changeClassroom(Classroom classroom, String path) throws IOException {

        HttpURLConnection con = this.buildConnection("classroom/" + path + "/" + classroom.getId(), "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(classroom.toString());

        this.sendRequest(con, data);
    }

    public void deleteClassroom(int classroomId) throws IOException {

        HttpURLConnection con = this.buildConnection("classroom/delete/" + classroomId, "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(Integer.toString(classroomId));

        this.sendRequest(con, data);
    }

    // group
    public void changeGroup(Group group, String path) throws IOException {

        HttpURLConnection con = this.buildConnection("group/" + path + "/" + group.getId(), "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(group.toString());

        this.sendRequest(con, data);
    }

    public void deleteGroup(int groupId) throws IOException {

        HttpURLConnection con = this.buildConnection("group/delete/" + groupId, "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(Integer.toString(groupId));

        this.sendRequest(con, data);
    }

    // item
    public void changeItem(Group group, Schedule schedule, Item item, String path) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/item/" + path + "/" + item.getId(), "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(group.toString());
        data.add(schedule.toString());
        data.add(item.toString());

        this.sendRequest(con, data);
    }

    public void deleteItem(int itemId) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/item/delete/" + itemId, "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(Integer.toString(itemId));

        this.sendRequest(con, data);
    }

    // person
    public void changePerson(Group group, Person person, String path) throws IOException {

        HttpURLConnection con = this.buildConnection("person/" + path + "/" + group.getId(), "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(group.toString());
        data.add(person.toString());

        this.sendRequest(con, data);
    }

    public void deletePerson(int personId) throws IOException {

        HttpURLConnection con = this.buildConnection("person/delete/" + personId, "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(Integer.toString(personId));

        this.sendRequest(con, data);
    }

    // schedule
    public void changeSchedule(Group group, Schedule schedule, String path) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/" + path + "/" + group.getId(), "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(group.toString());
        data.add(schedule.toString());

        this.sendRequest(con, data);
    }

    public void deleteSchedule(int scheduleId) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/delete/" + scheduleId, "POST");

        ArrayList<String> data = new ArrayList<>();

        data.add(Integer.toString(scheduleId));

        this.sendRequest(con, data);
    }

    // methods
    private HttpURLConnection buildConnection(String path, String method) throws IOException {

        URL url = new URL("http://" + ConfigModel.HOST + "/" + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        return con;
    }

    private String getResponse(HttpURLConnection con) throws IOException {

        String line;
        StringBuilder response = new StringBuilder();

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

            InputStreamReader input = new InputStreamReader(con.getInputStream());
            BufferedReader reader = new BufferedReader(input);

            while ((line = reader.readLine()) != null) {

                response.append(line);
            }

//            input.close();
        } else {

            throw new IOException();
        }

        return response.toString();
    }

    private void sendRequest(HttpURLConnection con, ArrayList<String> params) throws IOException {

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Host", ConfigModel.HOST);
        con.setDoOutput(true);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());

        for (String param : params)
            outputStreamWriter.write(param);

        outputStreamWriter.flush();

        this.log(con);

        outputStreamWriter.close();
    }

    private void log(HttpURLConnection con) throws IOException {

        System.out.println("\nSending 'POST' request to URL : " + con.getURL());
        System.out.println("Response Code : " + con.getResponseCode());

        System.out.println(this.getResponse(con));
    }
}
