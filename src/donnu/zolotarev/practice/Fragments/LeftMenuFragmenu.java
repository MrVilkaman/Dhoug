package donnu.zolotarev.practice.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import donnu.zolotarev.practice.R;

public class LeftMenuFragmenu extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.left_menu_fragmenu,inflater,container);
        return view;
    }
}
