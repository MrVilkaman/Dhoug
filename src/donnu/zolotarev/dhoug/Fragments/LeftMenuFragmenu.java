package donnu.zolotarev.dhoug.Fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.Activities.MainActivity;
import donnu.zolotarev.dhoug.Fragments.Dialogs.DialogFragment;
import donnu.zolotarev.dhoug.Interface.IOpenMenu;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;

public class LeftMenuFragmenu extends BaseFragment {


    private DrawerLayout drawerLayout;
    private CharSequence lastTitle = null;

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
                lastTitle = bar.getTitle();
                bar.setTitle(R.string.app_name);
            }

            @Override
            public void onDrawerClosed(View view) {
                if (lastTitle != null) {
                    ActionBar bar = getActivity().getActionBar();
                    bar.setTitle(lastTitle);
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        return view;
    }

    @OnClick({R.id.left_menu_goals,R.id.left_menu_nots,R.id.left_menu_about})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.left_menu_goals:
                getMenu().openGoals();
                break;
            case R.id.left_menu_nots:
                getMenu().openNotes();
                break;
            case R.id.left_menu_about:
                openAbout();
                break;
        }
        lastTitle = null;
        drawerLayout.closeDrawers();
    }

    private void openAbout() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.about,null);
        DialogFragment fragment = new DialogFragment();
        fragment.show(getFragmentManager(),"1");
        fragment.setTitle(getString(R.string.left_menu_about));
        fragment.setView(view);

        TextView textVersionView = ButterKnife.findById(view,R.id.about_version_text);
        String textVersion = getString(R.string.about_text_version,getString(R.string.app_name), getAppVersion());
        textVersionView.setText(textVersion);
        view.findViewById(R.id.about_email_btn_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gmail = new Intent(Intent.ACTION_VIEW);
                gmail.setClassName("com.google.android.gm","com.google.android.gm.ComposeActivityGmail");
                gmail.putExtra(Intent.EXTRA_EMAIL, new String[] { Constants.OUR_EMAIL });
                gmail.setData(Uri.parse(Constants.OUR_EMAIL));
//                gmail.putExtra(Intent.EXTRA_SUBJECT, "enter something");
                gmail.setType("plain/text");
//                gmail.putExtra(Intent.EXTRA_TEXT, "hi android jack!");
                startActivity(gmail);
                getActivity().startActivity(gmail);
            }
        });
       /* view.findViewById(R.id.offical_group_link_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri address = Uri.parse(Constants.VK_GROUP_LINK);
                Intent openlink = new Intent(Intent.ACTION_VIEW, address);
                getActivity().startActivity(openlink);
            }
        });*/

    }

    private String getAppVersion() {
        PackageInfo packinfo = null;
        try {
            packinfo = getActivity().getPackageManager().getPackageInfo(Constants.APP_PACKAGE, PackageManager.GET_ACTIVITIES);
            return packinfo.versionName.toString();
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "";
    }

    private IOpenMenu getMenu(){
        return  ((MainActivity)getActivity()).getOpenMenu();
    }

    public void setTitle(int about_email_btn_text) {
        getActivity().getActionBar().setTitle(about_email_btn_text);
    }

    @Override
    protected int getTitleId() {
        return 0;
    }
}
