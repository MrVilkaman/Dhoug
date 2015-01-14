package donnu.zolotarev.dhoug.DataModels;

import java.io.Serializable;

public class GoalItem implements Serializable {
    private boolean isDone = false;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}
