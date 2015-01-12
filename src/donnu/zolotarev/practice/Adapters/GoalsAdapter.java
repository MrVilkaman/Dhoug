package donnu.zolotarev.practice.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import donnu.zolotarev.practice.DataModels.GoalItem;
import donnu.zolotarev.practice.R;

import java.util.ArrayList;

public class GoalsAdapter extends BaseAdapter {

    //todo change SomeItem
    private final ArrayList<GoalItem> items;
    private final LayoutInflater layoutInflater;

    public GoalsAdapter(Activity context) {
       items = new ArrayList<GoalItem>();
       layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GoalsAdapter(Activity context, ArrayList<GoalItem> items) {
        this.items = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (i != items.size()) {
            if (view == null){
               view = inflateNewView(viewGroup);
            }
            ViewHolder holder = (ViewHolder)view.getTag();
            GoalItem goalItem = getSomeItem(i);
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

    private class ViewHolder {

        public ViewHolder(View view) {
           ///someField =  ButterKnife.findById(view,R.id.someField);
        }
    }
}
