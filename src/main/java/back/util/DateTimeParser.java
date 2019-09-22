package back.util;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
import java.time.format.DateTimeParseException;

public class DateTimeParser {
    public static String parseDateTime(String dateTime){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter ebayFormatObj = DateTimeFormatter.ofPattern("MMM-dd-yy HH:mm:ss");
        LocalDateTime myDateTimeObject;
        String dt;

        try{
            myDateTimeObject = LocalDateTime.parse(dateTime, ebayFormatObj);
            dt = myDateTimeObject.format(myFormatObj);
        }
        catch(DateTimeParseException e){
            myDateTimeObject = LocalDateTime.parse(dateTime, myFormatObj);
            dt = myDateTimeObject.format(myFormatObj);
        }

        return dt;
    }
}
