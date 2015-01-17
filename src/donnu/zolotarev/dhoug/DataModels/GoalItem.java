package donnu.zolotarev.dhoug.DataModels;

import donnu.zolotarev.dhoug.Enums.GOAL_REPETITION;

import java.io.Serializable;
import java.util.Date;

public class GoalItem implements Serializable {
    private String title;
    private String description;

    private Date timeStart;
    private Date timeEnd;
    private GOAL_REPETITION repetition;

    public GoalItem() {
        repetition = GOAL_REPETITION.NO;
        description = title = "";
//        timeStart = timeEnd = new Date();
    }

    private boolean isDone = false;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public GOAL_REPETITION getRepetition() {
        return repetition;
    }

    public void setRepetition(GOAL_REPETITION repetition) {
        this.repetition = repetition;
    }
}
