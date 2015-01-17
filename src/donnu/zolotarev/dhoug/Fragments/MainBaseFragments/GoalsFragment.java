package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import donnu.zolotarev.dhoug.Adapters.GoalsAdapter;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddGoalFragment;
import donnu.zolotarev.dhoug.Fragments.GoalShowPage;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

import java.util.ArrayList;

public class GoalsFragment extends MainBaseFragments {


    private GoalsAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (baseAdapted == null) {
            ArrayList<GoalItem> items = new ArrayList<GoalItem>();
            // todo remove test data
            GoalItem item = new GoalItem();
            item.setTitle("Курлык мля!");
            item.setDescription("Лети к цели!");
            items.add(item);

            baseAdapted = new GoalsAdapter(activity,items);
            adapter = (GoalsAdapter)baseAdapted;
        }
        adapter.setClickListener(new IClick() {
            @Override
            public void click(GoalItem goalItem) {
                GoalShowPage showPage = GoalShowPage.open(goalItem);
                showFragment(showPage, true);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setClickListener(null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                showFragment(AddGoalFragment.createNew(this), true);
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
            case AddGoalFragment.CHANGE:
                item = (GoalItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                adapter.change(item);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case CM_EDIT:
                AddGoalFragment goalsFragment = AddGoalFragment.open(this, adapter.getSomeItem((int) menuInfo.id), AddGoalFragment.CHANGE);
                showFragment(goalsFragment, true);
                return true;
        }
        return false;
    }
}
