package shit;

import java.util.ArrayList;
import java.util.Date;

/**
 * Test in progress...
 * @author Xuri
 * @since 07/03/2018
 */
public class TestDriver {
    
    public static void main(String[] args){
        //test add daily events
        TimePeriod p = new TimePeriod(1, System.currentTimeMillis(), System.currentTimeMillis()+iSchedule.WEEK);
        p.addDailyEvent(new Event(2,"test daily","just a test", System.currentTimeMillis()+10000, System.currentTimeMillis()+iSchedule.DAY-1000000));
        System.out.println(p);
        System.out.println("------------------------");
        //test getEventsAfter
        for(Event e:new ArrayList<Event>(p.getEventsAfter(new Date(System.currentTimeMillis()+iSchedule.DAY)))){
            System.out.println(e);
        }
        System.out.println("------------------------");
        //test add weekly events
        p = new TimePeriod(1, System.currentTimeMillis(), System.currentTimeMillis()+7*iSchedule.WEEK);
        p.addWeeklyEvent(new Event(3,"test weekly event","just a test", System.currentTimeMillis(), System.currentTimeMillis()+5*iSchedule.HOUR), iSchedule.convertBinToDec("1011010"));
        System.out.println(p);
    }
    
}
