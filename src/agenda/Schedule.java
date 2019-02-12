package agenda;

import java.util.ArrayList;
import java.util.Date;

public class Schedule {
    private Date date;
    private ArrayList<ScheduleItem> items;

    public Schedule(Date date, ArrayList<ScheduleItem> items) {
        this.date = date;
        this.items = items;
    }

    public Schedule(Date date) {
        this.date = date;
        this.items = new ArrayList<>();
    }

    public void addScheduleItem(ScheduleItem item) {
        this.items.add(item);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
