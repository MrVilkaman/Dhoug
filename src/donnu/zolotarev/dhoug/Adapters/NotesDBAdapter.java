package donnu.zolotarev.dhoug.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Select;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

public class NotesDBAdapter extends QuickAdapter {
    private final LayoutInflater layoutInflater;
    private IClick clickListener;

    public NotesDBAdapter(Context context) {
        super(context, new DataSource() {
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
        });
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflateNewView(parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        view.setLongClickable(true);

        if (cursor.moveToFirst()) {

            holder.title.setText(cursor.getString(cursor.getColumnIndex("title")));//noteItem.getTitle());
            holder.subTitle.setText(cursor.getString(cursor.getColumnIndex("discription")));
            holder.checkBox.setVisibility(View.INVISIBLE);
            final long id = cursor.getInt(cursor.getColumnIndex("Id"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.click(Model.load(NoteItem.class, id));
                }
            });
        }
    }

    private View inflateNewView(ViewGroup parent) {
        View view  = layoutInflater.inflate(R.layout.item_goal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    public IClick getClickListener() {
        return clickListener;
    }

    public void setClickListener(IClick clickListener) {
        this.clickListener = clickListener;
    }

    private static class ViewHolder {
        private final CheckBox checkBox;
        private final TextView title;
        private final TextView subTitle;

        public ViewHolder(View view) {
            checkBox =  ButterKnife.findById(view, R.id.checkBox);
            title =  ButterKnife.findById(view, R.id.goal_item_title);
            subTitle =  ButterKnife.findById(view, R.id.goal_item_sub_title);
        }
    }
}
