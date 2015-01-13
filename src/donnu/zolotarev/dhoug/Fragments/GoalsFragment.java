package donnu.zolotarev.dhoug.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.Adapters.GoalsAdapter;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Utils.Utils;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;

import java.util.ArrayList;

public class GoalsFragment extends BaseFragment {

    private GoalsAdapter adapted;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.goals_current_data)
    TextView calendar;

    @InjectView(R.id.goals_goals_period)
    TextView period;

    private PopupMenu popupMenu;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (adapted == null) {
            ArrayList<GoalItem> items = new ArrayList<GoalItem>();
            items.add(new GoalItem());
            GoalItem goalItem = new GoalItem();
            goalItem.setDone(true);
            items.add(goalItem);
            items.add(new GoalItem());
            items.add(new GoalItem());
            adapted = new GoalsAdapter(activity,items);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_goals,inflater,container);
        calendar.setText(Utils.getCurrentFormatDate(Constants.CURRENT_DATE));
        setup();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setAdapter(adapted);
    }

    private void setup() {
        popupMenu = new PopupMenu(getActivity(), period);
        popupMenu.inflate(R.menu.periodmenu); // Для Android 4.0
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.day:
                        period.setText(R.string.period_day);
                        return true;
                    case R.id.week:
                        period.setText(R.string.period_week);
                        return true;
                    case R.id.mounth:
                        period.setText(R.string.period_mounth);
                        return true;
                    case R.id.all:
                        period.setText(R.string.period_all);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @OnClick(R.id.goals_goals_period)
    void onPediod(){
        popupMenu.show();
    }

}
