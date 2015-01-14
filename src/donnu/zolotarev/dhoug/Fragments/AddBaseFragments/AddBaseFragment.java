package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.Fragments.BaseFragment;
import donnu.zolotarev.dhoug.R;

import java.io.Serializable;

public abstract class AddBaseFragment extends BaseFragment {

    public static final int ADD_NEW = 0;
    public static final String ITEM = "item";

    private PopupMenu popupMenu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_add_goals,inflater,container);
        return view;
    }

    @OnClick(R.id.add_goal_repeat)
    void clickPeriod(){
        popupMenu.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.yes_no_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_accept:
                sendResult(ADD_NEW);
                back();
                return true;
            case R.id.menu_cancel:
                back();
                return true;
        }
        return false;
    }


    protected void setupPopupMenu(final TextView view) {
        popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(R.menu.add_periodmenu); // Для Android 4.0
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_period_never:
                        view.setText(R.string.add_period_never);
                        return true;
                    case R.id.add_period_every_day:
                        view.setText(R.string.add_period_every_day);
                        return true;
                    case R.id.add_period_every_week:
                        view.setText(R.string.add_period_every_week);
                        return true;
                    case R.id.add_period_every_mounth:
                        view.setText(R.string.add_period_every_mounth);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void sendResult(int requetCode) {
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(ITEM, getItem());
        getTargetFragment().onActivityResult(getTargetRequestCode(), requetCode, intent);

    }

    protected abstract Serializable getItem();

}
