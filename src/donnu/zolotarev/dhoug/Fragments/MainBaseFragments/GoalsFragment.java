package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.activeandroid.Model;

import java.io.Serializable;

import donnu.zolotarev.dhoug.Adapters.DataSources.AllGoalsSortedData;
import donnu.zolotarev.dhoug.Adapters.GoalsDBAdapter;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddGoalFragment;
import donnu.zolotarev.dhoug.Fragments.GoalShowPage;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

public class GoalsFragment extends MainBaseFragments {


    private GoalsDBAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (baseAdapted == null) {
            // todo remove test data

            baseAdapted = new GoalsDBAdapter(activity,new AllGoalsSortedData());
            adapter = (GoalsDBAdapter)baseAdapted;
            adapter.doQuery();
        }
        adapter.setClickListener(new IClick() {
            @Override
            public void click(Serializable goalItem) {
                GoalShowPage showPage = GoalShowPage.open((GoalItem)goalItem);
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
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AddGoalFragment.ADD_NEW:
                GoalItem item = (GoalItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                item.save();
                adapter.doQuery();
                break;
            case AddGoalFragment.CHANGE:
                item = (GoalItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                item.save();
                adapter.doQuery();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        hideKeyboard();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case CM_EDIT:
                AddGoalFragment goalsFragment = AddGoalFragment.open(this, Model.load(GoalItem.class, menuInfo.id), AddGoalFragment.CHANGE);
                showFragment(goalsFragment, true);
                return true;
            case CM_DELETE:
                GoalItem.delete(menuInfo.id);
                adapter.notifyDataSetChanged();
                adapter.doQuery();
                return true;
        }
        return false;
    }
}
