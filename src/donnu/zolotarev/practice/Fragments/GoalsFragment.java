package donnu.zolotarev.practice.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import donnu.zolotarev.practice.R;

public class GoalsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflateFragmentView(R.layout.fragment_goals,inflater,container);
        return view;
    }

    /*@OnClick(R.id.button)
    void onClick(){

    }*/
}
