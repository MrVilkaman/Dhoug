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
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Interface.IClick;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GoalsAdapter extends BaseAdapter implements IAdapter {

    //todo change SomeItem
    private final ArrayList<GoalItem> items;
    private final LayoutInflater layoutInflater;

    private int devider = 1001;
    private IClick clickListener;

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
        int index = i < devider?i:i-1;
        if (-1 < index && index < items.size()) {
            return items.get(index).getId();
        }
        return 0;
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
                    goalItem.save();
                    sort();
                }
            });
            holder.title.setText(goalItem.getTitle());
            holder.subTitle.setText(goalItem.getDescription());

            holder.checkBox.setChecked(goalItem.isDone());
            view.setLongClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.click(goalItem);
                }
            });
        } else{
            if (devider != items.size()) {
                view = inflateTextView(viewGroup);
                view.setLongClickable(false);
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return true;
                    }
                });

            }else{
                view = new View(viewGroup.getContext());
            }
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

    public void add(GoalItem item) {
        items.add(item);
        sort();
    }

    @Override
    public void delete(long id) {
        int index = (int)(id < devider?id:id-1);
        if (-1 < index && index < items.size()) {
            items.remove(index);
            sort();
        }
    }

    @Override
    public void change(Object item) {

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

    public IClick getClickListener() {
        return clickListener;
    }

    public void setClickListener(IClick clickListener) {
        this.clickListener = clickListener;
    }
}
