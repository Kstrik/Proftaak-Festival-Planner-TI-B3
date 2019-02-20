package model;

import model.entity.Agenda;
import model.entity.ScheduleItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

public class HTTPModel {

    public Agenda getAgenda() throws IOException {

        HttpURLConnection con = this.buildConnection("agenda", "GET");

        String response = this.getResponse(con);

        JSONModel jsonModel = new JSONModel();

        return jsonModel.convertJSONAgenda(jsonModel.parseJSON(response));
    }

    public void createScheduleItem(int groupId, LocalDateTime scheduleDate, ScheduleItem scheduleItem) throws IOException {

        HttpURLConnection con = this.buildConnection("create-ScheduleItem", "POST");

        con.setRequestProperty("groupId", Integer.toString(groupId));
        con.setRequestProperty("date", scheduleDate.toString());
        con.setRequestProperty("schedule", scheduleItem.toString());
    }

    public void updateScheduleItem() throws IOException {

        HttpURLConnection con = this.buildConnection("update-ScheduleItem", "POST");
    }

    public void deleteScheduleItem() throws IOException {

        HttpURLConnection con = this.buildConnection("delete-ScheduleItem", "POST");
    }

    private HttpURLConnection buildConnection(String path, String method) throws IOException {

        URL url = new URL("http://api.jijbentzacht.nl:1337/" + path);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);

        return con;
    }

    private String getResponse(HttpURLConnection con) throws IOException {

        String line;
        StringBuilder response = new StringBuilder();

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {

            BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((line = input.readLine()) != null) {

                response.append(line);
            }

            input.close();
        } else {

            throw new IOException();
        }

        return response.toString();
    }
}
