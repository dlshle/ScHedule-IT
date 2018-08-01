package shit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * TimePeriod is a container of events(basically an event manager)
 * @author amnisia
 * @since 07/03/2018
 */
public class TimePeriod implements iSchedule {

    protected long id;
    protected long starting;
    protected long ending;
    protected PriorityQueue<Event> events;

    public TimePeriod(long id, long starting, long ending) {
        if (iSchedule.TIME_PERIOD_IDS.contains(id)) {
            int newId;
            do {
                newId = (int) (Math.random() * 1000);
            } while (!iSchedule.TIME_PERIOD_IDS.add(newId));
        }
        this.id = id;
        this.starting = starting;
        if (ending < starting) {
            System.out.println("ERROR, period " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        events = new PriorityQueue<>();
    }

    public TimePeriod(long id, Date starting, Date ending) {
        if (iSchedule.TIME_PERIOD_IDS.contains(id)) {
            int newId;
            do {
                newId = (int) (Math.random() * 1000);
            } while (!iSchedule.TIME_PERIOD_IDS.add(newId));
        }
        this.id = id;
        this.starting = starting.getTime();
        if (ending.getTime() < this.starting) {
            System.out.println("ERROR, period " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        } else {
            this.ending = ending.getTime();
        }
        events = new PriorityQueue<>();
    }

    public TimePeriod(long id, long starting, long ending, PriorityQueue<Event> events) {
        if (iSchedule.TIME_PERIOD_IDS.contains(id)) {
            int newId;
            do {
                newId = (int) (Math.random() * 1000);
            } while (!iSchedule.TIME_PERIOD_IDS.add(newId));
        }
        this.id = id;
        this.starting = starting;
        if (ending < starting) {
            System.out.println("ERROR, period " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.events = events;
    }

    public TimePeriod(long id, Date starting, Date ending, PriorityQueue<Event> events) {
        if (iSchedule.TIME_PERIOD_IDS.contains(id)) {
            int newId;
            do {
                newId = (int) (Math.random() * 1000);
            } while (!iSchedule.TIME_PERIOD_IDS.add(newId));
        }
        this.id = id;
        this.starting = starting.getTime();
        if (ending.getTime() < this.starting) {
            System.out.println("ERROR, period " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        } else {
            this.ending = ending.getTime();
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
     *
     * @param events new events that would replace the old ones
     */
    public void setEvents(PriorityQueue<Event> events) {
        this.events = events;
    }

    public boolean isValidEvent(Event e) {
        return (!events.contains(e)) && (e.getStarting() >= starting || e.getEnding() <= ending);
    }

    @Override
    public boolean addEvent(Event e) {
        if (!isValidEvent(e)) {
            return false;
        }
        return events.add(e);
    }

    @Override
    public boolean addEvents(List<Event> events) {
        for (Event e : events) {
            if (!addEvent(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addDailyEvent(Event e) {
        long period = e.getPeriod();
        long startFrom = e.getStarting();
        if (!isValidEvent(e)) {
            System.out.println("The event is too early or too late for the SHIT.");
            return false;
        }
        while (startFrom < ending) {
            //as new event has just different id, starting and ending time:
            e.setStarting(startFrom);
            e.setEnding(startFrom + period);
            Event tempEvent = e.getCopy();
            //System.out.println(DateToolSet.sdf.format(tempEvent.getStartingDate()));
            //System.out.println(DateToolSet.sdf.format(tempEvent.getEndingDate()));
            if (!addEvent(e.getCopy())) {
                //deep copy method has already changed the id
                return false;
            }
            //increatment the startFrom by one day
            startFrom += DAY;
        }
        return true;
    }

    /**
     * add weekly event(the event starting time will indicate the starting week
     * day and starting time(exat time during the day) and ending time
     *
     * @param e
     * @param weekDays
     * @return
     */
    @Override
    public boolean addWeeklyEvent(Event e, int weekDays) {
        if (weekDays > 127 || weekDays < 1) //bin(127)=1111111 0000000 indicates no weekday would have event happen
        {
            return false;
        }
        if ((!isValidEvent(e)) || e.getPeriod() > DAY) //invalid starting, ending time or invalid event period(>DAY)
        {
            return false;
        }
        String weekDaysIndicator = iSchedule.convertDecToBin(weekDays);
        if (weekDaysIndicator.length() > 7) {
            return false;
        }
        //patch up
        int less = 7 - weekDaysIndicator.length();
        for (int i = 0; i < less; i++) {
            weekDaysIndicator = "0" + weekDaysIndicator;
        }
        long startFrom = e.getStarting();
        long period = e.getPeriod();
        Date d = new Date(startFrom);
        int day = d.getDay();
        Event current;
        while (startFrom < ending) {
            for (int i = day; i < weekDaysIndicator.length(); i++) {
                if (weekDaysIndicator.charAt(i) == '1') {
                    e.setStarting(startFrom);
                    e.setEnding(startFrom + period);
                    current = e.getCopy();//deep copy method has already changed the id
                    if (!addEvent(current)) {
                        return false;
                    }
                }
                //increatment startFrom by one day
                startFrom += DAY;
            }
            day = 0;
        }
        return true;
    }

    @Override
    public boolean addMonthlyEvent(Event e, List<Integer> days) {
        if (!isValidEvent(e)) {
            return false;
        }
        Date firstStartingDate = e.getStartingDate();
        int year = firstStartingDate.getYear();
        int month = firstStartingDate.getMonth();
        int startingDate = firstStartingDate.getDate();
        long period = e.getPeriod();
        int lengthOfMonth;
        while (e.getStarting() < ending) {
            //validating the length of period
            lengthOfMonth = (DateToolSet.isLeapYear(year) ? DateToolSet.LEAP_YEAR_MONTHS[month] : DateToolSet.MONTHS[month]);
            if ((period > (DAY * lengthOfMonth))) {
                return false;
            }
            for (int i : days) {
                //iterating days and modify the event
                if (i > lengthOfMonth) {
                    return false;
                }
                if (i < startingDate) {
                    continue;
                }
                e.setStarting(e.getStarting() + DAY * i - startingDate);
                e.setEnding(e.getStarting() + period);
                if (!addEvent(e.getCopy())) {
                    return false;
                }
            }
            //checking if it's the end of the year
            if (++month == 13) {
                month = 1;
                year++;
            }
        }
        return true;
    }

    /**
     * Unfinished, will fix this later...
     *
     * @param e
     * @param dates
     * @return
     */
    @Override
    public boolean addYearlyEvent(Event e, List<Date> dates) {
        if (!isValidEvent(e)) {
            return false;
        }
        DateToolSet.sortAndSetSameYear(dates);
        Date startTime = e.getStartingDate();
        int year = startTime.getYear();
        long period = e.getPeriod();
        boolean isLeapYear;
        int lengthOfYear;
        int monthOfDates;
        int dateOfDates;
        while (e.getStarting() < ending) {
            //validating the length of period, need to validate for each year before the ending year
            isLeapYear = DateToolSet.isLeapYear(year);
            lengthOfYear = (isLeapYear ? 366 : 365);
            if ((period > (DAY * lengthOfYear))) {
                return false;
            }
            //iterate and add dates
            for (Date d : dates) {
                monthOfDates = d.getMonth();
                dateOfDates = d.getDate();
                //validate the date of that year(checking Feb 29)
                if (monthOfDates == 2 && !isLeapYear && dateOfDates == 29) {
                    continue;
                }
                //set event date
                startTime.setMonth(monthOfDates);
                startTime.setDate(dateOfDates);
                e.setStartingDate(startTime);
                e.setEnding(e.getStarting() + period);
                if (!addEvent(e.getCopy())) {
                    return false;
                }
            }
            //increatment the year
            startTime.setYear(year++);
        }
        return true;
    }

    @Override
    public boolean contaninsEvent(Event e) {
        return events.contains(e);
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
        return;
    }

    public boolean isValidTime(long time) {
        return time > starting && time < ending;
    }

    /**
     *
     * @param date date has to be the beginning of the day(0:00)
     * @return 草，老子懒得干了，费事
     */
    @Override
    public DaySchedule getDaySchedule(Date date) {
        if (!(isValidTime(date.getTime()) && isValidTime(date.getTime() + DAY))) {
            return null;
        }
        //acquiring all the events from that day
        Date endDate = new Date(date.getTime()+DAY);
        List<Event> dayEvents = getEventsByTimePeriod(date,endDate);
        return null;
    }

    /**
     *
     * @param from
     * @return
     */
    @Override
    public WeekSchedule getWeekShedule(Date from) {
        if (!(isValidTime(from.getTime()) && isValidTime(from.getTime() + WEEK))) {
            return null;
        }
        //acquiring all the events from that day
        Date to = new Date(from.getTime() + WEEK);
        List<Event> dayEvents = getEventsByTimePeriod(from,to);
        return null;
    }

    /**
     * get each DayPeriod at that month and combine them
     *
     * @param month
     * @return
     */
    @Override
    public MonthSchedule getMonthSchedule(Date month) {
        //TO-DO:I'm Indian, you know what I mean?
        return null;
    }

    @Override
    public Event getEventById(int id) {
        for (Event e : events) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Event> getEventsByParticipator(Personel participator) {
        ArrayList<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getParticipators().contains(participator)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * As long as the event has one of the participators, it counts.(very
     * inefficient)
     *
     * @param participators
     * @return
     */
    @Override
    public List<Event> getEventsByParticipators(Set<Personel> participators) {
        return null;
    }

    /**
     * Get events by exat participators
     *
     * @param participators
     * @return
     */
    @Override
    public List<Event> getEventsByExatParticipators(Set<Personel> participators) {
        ArrayList<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getParticipators().equals(participators)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * list all events ending after the date
     *
     * @param time
     * @return
     */
    @Override
    public List<Event> getEventsAfter(Date time) {
        long afterTime = time.getTime();
        ArrayList<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getEnding() >= afterTime) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * list all events ending after from and starting before to
     *
     * @param from
     * @param to
     * @return
     */
    @Override
    public List<Event> getEventsByTimePeriod(Date from, Date to) {
        long fromTime = from.getTime();
        long afterTime = to.getTime();
        ArrayList<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getEnding() <= afterTime && e.getStarting() >= fromTime) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public List<Event> getEventsByTitle(String title) {
        ArrayList<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getTitle().equals(title)) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public boolean removeEventById(int id) {
        for (Event e : events) {
            if (e.getId() == id) {
                return events.remove(e);
            }
        }
        return false;
    }

    @Override
    public boolean removeEventByTitle(String title) {
        for (Event e : events) {
            if (e.getTitle().equals(title)) {
                return events.remove(e);
            }
        }
        return false;
    }

    @Override
    public boolean removeEvent(Event e) {
        return events.remove(e);
    }

    private long getGap(Event e1, Event e2) {
        return e2.getStarting() - e1.getEnding();
    }

    @Override
    public TimePeriod findLongestGap() {
        if (events.isEmpty()) {
            return new TimePeriod(id + 1, starting, ending);
        }
        if (events.size() == 1) {
            return (starting - events.peek().getStarting() >= ending - events.peek().getEnding() ? new TimePeriod(id + 1, starting, events.peek().getStarting()) : new TimePeriod(id + 1, events.peek().getEnding(), ending));
        }
        long maxLen = 0;
        long maxStart = -1;
        long maxEnd = -1;
        Event current;
        Event next;
        Iterator<Event> it = events.iterator();
        while (it.hasNext()) {
            current = it.next();
            //checking if two events are consecutive
            while (it.hasNext()) {
                next = it.next();
                //checking is consecutive(intersect)
                if (next.getStarting() <= current.getEnding()) {
                    continue;
                } //not consecutive, find the gap
                else if (getGap(current, next) > maxLen) {
                    maxStart = current.getEnding();
                    maxEnd = next.getStarting();
                    maxLen = getGap(current, next);
                    break;
                }
            }
        }
        //if no gap, return null
        return maxStart == -1 ? null : new TimePeriod(id + 1, maxStart, maxEnd);
    }

    @Override
    public TimePeriod findLongestConsecutivePeriod() {
        if (events.isEmpty()) {
            return null;
        }
        if (events.size() == 1) {
            Event e = events.peek();
            return new TimePeriod(id + 1, e.getStarting(), e.getEnding());
        }
        long maxStart = -1;
        long maxEnd = -1;
        long maxLen = 0;
        long currentLen = 0;
        Event current;
        Event next;
        Iterator<Event> it = events.iterator();
        while (it.hasNext()) {
            currentLen = 0;
            current = it.next();
            while (it.hasNext()) {
                next = it.next();
                //consecutive, extend the current len
                if (next.getStarting() <= current.getEnding()) {
                    currentLen = next.getEnding() - current.getStarting();
                } else if (currentLen > maxLen) {
                    //if not consecutive, check and update the max len
                    maxLen = currentLen;
                    maxStart = current.getStarting();
                    maxEnd = next.getEnding();
                    break;
                }
            }
        }
        return (maxStart == -1 ? null : new TimePeriod(id + 1, maxStart, maxEnd));
    }
    
    @Override
    public String toString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Period ").append(id).append("\n").append(df.format(new Date(starting))).append("\n");
        for(Event e:events){
            sb.append(e).append("\n");
        }
        sb.append(df.format(new Date(ending)));
        return sb.toString();
    }
}
