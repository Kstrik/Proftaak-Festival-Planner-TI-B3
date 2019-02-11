package entity;

import java.util.ArrayList;
import java.util.List;

public class AgendaEntity {

    private String name;                 // for instance: Woensdag
    private List<LessonEntity> lessons;  // all lessons of this agenda

    // constructor
    public AgendaEntity() {

        this.lessons = new ArrayList<>();
    }

    // methods
    public void addLesson(LessonEntity lesson) {

        this.lessons.add(lesson);
    }

    public void setLesson(LessonEntity lesson, int key) {

        this.lessons.set(key, lesson);
    }

    public LessonEntity getLesson(int key) {

        return this.lessons.get(key);
    }

    public int getAmountOfLessons() {

        return this.lessons.size();
    }

    public int getAmountOfClassRooms() {

        List<String> classRooms = getAllClassRooms();
        return classRooms.size();
    }

    public List<String> getAllClassRooms() {

        List<String> classRooms = new ArrayList<>();

        for (LessonEntity lesson : this.lessons)
            if (!classRooms.contains(lesson.getClassRoom()))
                classRooms.add(lesson.getClassRoom());

        return classRooms;
    }

    public LessonEntity getEarliestLesson() {

        LessonEntity earliestLesson = this.lessons.get(0);

        for (LessonEntity lesson : this.lessons)
            if (lesson.getStartTime() < earliestLesson.getStartTime())
                earliestLesson = lesson;

        return earliestLesson;
    }

    public double getEarliestLessonStartTime() {

        LessonEntity lesson = this.getEarliestLesson();
        return lesson.getStartTime();
    }

    public LessonEntity getLatestLesson() {

        LessonEntity latestLesson = this.lessons.get(0);

        for (LessonEntity lesson : this.lessons)
            if (lesson.getEndTime() > latestLesson.getEndTime())
                latestLesson = lesson;

        return latestLesson;
    }

    public double getLatestLessonEndTime() {

        LessonEntity lesson = this.getLatestLesson();
        return lesson.getEndTime();
    }

    // setters
    public void setName(String name) {

        this.name = name;
    }

    public void setLessons(List<LessonEntity> lessons) {

        this.lessons = lessons;
    }

    // getters
    public String getName() {

        return name;
    }

    public List<LessonEntity> getLessons() {

        return lessons;
    }

    // overides
    @Override
    public String toString() {

        String string = "agendaName: " + this.name + "\n";

        for (LessonEntity lesson : this.lessons)
            string += lesson + "\n";

        return string;
    }
}
