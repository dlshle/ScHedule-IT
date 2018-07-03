package shit;

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
            startFrom += period;
        }
        return true;
    }

    @Override
    public boolean addWeeklyEvent(Event e, int weekDays) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addMonthlyEvent(Event e, List<Integer> days) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addYearlyEvent(Event e, List<Date> dates) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
