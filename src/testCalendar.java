
import java.util.Calendar;


public class testCalendar {
    public static void main(String[]args){
        Calendar fecha=Calendar.getInstance();
        System.out.println(fecha);
        int year=fecha.get(Calendar.YEAR);
        System.out.println(year);
    }
}
