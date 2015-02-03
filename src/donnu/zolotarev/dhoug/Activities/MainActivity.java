package donnu.zolotarev.dhoug.Activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import donnu.zolotarev.dhoug.Fragments.LeftMenuFragmenu;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.GoalsFragment;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.NotesFragment;
import donnu.zolotarev.dhoug.Interface.IAnalytics;
import donnu.zolotarev.dhoug.Interface.IOpenMenu;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;

public class MainActivity extends SingleFragmentActivity implements IAnalytics {

    private DrawerLayout drawerLayout;

    private IOpenMenu openMenu;
    private LeftMenuFragmenu myFragment;

    @Override
    protected Fragment createFragment() {
        return new GoalsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTabs();
        createLeftPanel();
        initAnalytics();
    }

    private void initAnalytics() {
        if (Constants.NEED_ANALYTICS) {
            GoogleAnalytics.getInstance(this).enableAutoActivityReports(getApplication());

            try {
                GoogleAnalytics.getInstance(this).dispatchLocalHits();
            } catch (Exception e) {
            }
        }
    }


    private void createLeftPanel() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        FragmentManager fm = getFragmentManager();
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
                loadRootFragment(new GoalsFragment(),false);
            }

            @Override
            public void openNotes() {
                clearBackStack();
                loadRootFragment(new NotesFragment(),false);
            }
        };
    }

    public void hideKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = getCurrentFocus();
            if (view != null){
                view.clearFocus();
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e){

        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawers();
                }else {
                    hideKeyboard();
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
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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

    public void setTitleText(int text){
        myFragment.setTitle(text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.NEED_ANALYTICS) {
            GoogleAnalytics.getInstance(this).dispatchLocalHits();
            GoogleAnalytics.getInstance(this).reportActivityStart(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Constants.NEED_ANALYTICS) {
            GoogleAnalytics.getInstance(this).reportActivityStop(this);
        }
    }

    @Override
    public void sendReport(String category,String action){
        sendReport(category, action,null);
    }

    @Override
    public void sendReport(String category,String action, String label){
        if (Constants.NEED_ANALYTICS) {
            try {
                Tracker tracker = GoogleAnalytics.getInstance(MainActivity.this).newTracker(Constants.ANALISTYC_TRACER_ID);

                HitBuilders.EventBuilder builder =  new HitBuilders.EventBuilder();
                if (category != null) {
                    builder.setCategory(category);
                }
                if (action != null) {
                    builder.setAction(action);
                }
                if (label != null) {
                    builder.setLabel(label);
                }
                tracker.send(builder.build());
            } catch (Exception e) {
            }
        }
    }
}
