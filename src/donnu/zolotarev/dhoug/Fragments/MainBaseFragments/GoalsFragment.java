package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import donnu.zolotarev.dhoug.Adapters.GoalsAdapter;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddGoalFragment;
import donnu.zolotarev.dhoug.R;

import java.util.ArrayList;

public class GoalsFragment extends MainBaseFragments {


    private GoalsAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (baseAdapted == null) {
            ArrayList<GoalItem> items = new ArrayList<GoalItem>();
            items.add(new GoalItem());
            GoalItem goalItem = new GoalItem();
            goalItem.setDone(true);
            items.add(goalItem);
            items.add(new GoalItem());
            items.add(new GoalItem());
            baseAdapted = new GoalsAdapter(activity,items);
            adapter = (GoalsAdapter)baseAdapted;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                AddGoalFragment goalFragment = new AddGoalFragment();
                goalFragment.setTargetFragment(this,AddGoalFragment.ADD_NEW);
                showFragment(goalFragment, true);
                return true;
            case R.id.menu_edit:
                return true;
        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AddGoalFragment.ADD_NEW:
                GoalItem item = (GoalItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                adapter.add(item);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
