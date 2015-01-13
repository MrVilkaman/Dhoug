package donnu.zolotarev.dhoug.Fragments;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.Activities.MainActivity;
import donnu.zolotarev.dhoug.Interface.IOpenMenu;
import donnu.zolotarev.dhoug.R;

public class LeftMenuFragmenu extends BaseFragment {


    private SlidingPaneLayout drawerLayout;
    private int lastTitle = R.string.left_menu_goals;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.left_menu_fragmenu,inflater,container);
        drawerLayout = (SlidingPaneLayout) ButterKnife.findById(getActivity(), R.id.drawer_layout);
        drawerLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelOpened(View view) {
                ActionBar bar = getActivity().getActionBar();
                bar.setTitle(R.string.app_name);
            }

            @Override
            public void onPanelClosed(View view) {
                ActionBar bar = getActivity().getActionBar();
                bar.setTitle(lastTitle);
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

        drawerLayout.closePane();
    }



    private IOpenMenu getMenu(){
        return  ((MainActivity)getActivity()).getOpenMenu();
    }
}
