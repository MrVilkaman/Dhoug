package donnu.zolotarev.dhoug.Interface;

public interface IAnalytics {
    String GOAL = "GOAL";
    String NOTE = "NOTE";
    String ACTION_ADD = "ACTION_ADD";
    String ACTION_CHANGE = "ACTION_CHANGE";
    String ACTION_DELETE = "ACTION_DELETE";

    void sendReport(String goal, String actionAdd);
    void sendReport(String category,String action, String label);

}
