package shit;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Period implements iSchedule{

    protected long id;
    protected long starting;
    protected long ending;
    protected PriorityQueue<Event> events;

    public Period(long id, long starting, long ending) {
        this.id = id;
        this.starting = starting;
        if (ending < starting) {
            System.out.println("ERROR, period "+id+" has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        events = new PriorityQueue<>();
    }

    public Period(long id, long starting, long ending, PriorityQueue<Event> events) {
        this.id = id;
        this.starting = starting;
        if (ending < starting) {
            System.out.println("ERROR, period "+id+" has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.events = events;
    }

    public long getId() {
        return id;
    }

    public long getStarting() {
        return starting;
    }

    public long getEnding() {
        return ending;
    }

    public PriorityQueue<Event> getEvents() {
        return events;
    }

    public void setEnding(long ending) {
        this.ending = ending;
    }

    /**
     * If necessary, try not to use this!!!!!!!!!!!
     * @param events new events that would replace the old ones
     */
    public void setEvents(PriorityQueue<Event> events) {
        this.events = events;
    }

    @Override
    public boolean addEvent(Event e) {
        return events.add(e);
    }

    @Override
    public boolean addDailyEvent(Event e) {
        long period = e.getPeriod();
        long startFrom = e.getStarting();
        if (startFrom < starting || startFrom > ending) {
            System.out.println("The event is too early or too late for the SHIT.");
            return false;
        }
        Event tempEvent;
        while (startFrom < ending) {
            //as new event has just different id, starting and ending time:
            e.setStarting(startFrom);
            e.setEnding(startFrom + period);
            tempEvent = e.getCopy();//deep copy method has already changed the id
            if(!events.add(tempEvent))
                    return false;
            //increatment the startFrom by one day
            startFrom += DAY;
        }
        return true;
    }

    /**
     * add weekly event(the event starting time will indicate the starting 
     * week day and starting time(exat time during the day) and ending time
     * @param e
     * @param weekDays
     * @return 
     */
    @Override
    public boolean addWeeklyEvent(Event e, int weekDays) {
        if(weekDays>127||weekDays<1)
            //bin(127)=1111111 0000000 indicates no weekday would have event happen
            return false;
        if(e.getStarting()<starting||e.getEnding()>=ending||e.getPeriod()>DAY)
            //invalid starting, ending time or invalid event period(>DAY)
            return false;
        String weekDaysIndicator = iSchedule.convertDecToBin(weekDays);
        if(weekDaysIndicator.length()>7)
            return false;
        //patch up
        int less = 7-weekDaysIndicator.length();
        for(int i=0;i<less;i++){
            weekDaysIndicator = "0"+weekDaysIndicator;
        }
        long startFrom = e.getStarting();
        long period = e.getPeriod();
        Date d = new Date(startFrom);
        int day = d.getDay();
        Event current;
        for (int i = day; i < weekDaysIndicator.length(); i++) {
            if (weekDaysIndicator.charAt(i) == '1') {
                e.setStarting(startFrom);
                e.setEnding(startFrom + period);
                current = e.getCopy();//deep copy method has already changed the id
                if (!events.add(current)) {
                    return false;
                }
                //increatment startFrom by one day
                startFrom += DAY;
            }
        }
        return true;
    }

    @Override
    public boolean addMonthlyEvent(Event e, List<Integer> days) {
        return false;
    }

    @Override
    public boolean addYearlyEvent(Event e, List<Date> dates) {
        return false;
    }

    @Override
    public boolean generateFile(String fileName) {
        return false;
    }

    @Override
    public void iterateEvents() {
        long earlyAlermPeriod = 0;
        for (Iterator<Event> it = events.iterator(); it.hasNext();) {
            Event current = it.next();
            /*TODO:
            do earlyAlerm;
            use event runner to run the event(differnet thread)
            next one..
            */
        }
    }

    @Override
    public void alert(Event e, String message) {
        //do something here
        return ;
    }

    @Override
    public DaySchedule getDaySchedule(Date date) {
        return null;
    }

    @Override
    public WeekSchedule getWeekShedule(Date from) {
        return null;
    }

    @Override
    public MonthSchedule getMonthSchedule(Date month) {
        return null;
    }
}
