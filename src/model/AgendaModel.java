package model;

import entity.AgendaEntity;
import entity.LessonEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class AgendaModel {

    public AgendaEntity getAgendaWithJSONFile(String fileName) {

        AgendaEntity agenda = null;

        JSONParser parser = new JSONParser();

        String path = "src/files/" + fileName + ".json";

        try {

            agenda = convertJSON((JSONObject) parser.parse(new FileReader(path)));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return agenda;
    }

    private AgendaEntity convertJSON(JSONObject jsonAgenda) {

        AgendaEntity agenda = new AgendaEntity();

        agenda.setName((String) jsonAgenda.get("name"));

        JSONArray jsonLessons = (JSONArray) jsonAgenda.get("schedule");

        for (Object object : jsonLessons) {

            JSONObject jsonLesson = (JSONObject) object;

            LessonEntity lessonEntity = new LessonEntity();

            lessonEntity.setName((String) jsonLesson.get("name"));
            lessonEntity.setGroup((String) jsonLesson.get("group"));
            lessonEntity.setTeacher((String) jsonLesson.get("teacher"));
            lessonEntity.setClassRoom((String) jsonLesson.get("classRoom"));
            lessonEntity.setStartTime(Double.parseDouble((String) jsonLesson.get("startTime")));
            lessonEntity.setEndTime(Double.parseDouble((String) jsonLesson.get("endTime")));

            agenda.addLesson(lessonEntity);
        }

        return agenda;
    }
}
