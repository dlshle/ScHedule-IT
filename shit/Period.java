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
    
    public boolean isValidEvent(Event e){
        return (e.getStarting()>=starting||e.getEnding()<=ending);
    }

    @Override
    public boolean addEvent(Event e) {
        if(!isValidEvent(e))
            return false;
        return events.add(e);
    }

    @Override
    public boolean addDailyEvent(Event e) {
        long period = e.getPeriod();
        long startFrom = e.getStarting();
        if (!isValidEvent(e)) {
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
        if((!isValidEvent(e))||e.getPeriod()>DAY)
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
        while (startFrom < ending) {
            for (int i = day; i < weekDaysIndicator.length(); i++) {
                if (weekDaysIndicator.charAt(i) == '1') {
                    e.setStarting(startFrom);
                    e.setEnding(startFrom + period);
                    current = e.getCopy();//deep copy method has already changed the id
                    if (!events.add(current)) {
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
        if(!isValidEvent(e))
            return false;
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
                if (!events.add(e.getCopy())) {
                    return false;
                }
            }
            //checking if it's the end of the year
            if(++month==13){
                month=1;
                year++;
            }
        }
        return true;
    }

    /**
     * Unfinished, will fix this later...
     * @param e
     * @param dates
     * @return 
     */
    @Override
    public boolean addYearlyEvent(Event e, List<Date> dates) {
        if(!isValidEvent(e))
            return false;
        DateToolSet.sortAndSetSameYear(dates);
        Date startTime = e.getStartingDate();
        int year = startTime.getYear();
        int startMonth = startTime.getMonth();
        int startDate = startTime.getDate();
        long period = e.getPeriod();
        boolean isLeapYear;
        int lengthOfYear ;
        int monthOfDates;
        int dateOfDates;
        while (e.getStarting() < ending) {
            //validating the length of period, need to validate for each year before the ending year
            isLeapYear = DateToolSet.isLeapYear(year);
            lengthOfYear = (isLeapYear ? 366 : 365);
            if ((period > (DAY * lengthOfYear))) {
                return false;
            }
            //prepare the start dates for this year
            startMonth = startTime.getMonth();
            startDate = startTime.getDate();
            
            for (Date d : dates) {
                monthOfDates = d.getMonth();
                dateOfDates = d.getDate();
                //validate the date of that year(checking Feb 29)
                if(monthOfDates==2&&!isLeapYear&&dateOfDates==29){
                    continue;
                }
                
                /*e.setStarting(e.getStarting() + DAY * i - startDate);
                e.setEnding(e.getStarting() + period);
                if (!events.add(e.getCopy())) {
                    return false;
                }*/
            }
            //increatment the year
            startTime.setYear(year++);
            //set the date to the first date in dates
            startTime.setMonth(dates.get(0).getMonth());
            startTime.setDate(dates.get(0).getDate());
        }
        return true;
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
    public boolean contaninsEvent(Event e){
        return false;
    }

    @Override
    public MonthSchedule getMonthSchedule(Date month) {
        return null;
    }

    @Override
    public List<Event> getEventsByParticipator(Personel participator) {
        return null;
    }

    @Override
    public List<Event> getEventsByParticipators(List<Personel> participators) {
        return null;
    }

    @Override
    public List<Event> getEventsByTimePeriod(Date from, Date to) {
        return null;
    }

    @Override
    public List<Event> getEvenetsByTitle(String title) {
        return null;
    }

    @Override
    public boolean removeEventById(int id) {
        return false;
    }

    @Override
    public boolean removeEventByTitle(String title) {
        return false;
    }

    @Override
    public boolean removeEvent(Event e) {
        return false;
    }
}
