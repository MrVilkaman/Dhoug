package donnu.zolotarev.dhoug.Adapters.DataSources;

import android.database.Cursor;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import donnu.zolotarev.dhoug.DataModels.GoalItem;

public class GoalsSortedData extends BaseAllData {


    @Override
    public Cursor getRowIds() {
        String query = new Select("Id","isDone","createTime").from(GoalItem.class).where("createTime >= ?").orderBy("isDone ASC").toSql();
        return ActiveAndroid.getDatabase().rawQuery(query, new String[]{Long.toString(time)});
    }

    @Override
    public Cursor getRowById(long rowId) {
        String query = new Select().all().from(GoalItem.class).where("Id = ?").toSql();
        return ActiveAndroid.getDatabase().rawQuery(query, new String[]{Long.toString(rowId)});
    }

}
