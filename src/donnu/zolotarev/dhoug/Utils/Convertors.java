package donnu.zolotarev.dhoug.Utils;

import donnu.zolotarev.dhoug.Enums.GOAL_REPETITION;
import donnu.zolotarev.dhoug.Enums.NOTES_VALIDATE;
import donnu.zolotarev.dhoug.R;

public class Convertors {
    public static NOTES_VALIDATE menuIdToEnumNotes(int id){
        switch (id){
            default:
            case R.id.notes_validate_in_perpetuity:
                return NOTES_VALIDATE.IN_PERPETUITY;
            case R.id.notes_validate_mounth:
                return NOTES_VALIDATE.MOUNTH;
            case R.id.notes_validate_week:
                return NOTES_VALIDATE.WEEK;
            case R.id.notes_validate_year:
                return NOTES_VALIDATE.YEAR;
        }
    }

    public static int enumNotesToText(NOTES_VALIDATE validate){
        switch (validate){
            default:
            case IN_PERPETUITY:
                return R.string.notes_validate_in_perpetuity;
            case MOUNTH:
                return R.string.notes_validate_mounth;
            case WEEK:
                return R.string.notes_validate_week;
            case YEAR:
                return R.string.notes_validate_year;
        }
    }

    public static GOAL_REPETITION menuIdToEnumGoalReplay(int itemId) {
        switch (itemId){
            default:
            case R.id.add_period_never:
                return GOAL_REPETITION.NO;
            case R.id.add_period_every_day:
                return GOAL_REPETITION.EVERY_DAY;
            case R.id.add_period_every_week:
                return GOAL_REPETITION.EVERY_WEEK;
            case R.id.add_period_every_mounth:
                return GOAL_REPETITION.EVERY_MONTH;
        }
    }

    public static int enumGoalsToText(GOAL_REPETITION validate){
        switch (validate){
            default:
            case NO:
                return R.string.add_period_never;
            case EVERY_DAY:
                return R.string.add_period_every_day;
            case EVERY_WEEK:
                return R.string.add_period_every_week;
            case EVERY_MONTH:
                return R.string.add_period_every_mounth;
        }
    }
}
