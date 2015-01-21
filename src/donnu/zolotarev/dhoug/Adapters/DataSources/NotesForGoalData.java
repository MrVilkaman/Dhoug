package donnu.zolotarev.dhoug.Adapters.DataSources;

import android.database.Cursor;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import donnu.zolotarev.dhoug.Adapters.QuickAdapter;
import donnu.zolotarev.dhoug.DataModels.NoteItem;

public class NotesForGoalData implements QuickAdapter.DataSource {
    private final Long id;

    public NotesForGoalData(Long id) {
        this.id = id;
    }
    @Override
    public Cursor getRowIds() {
        String query = new Select("Id").from(NoteItem.class).where("goalId = ?").toSql();
        return ActiveAndroid.getDatabase().rawQuery(query, new String[]{Long.toString(id)});
    }

    @Override
    public Cursor getRowById(long rowId) {
        String query = new Select().all().from(NoteItem.class).where("Id = ?").toSql();
        return ActiveAndroid.getDatabase().rawQuery(query, new String[]{Long.toString(rowId)});
    }
}
