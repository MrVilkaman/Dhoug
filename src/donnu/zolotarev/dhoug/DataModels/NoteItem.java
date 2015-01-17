package donnu.zolotarev.dhoug.DataModels;

import donnu.zolotarev.dhoug.Enums.NOTES_VALIDATE;

import java.io.Serializable;

public class NoteItem implements Serializable {


    private int id;
    private String title;
    private String discription;
    private NOTES_VALIDATE validate;

    public NoteItem() {
        validate = NOTES_VALIDATE.IN_PERPETUITY;
    }

    public int getId() {
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
}
