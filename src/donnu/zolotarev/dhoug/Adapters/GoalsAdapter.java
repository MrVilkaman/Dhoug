package donnu.zolotarev.dhoug.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import butterknife.ButterKnife;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.practice.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GoalsAdapter extends BaseAdapter {

    //todo change SomeItem
    private final ArrayList<GoalItem> items;
    private final LayoutInflater layoutInflater;

    private int devider = 1001;

    public GoalsAdapter(Activity context) {
       items = new ArrayList<GoalItem>();
       layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GoalsAdapter(Activity context, ArrayList<GoalItem> items) {
        this.items = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sort();
    }

    @Override
    public int getCount() {
        return items.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public GoalItem getSomeItem(int i){
        return (GoalItem)getItem(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (i != devider) {
            ViewHolder holder;
            final GoalItem goalItem;
            if (view == null) {
                view = inflateNewView(viewGroup);
            }
                holder = (ViewHolder)view.getTag();
            if (holder == null) {
                view = inflateNewView(viewGroup);
                holder = (ViewHolder)view.getTag();
            }

                goalItem = getSomeItem(i < devider?i:i-1);
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        goalItem.setDone(b);
                        sort();
                    }
                });



            holder.checkBox.setChecked(goalItem.isDone());


        } else{
            view = inflateTextView(viewGroup);
        }

        return view;
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

    public void sort(){
        Collections.sort(items, new Comparator<GoalItem>() {
            public int compare(GoalItem o1, GoalItem o2) {
                return Boolean.valueOf(o1.isDone()).compareTo(o2.isDone());
            }
        });

        devider = items.size();
        for (int i = 0; i<items.size();i++){
            if (getSomeItem(i).isDone()) {
                devider = i;
                break;
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
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
