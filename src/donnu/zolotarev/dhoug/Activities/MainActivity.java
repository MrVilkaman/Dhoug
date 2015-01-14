package donnu.zolotarev.dhoug.Activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.MenuItem;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.GoalsFragment;
import donnu.zolotarev.dhoug.Fragments.LeftMenuFragmenu;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.NotesFragment;
import donnu.zolotarev.dhoug.Interface.IOpenMenu;
import donnu.zolotarev.dhoug.R;

public class MainActivity extends SingleFragmentActivity  {

    private SlidingPaneLayout drawerLayout;

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
    }

    private void createLeftPanel() {
        drawerLayout = (SlidingPaneLayout) findViewById(R.id.drawer_layout);
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
                if (drawerLayout.isOpen()) {
                    drawerLayout.closePane();
                }else {
                    drawerLayout.openPane();
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
}
