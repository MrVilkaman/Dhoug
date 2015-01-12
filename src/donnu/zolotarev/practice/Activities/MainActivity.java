package donnu.zolotarev.practice.Activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import donnu.zolotarev.practice.Fragments.MainFragment;
import donnu.zolotarev.practice.R;

public class MainActivity extends SingleFragmentActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTabs();

        createLeftPanel();
    }

    private void createLeftPanel() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        String[] mScreenTitles = getResources().getStringArray(R.array.screen_array);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.list_item_drawer, mScreenTitles));

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(drawerList)) {
                    drawerLayout.closeDrawer(drawerList);
                }else {
                    drawerLayout.openDrawer(drawerList);
                }
                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
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
