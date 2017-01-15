import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekUtil {

    public static String getWeekNumber() {
        LocalDate date = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.GERMAN).weekOfWeekBasedYear();
        int weekNumber = date.get(woy);
        return String.valueOf(weekNumber);
    }

}
