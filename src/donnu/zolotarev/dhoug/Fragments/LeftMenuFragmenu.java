package donnu.zolotarev.dhoug.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.Activities.MainActivity;
import donnu.zolotarev.dhoug.Interface.IOpenMenu;
import donnu.zolotarev.dhoug.R;

public class LeftMenuFragmenu extends BaseFragment {


    private DrawerLayout drawerLayout;
    private int lastTitle = R.string.left_menu_goals;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.left_menu_fragmenu,inflater,container);
        drawerLayout = (DrawerLayout) ButterKnife.findById(getActivity(), R.id.drawer_layout);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
                ActionBar bar = getActivity().getActionBar();
                bar.setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View view) {
                ActionBar bar = getActivity().getActionBar();
                bar.setTitle(lastTitle);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        ActionBar bar = getActivity().getActionBar();
        bar.setTitle(lastTitle);
        return view;
    }

    @OnClick({R.id.left_menu_goals,R.id.left_menu_nots})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.left_menu_goals:
                lastTitle = R.string.left_menu_goals;
                getMenu().openGoals();
                break;
            case R.id.left_menu_nots:
                lastTitle = R.string.left_menu_nots;
                getMenu().openNotes();
                break;
        }

        drawerLayout.closeDrawers();
    }



    private IOpenMenu getMenu(){
        return  ((MainActivity)getActivity()).getOpenMenu();
    }
}
