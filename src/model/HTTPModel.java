package model;

import model.entity.Agenda;
import model.entity.Item;

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
        System.out.println(response);

        JSONModel jsonModel = new JSONModel();

        return jsonModel.convertJSONAgenda(jsonModel.parseJSON(response));
    }

    public void createItem(int groupId, LocalDateTime scheduleDate, Item item) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/item/create", "POST");

        con.setRequestProperty("groupId", Integer.toString(groupId));
        con.setRequestProperty("date", scheduleDate.toString());
        con.setRequestProperty("scheduleItem", item.toString());

        this.sendRequest(con);
    }

    public void updateItem(int groupId, LocalDateTime scheduleDate, Item item) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/item/update/" + item.getId(), "POST");

        con.setRequestProperty("groupId", Integer.toString(groupId));
        con.setRequestProperty("date", scheduleDate.toString());
        con.setRequestProperty("scheduleItem", item.toString());

        this.sendRequest(con);
    }

    public void deleteItem(int scheduleItemId) throws IOException {

        HttpURLConnection con = this.buildConnection("schedule/item/delete/" + scheduleItemId, "POST");

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
