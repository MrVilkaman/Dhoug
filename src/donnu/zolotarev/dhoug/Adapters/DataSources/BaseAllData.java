package donnu.zolotarev.dhoug.Adapters.DataSources;

import java.util.Date;

import donnu.zolotarev.dhoug.Adapters.QuickAdapter;
import donnu.zolotarev.dhoug.Enums.TIME_PERIOD;
import donnu.zolotarev.dhoug.Utils.Constants;

abstract class BaseAllData implements QuickAdapter.DataSource {

    protected long time = 0;

    public BaseAllData() {
        setRepetition(TIME_PERIOD.ALL);
    }

    @Override
    public void setRepetition(TIME_PERIOD repetition) {
        Date date = new Date();
        switch (repetition){
            case DAY:
                time = date.getTime() - Constants.DAY_IN_MILISEC;
                break;
            case MOUNTH:
                time = date.getTime() - Constants.MONTH_IN_MILISEC;
                break;
            case WEEK:
                time = date.getTime() - Constants.WEEK_IN_MILISEC;
                break;
            case ALL:
            default:
                time = 0;
        }
    }
}
