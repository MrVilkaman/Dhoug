package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Fragments.Dialogs.DatePickerFragment;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.GoalsFragment;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;
import donnu.zolotarev.dhoug.Utils.Convertors;
import donnu.zolotarev.dhoug.Utils.Utils;

import java.io.Serializable;
import java.util.Date;

public class AddGoalFragment extends AddBaseFragment {
    @InjectView(R.id.add_goal_repeat)
    EditText period;

    @InjectView(R.id.add_goal_title)
    EditText title;

    @InjectView(R.id.add_goal_subtitle)
    EditText subTitle;

    @InjectView(R.id.add_goal_begin_time)
    EditText beginTime;

    @InjectView(R.id.add_goal_end_time)
    EditText endTime;

    @InjectView(R.id.add_goal_begin_data)
    EditText beginDate;

    @InjectView(R.id.add_goal_end_data)
    EditText endDate;

    private PopupMenu popupMenu;
    private GoalItem goalItemTemp;
    private int mode;

    @SuppressLint("ValidFragment")
    private AddGoalFragment() {
    }

    public static AddGoalFragment open(GoalsFragment target, GoalItem someItem, int mode) {
        AddGoalFragment fragment = new AddGoalFragment();
        Bundle boundle = new Bundle();
        boundle.putSerializable(ITEM,someItem);
        boundle.putSerializable(MODE,mode);
        fragment.setArguments(boundle);
        fragment.setTargetFragment(target,mode);
        return fragment;
    }

    public static AddGoalFragment createNew(GoalsFragment target) {
        return open(target,null,ADD_NEW);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_add_goals, inflater, container);
        popupMenu = setupPopupMenu(period, R.menu.add_periodmenu, periodListener);
        updateViews();
        return view;
    }

    private void updateViews() {
        Bundle arg = getArguments();

        period.setText(R.string.notes_validate_in_perpetuity);
        goalItemTemp = (GoalItem)arg.get(ITEM);
        if (arg == null || (arg != null && goalItemTemp == null)) {
            goalItemTemp = new GoalItem();
           return;
        }
        title.setText(goalItemTemp.getTitle());
        subTitle.setText(goalItemTemp.getDescription());
        subTitle.setText(goalItemTemp.getDescription());
        if (goalItemTemp.getTimeEnd() != null) {
            endDate.setText(Utils.getFormatData(Constants.DATE_FORMAT, goalItemTemp.getTimeEnd()));
            endTime.setText(Utils.getFormatData(Constants.TIME_FORMAT, goalItemTemp.getTimeEnd()));
        }
        if (goalItemTemp.getTimeStart() != null) {
            beginDate.setText(Utils.getFormatData(Constants.DATE_FORMAT, goalItemTemp.getTimeStart()));
            beginTime.setText(Utils.getFormatData(Constants.TIME_FORMAT, goalItemTemp.getTimeStart()));
        }
        mode = getArguments().getInt(MODE);
        title.setEnabled(mode != SHOW);
        subTitle.setEnabled(mode != SHOW);
        period.setText(Convertors.enumGoalsToText(goalItemTemp.getRepetition()));

    }

    @OnClick(R.id.add_goal_begin_data)
    void clickBData(View view){
        if (mode != SHOW) {
            showDatepickerDialog(view.getId(),goalItemTemp.getTimeStart());
        }
    }

    @OnClick(R.id.add_goal_end_data)
    void clickEData(View view){
        if (mode != SHOW) {
            showDatepickerDialog(view.getId(),goalItemTemp.getTimeEnd());
        }
    }

    @OnClick(R.id.add_goal_begin_time)
    void clickBTime(View view){
        if (mode != SHOW) {
            showTimepickerDialog(view.getId(), goalItemTemp.getTimeStart());
        }
    }
    @OnClick( R.id.add_goal_end_time)
    void clickETime(View view){
        if (mode != SHOW) {
            showTimepickerDialog(view.getId(), goalItemTemp.getTimeEnd());
        }
    }

    @OnClick(R.id.add_goal_repeat)
    void clickPeriod(){
        if (mode != SHOW) {
            popupMenu.show();
        }
    }

    private void showTimepickerDialog(int viewId, Date data) {
        FragmentManager fm = getFragmentManager();
        DatePickerFragment df = DatePickerFragment.newInstance(data, viewId);
        df.setTargetFragment(this, DatePickerFragment.REQUEST_TIME);
        df.show(fm, DatePickerFragment.DIALOG_DATE);
    }

    private void showDatepickerDialog(int viewId, Date data) {
        FragmentManager fm = getFragmentManager();
        DatePickerFragment df = DatePickerFragment.newInstance(data, viewId);
        df.setTargetFragment(this, DatePickerFragment.REQUEST_DATE);
        df.show(fm, DatePickerFragment.DIALOG_DATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case DatePickerFragment.REQUEST_DATE:
                case DatePickerFragment.REQUEST_TIME:
                    Date date = (Date) data.getExtras().getSerializable(DatePickerFragment.EXTRA_DATA);
                    int id = data.getIntExtra(DatePickerFragment.EXTRA_VIEW_ID,-1);
                    String format = requestCode  == DatePickerFragment.REQUEST_DATE?Constants.DATE_FORMAT:Constants.TIME_FORMAT;
                    ((EditText) ButterKnife.findById(getView(), id)).setText(Utils.getFormatData(format, date));
                    if (id == R.id.add_goal_begin_data || id == R.id.add_goal_begin_time) {
                        goalItemTemp.setTimeStart(date);
                    }else{
                        goalItemTemp.setTimeEnd(date);
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected Serializable getItem() {
        goalItemTemp.setTitle(getText(title));
        goalItemTemp.setDescription(getText(subTitle));
        return goalItemTemp;
    }

    PopupMenu.OnMenuItemClickListener periodListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            period.setText(item.getTitle());
            goalItemTemp.setRepetition(Convertors.menuIdToEnumGoalReplay(item.getItemId()));
            return true;
        }
    };
}
