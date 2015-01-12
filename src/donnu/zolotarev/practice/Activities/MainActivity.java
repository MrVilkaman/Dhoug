package donnu.zolotarev.practice.Activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.MenuItem;
import donnu.zolotarev.practice.Fragments.LeftMenuFragmenu;
import donnu.zolotarev.practice.Fragments.GoalsFragment;
import donnu.zolotarev.practice.R;

public class MainActivity extends SingleFragmentActivity {

    private SlidingPaneLayout drawerLayout;


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
                break;
        }
        return true;
    }


    private void setupTabs() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
