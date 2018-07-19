/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shit;

/**
 *
 * @author Xuri
 */
public class TestDriver {
    
    public static void main(String[] args){
        Period p = new Period(1, System.currentTimeMillis(), System.currentTimeMillis()+iSchedule.WEEK);
        p.addDailyEvent(new Event(2,"test daily","just a test", System.currentTimeMillis()+10000, System.currentTimeMillis()+iSchedule.DAY-1000000));
        System.out.println(p);
    }
    
}
