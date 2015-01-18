package donnu.zolotarev.dhoug.DataModels;

import donnu.zolotarev.dhoug.Enums.NOTES_VALIDATE;

import java.io.Serializable;
import java.util.UUID;

public class NoteItem implements Serializable {


    private UUID id;
    private String title;
    private String discription;
    private NOTES_VALIDATE validate;
    private UUID goalId;

    public NoteItem() {
        validate = NOTES_VALIDATE.IN_PERPETUITY;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
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

    public UUID getGoalId() {
        return goalId;
    }

    public void setGoalId(UUID goalId) {
        this.goalId = goalId;
    }
}
