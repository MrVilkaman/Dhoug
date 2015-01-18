package donnu.zolotarev.dhoug;

import android.app.Application;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Enums.ENTITY;
import donnu.zolotarev.dhoug.Interface.IDataHolfer;

import java.io.Serializable;
import java.util.*;

public class App extends Application implements IDataHolfer {

    private HashMap<ENTITY,Collection> data;

    @Override
    public void onCreate() {
        super.onCreate();
        data = new HashMap<ENTITY, Collection>(2);
    }

    @Override
    public Collection get(ENTITY entity) {
        return data.get(entity);
    }

    @Override
    public void create(ENTITY entity, Collection collection) {
        data.put(entity,collection);
    }

}
