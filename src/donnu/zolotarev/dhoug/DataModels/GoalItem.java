package donnu.zolotarev.dhoug.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import donnu.zolotarev.dhoug.Enums.GOAL_REPETITION;

@Table(name = "Goals")
public class GoalItem extends Model implements Serializable {
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "timeStart")
    private Date timeStart;
    @Column(name = "timeEnd")
    private Date timeEnd;
    @Column(name = "repetition")
    private GOAL_REPETITION repetition;
    @Column(name = "isDone")
    private boolean isDone = false;

    @Column(name = "createTime")
    private Date createTime;

    public GoalItem() {
        super();
        repetition = GOAL_REPETITION.NO;
        description = title = "";
        createTime = new Date();
    }

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

    @Override
    public String toString() {
        return getTitle();
    }

    public static ArrayList<GoalItem> getAll() {
        return (ArrayList)(new Select().all().from(GoalItem.class).execute());
    }

    public static void delete(long id){
        GoalItem item = Model.load(GoalItem.class, id);
        if (item != null) {
            item.delete();
        }
    }

    public static String getTitleById(long goalId) {
        if (goalId == -1) {
            return "";
        }
        GoalItem item = Model.load(GoalItem.class, goalId);
        if (item !=null) {
            return item.getTitle();
        }else {
            return "";
        }
    }

    public Date getCreateTime() {
        return createTime;
    }
}
