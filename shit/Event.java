package shit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Event implements Comparable {

    private int id;
    private String title;
    private String description;
    private long starting;
    private long ending;
    private HashSet<Personel> participators;
    //if two events have the same starting time, priority will decide which one comes first
    private int priority;
    private long earlyAlarmPeriod = 0;

    public Event(int id, String title, String description, long starting, long ending) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = new HashSet<>();
        this.priority = 1;
    }

    public Event(int id, String title, String description, long starting, long ending, HashSet<Personel> participators) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = participators;
        this.priority = 1;
    }

    public Event(int id, String title, String description, long starting, long ending, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = new HashSet<>();
        this.priority = priority;
    }

    public Event(int id, String title, String description, long starting, long ending, HashSet<Personel> participators, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = participators;
        this.priority = priority;
    }
    
    public Event(int id, String title, String description, long starting, long ending, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = new HashSet<>();
        this.priority = 1;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public Event(int id, String title, String description, long starting, long ending, HashSet<Personel> participators, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = participators;
        this.priority = 1;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public Event(int id, String title, String description, long starting, long ending, int priority, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = new HashSet<>();
        this.priority = priority;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public Event(int id, String title, String description, long starting, long ending, HashSet<Personel> participators, int priority, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting;
        if (ending < starting) {
            this.ending = this.starting;
        } else {
            this.ending = ending;
        }
        this.participators = participators;
        this.priority = priority;
        this.earlyAlarmPeriod = earlyAlarm;
    }
    
    public Event(int id, String title, String description, Date starting, Date ending) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = new HashSet<>();
        this.priority = 1;
    }

    public Event(int id, String title, String description, Date starting, Date ending, HashSet<Personel> participators) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = participators;
        this.priority = 1;
    }

    public Event(int id, String title, String description, Date starting, Date ending, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = new HashSet<>();
        this.priority = priority;
    }

    public Event(int id, String title, String description, Date starting, Date ending, HashSet<Personel> participators, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = participators;
        this.priority = priority;
    }
    public Event(int id, String title, String description, Date starting, Date ending, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = new HashSet<>();
        this.priority = 1;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public Event(int id, String title, String description, Date starting, Date ending, HashSet<Personel> participators, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = participators;
        this.priority = 1;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public Event(int id, String title, String description, Date starting, Date ending, int priority, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = new HashSet<>();
        this.priority = priority;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public Event(int id, String title, String description, Date starting, Date ending, HashSet<Personel> participators, int priority, long earlyAlarm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.starting = starting.getTime();
        this.ending = ending.getTime();
        if (this.ending < this.starting) {
            System.out.println("ERROR, event " + id + " has an invalid ending time(Ending>Starting)!");
            this.ending = this.starting;
        }
        this.participators = participators;
        this.priority = priority;
        this.earlyAlarmPeriod = earlyAlarm;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getStarting() {
        return starting;
    }

    public long getEnding() {
        return ending;
    }

    public Date getStartingDate() {
        return new Date(starting);
    }

    public Date getEndingDate() {
        return new Date(ending);
    }

    public long getPeriod() {
        return ending - starting;
    }

    public long getPeriodInSeconds() {
        return getPeriod() / 1000;
    }

    public double getPeriodInMinutes() {
        return getPeriodInSeconds() / 60;
    }

    public double getPeriodInHours() {
        return getPeriodInMinutes() / 60;
    }

    public double getPeriodInDays() {
        return getPeriodInHours() / 24;
    }

    public double getPeriodInWeeks() {
        return getPeriodInDays() / 7;
    }

    public HashSet<Personel> getParticipators() {
        return participators;
    }

    public int getPriority() {
        return priority;
    }
    
    public long getEarlyAlarmPeriod(){
        return earlyAlarmPeriod;
    }
    
    public long getEarlyAlarm(){
        return starting-earlyAlarmPeriod;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStarting(long starting) {
        this.starting = starting;
    }
    
    public void setStartingDate(Date starting){
        setStarting(starting.getTime());
    }
    
    public boolean changeStarting(long starting){
        if(starting>ending)
            return false;
        this.starting = starting;
        return true;
    }
    
    public boolean changeStartingDate(Date startingDate){
        return changeStarting(startingDate.getTime());
    }

    public boolean setEnding(long ending) {
        if(ending<starting)
            return false;
        this.ending = ending;
        return true;
    }
    
    public boolean setEndingDate(Date endingDate){
        return setEnding(endingDate.getTime());
    }

    public void setParticipators(HashSet<Personel> participators) {
        this.participators = participators;
    }

    public boolean setPriority(int priority) {
        if(priority<1||priority>5)
            return false;
        this.priority = priority;
        return true;
    }
    
    public boolean setEarlyAlarmPeriod(long earlyAlarm){
        if(earlyAlarm>starting)
            return false;
        this.earlyAlarmPeriod = earlyAlarm;
        return true;
    }

    public boolean addParticipator(Personel participator) {
        return participators.add(participator);
    }

    public boolean removeParticopator(Personel participator) {
        return participators.remove(participator);
    }

    public boolean removeParticipatorById(int id) {
        for (Personel p : participators) {
            if (p.getId() == id) {
                return removeParticopator(p);
            }
        }
        return false;
    }

    public int getNumberOfParticipators() {
        return participators.size();
    }

    @Override
    public boolean equals(Object o) {
        Event e = (Event) o;
        return id == e.getId()&&starting==e.getStarting()&&ending==e.getEnding()&&title.equals(e.getTitle())&&priority==e.getPriority();
    }

    /**
     * If two events have the same starting(earlyAlarming) time, compare their priority
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Object o) {
        try {
            Event otherEvent = (Event) o;
            int pdiff = priority - otherEvent.getPriority();
            long diff = getEarlyAlarm() - otherEvent.getEarlyAlarm();
            if (diff == 0) {
                if (pdiff != 0) {
                    return pdiff * 10;
                }
            } else                
                return 0;
            return diff > 0 ? 1 : -1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SHIT event can not compare to this high-end superb object!");
            return -100;
        }
    }
    
    public Event getCopy(){
        return new Event(id+1,title,description,starting,ending,participators,priority,earlyAlarmPeriod);
    }
    
    @Override
    public String toString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        StringBuilder sb = new StringBuilder();
        sb.append(title);
        sb.append("("+id+")");
        sb.append(":").append(df.format(new Date(starting))).append("-").append(df.format(new Date(ending)));
        return sb.toString();
    }

}
