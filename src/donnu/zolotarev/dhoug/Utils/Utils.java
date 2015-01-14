package donnu.zolotarev.dhoug.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String getCurrentFormatDate(String format){
        return getFormatData(format,new Date());
    }

    public static String getFormatData(String format, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return  simpleDateFormat.format(date);
    }

}
