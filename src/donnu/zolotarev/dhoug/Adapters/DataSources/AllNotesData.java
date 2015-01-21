package donnu.zolotarev.dhoug.Adapters.DataSources;

import android.database.Cursor;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import donnu.zolotarev.dhoug.Adapters.QuickAdapter;
import donnu.zolotarev.dhoug.DataModels.NoteItem;

public class AllNotesData implements QuickAdapter.DataSource {
    @Override
    public Cursor getRowIds() {
        String query = new Select("Id").from(NoteItem.class).toSql();
        return ActiveAndroid.getDatabase().rawQuery(query, null);
    }

    @Override
    public Cursor getRowById(long rowId) {
        String query = new Select().all().from(NoteItem.class).where("Id = ?").toSql();
        return ActiveAndroid.getDatabase().rawQuery(query, new String[]{Long.toString(rowId)});
    }
}
