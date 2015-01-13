package donnu.zolotarev.practice.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.InjectView;
import donnu.zolotarev.practice.Adapters.GoalsAdapter;
import donnu.zolotarev.practice.DataModels.GoalItem;
import donnu.zolotarev.practice.R;

import java.util.ArrayList;

public class GoalsFragment extends BaseFragment {

    private GoalsAdapter adapted;

    @InjectView(R.id.list)
    ListView listView;


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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setAdapter(adapted);
    }


}
