package model;

import model.entity.Agenda;
import model.entity.ScheduleItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
        con.setRequestProperty("scheduleItem", scheduleItem.toString());

        this.sendRequest(con);
    }

    public void updateScheduleItem(int scheduleItemId, ScheduleItem scheduleItem) throws IOException {

        HttpURLConnection con = this.buildConnection("update-ScheduleItem", "POST");

        con.setRequestProperty("scheduleItemId", Integer.toString(scheduleItemId));
        con.setRequestProperty("scheduleItem", scheduleItem.toString());

        this.sendRequest(con);
    }

    public void deleteScheduleItem(int scheduleItemId) throws IOException {

        HttpURLConnection con = this.buildConnection("delete-ScheduleItem", "POST");

        con.setRequestProperty("scheduleItemId", Integer.toString(scheduleItemId));

        this.sendRequest(con);
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

    private void sendRequest(HttpURLConnection con) throws IOException {

        OutputStream output = con.getOutputStream();
        output.flush();
        output.close();
    }
}
