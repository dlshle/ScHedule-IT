package shit;

import java.util.List;

/**
 * EventRunner
 * EventRunner runs events of a TimePeriod in a time manner.
 * @author Xuri Li
 * @since 07/03/2018
 */
public class EventRunner {
    
    private TimePeriod period;
    
    public EventRunner(List<TimePeriod> periods){
        period = iSchedule.combineTimePeriods(periods);
    }
    
    public void run(){
        
    }
    
    public void earlyAlert(Event e){
        
    }
    
    public void checkEvent(Event e){
        
    }
    
    public void lateElert(Event e){
        
    }
    
}
