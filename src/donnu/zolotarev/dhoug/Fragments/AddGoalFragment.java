package donnu.zolotarev.dhoug.Fragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.PopupMenu;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;
import donnu.zolotarev.dhoug.Utils.Utils;

import java.util.Date;

public class AddGoalFragment extends BaseFragment {

    public static final int ADD_NEW = 0;

    public static final String ITEM = "item";

    private PopupMenu popupMenu;

    @InjectView(R.id.add_goal_repeat)
    EditText period;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_add_goals,inflater,container);
        setupPopupMenu();

        period.setText(R.string.add_period_never);
        return view;
    }

    @OnClick({R.id.add_goal_begin_data,R.id.add_goal_end_data})
    void clickData(View view){
        showDatepickerDialog(view.getId());
    }

    @OnClick({R.id.add_goal_begin_time,R.id.add_goal_end_time})
    void clickTime(View view){
        showTimepickerDialog(view.getId());
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
               toast("Добавил!");
                sendResult(ADD_NEW);
                back();
                return true;
            case R.id.menu_cancel:
                back();
                return true;
        }

        return false;
    }

    private void showTimepickerDialog(int viewId) {
        FragmentManager fm = getFragmentManager();
        DatePickerFragment df = DatePickerFragment.newInstance(new Date(), viewId);
        df.setTargetFragment(this, DatePickerFragment.REQUEST_TIME);
        df.show(fm, DatePickerFragment.DIALOG_DATE);
    }

    private void showDatepickerDialog(int viewId) {
        FragmentManager fm = getFragmentManager();
        DatePickerFragment df = DatePickerFragment.newInstance(new Date(), viewId);
        df.setTargetFragment(this, DatePickerFragment.REQUEST_DATE);
        df.show(fm, DatePickerFragment.DIALOG_DATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DatePickerFragment.REQUEST_DATE:
            case DatePickerFragment.REQUEST_TIME:
                Date date = (Date) data.getExtras().getSerializable(DatePickerFragment.EXTRA_DATA);
                int id = data.getIntExtra(DatePickerFragment.EXTRA_VIEW_ID,-1);
                String format = requestCode  == DatePickerFragment.REQUEST_DATE?Constants.DATE_FORMAT:Constants.TIME_FORMAT;
                ((EditText) ButterKnife.findById(getView(), id)).setText(Utils.getFormatData(format, date));

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setupPopupMenu() {
        popupMenu = new PopupMenu(getActivity(), period);
        popupMenu.inflate(R.menu.periodmenu); // Для Android 4.0
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_period_never:
                        period.setText(R.string.add_period_never);
                        return true;
                    case R.id.add_period_every_day:
                        period.setText(R.string.add_period_every_day);
                        return true;
                    case R.id.add_period_every_week:
                        period.setText(R.string.add_period_every_week);
                        return true;
                    case R.id.add_period_every_mounth:
                        period.setText(R.string.add_period_every_mounth);
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
        intent.putExtra(ITEM, new GoalItem());
        getTargetFragment().onActivityResult(getTargetRequestCode(), requetCode, intent);

    }

}
