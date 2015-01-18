package donnu.zolotarev.dhoug.Activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Enums.ENTITY;
import donnu.zolotarev.dhoug.Fragments.LeftMenuFragmenu;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.GoalsFragment;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.NotesFragment;
import donnu.zolotarev.dhoug.Interface.IDataHolfer;
import donnu.zolotarev.dhoug.Interface.IOpenMenu;
import donnu.zolotarev.dhoug.R;

import java.util.ArrayList;

public class MainActivity extends SingleFragmentActivity  {

    private DrawerLayout drawerLayout;

    private IOpenMenu openMenu;

    @Override
    protected Fragment createFragment() {
        return new GoalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTabs();
        createLeftPanel();

        loadData();
    }

    private void loadData() {
        //todo заменить
        IDataHolfer dataHolfer = getData();

        ArrayList<GoalItem> items = new ArrayList<GoalItem>();
        GoalItem item = new GoalItem();
        item.setTitle("Курлык мля!");
        item.setDescription("Лети к цели!");
        items.add(item);

        dataHolfer.create(ENTITY.GOALS,items);

        ArrayList<NoteItem> nItems = new ArrayList<NoteItem>();
        NoteItem item2 = new NoteItem();
        item2.setTitle("Новая заметка!");
        nItems.add(item2);
        dataHolfer.create(ENTITY.NOTES,nItems);

    }

    private void createLeftPanel() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getFragmentManager();
        Fragment myFragment = fm.findFragmentById(R.id.menu_frame);
        if (myFragment == null){
            myFragment = new LeftMenuFragmenu();
            fm.beginTransaction()
                    .add(R.id.menu_frame, myFragment)
                    .commit();
        }

        openMenu = new IOpenMenu() {
            @Override
            public void openGoals() {
                clearBackStack();
                loadRootFragment(new GoalsFragment(),true);
            }

            @Override
            public void openNotes() {
                clearBackStack();
                loadRootFragment(new NotesFragment(),true);
            }
        };
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawers();
                }else {
                    FragmentManager fManager = getFragmentManager();
                    if (fManager.getBackStackEntryCount() != 0){
                       popBackStack();
                    }else{
                        drawerLayout.openDrawer(Gravity.LEFT);
                    }
                }
                return true;
        }
        return super.onMenuItemSelected(featureId,item);
    }

    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public IOpenMenu getOpenMenu() {
        return openMenu;
    }

    private void clearBackStack(){
        FragmentManager fManager = getFragmentManager();
        for(int i = 0, max = fManager.getBackStackEntryCount(); i < max; i++){
            fManager.popBackStack();
        }
    }

    public IDataHolfer getData(){
        return (IDataHolfer)getApplication();
    }
}
