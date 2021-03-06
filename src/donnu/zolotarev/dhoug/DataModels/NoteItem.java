package donnu.zolotarev.dhoug.DataModels;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import donnu.zolotarev.dhoug.Enums.NOTES_VALIDATE;

@Table(name = "Notes")
public class NoteItem  extends Model implements Serializable {
    @Column(name = "title")
    private String title;
    @Column(name = "discription")
    private String discription;
    @Column(name = "validate")
    private NOTES_VALIDATE validate;
    @Column(name = "goalId")
    private long goalId;
    @Column(name = "createTime")
    private Date createTime;

    public NoteItem() {
        validate = NOTES_VALIDATE.IN_PERPETUITY;
        goalId = -1;
        createTime = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public NOTES_VALIDATE getValidate() {
        return validate;
    }

    public void setValidate(NOTES_VALIDATE validate) {
        this.validate = validate;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public static ArrayList<NoteItem> getAll() {
        return (ArrayList)(new Select().all().from(NoteItem.class).execute());
    }

    public static ArrayList<NoteItem> getNotesForGoals(long goalsId) {
        return (ArrayList)(new Select().all().from(NoteItem.class).where("goalId = ?",goalsId).execute());
    }

    public static void delete(long id){
        NoteItem item = Model.load(NoteItem.class, id);
        if (item != null) {
            item.delete();
        }
    }

    public Date getCreateTime() {
        return createTime;
    }
}
