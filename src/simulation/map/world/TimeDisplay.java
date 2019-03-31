package simulation.map.world;

public class TimeDisplay {
    int hours;
    int minutes;
    double seconds;

    public TimeDisplay()
    {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
    }

    public void addSeconds(double seconds)
    {
        this.seconds += seconds;

        if(this.seconds >= 60)
        {
            this.minutes++;
            this.seconds -= 60;
        }

        if(this.minutes >= 60)
        {
            this.hours++;
            this.minutes -= 60;
        }

        if(this.hours >= 24)
        {
            this.hours = 0;
            this.minutes = 0;
            this.seconds -= 60;
        }
    }

    public String toString()
    {
        return (this.hours + ":" + this.minutes + ":" + (int)this.seconds);
    }

    public int getHours() {
        return this.hours;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getSeconds() {
        return (int)this.seconds;
    }
}
