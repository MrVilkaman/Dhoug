package donnu.zolotarev.dhoug.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.activeandroid.Model;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

public class GoalsDBAdapter extends QuickAdapter {

    private final LayoutInflater layoutInflater;
    private IClick clickListener;

    private int devider = -1;//Integer.MAX_VALUE;

    public GoalsDBAdapter(Context context, DataSource dataSource) {
        super(context, dataSource);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void doQuery() {
        super.doQuery();
        //SELECT * FROM SAMPLE_TABLE ORDER BY ROWID ASC LIMIT 1
       /* String query = new Select("Id","isDone").from(GoalItem.class).orderBy("isDone ASC").toSql();
        devider = ActiveAndroid.getDatabase().rawQuery(query, null).getPosition();*/
    }

    @Override
    public int getCount() {
        return super.getCount()/*+1*/;
    }

    @Override
    public Object getItem(int position) {
     /*   if (position == devider) {
            return null;
        }
        int index = position < devider?position:position-1;*/
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
      /*  if (position == devider) {
            return 0;
        }
        int index = position < devider?position:position-1;*/
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
       /* if (position == devider) {
            return inflateTextView(parent);
        }*/
        if (convertView != null) {
            ViewHolder holder = (ViewHolder)convertView.getTag();
            if (holder == null) {
                convertView = null;
            }
        }
//        int index = position < devider?position:position-1;
        view = super.getView(position, convertView, parent);
        return view;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflateNewView(parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();
        final long id = cursor.getInt(cursor.getColumnIndex("Id"));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                GoalItem goalItem = Model.load(GoalItem.class, id);
                if (goalItem != null) {
                    goalItem.setDone(b);
                    goalItem.save();
                    notifyDataSetChanged();
                    doQuery();
                }
            }
        });
        holder.title.setText(cursor.getString(cursor.getColumnIndex("title")));
        holder.subTitle.setText(cursor.getString(cursor.getColumnIndex("description")));

        holder.checkBox.setChecked(cursor.getInt(cursor.getColumnIndex("isDone")) == 1?true:false);
        view.setLongClickable(true);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(Model.load(GoalItem.class, id));
            }
        });
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

    private View inflateTextView(ViewGroup viewGroup) {
        View view  = layoutInflater.inflate(R.layout.goal_text_separator, viewGroup, false);
        ((TextView)view.findViewById(R.id.text)).setText(R.string.goal_done);
        return view;
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
}
