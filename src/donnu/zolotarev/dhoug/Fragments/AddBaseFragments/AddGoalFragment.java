package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Enums.GOAL_REPETITION;
import donnu.zolotarev.dhoug.Fragments.Dialogs.DatePickerFragment;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;
import donnu.zolotarev.dhoug.Utils.Utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private GOAL_REPETITION lastRepetition;

    PopupMenu.OnMenuItemClickListener periodListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            period.setText(item.getTitle());
            return true;
        }
    };

    public static AddGoalFragment edit(GoalItem someItem) {
        AddGoalFragment fragment = new AddGoalFragment();
        Bundle boundle = new Bundle();
        boundle.putSerializable(ITEM,someItem);
        fragment.setArguments(boundle);
        return fragment;
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
        lastRepetition = GOAL_REPETITION.NO;

        period.setText(R.string.notes_validate_in_perpetuity);
        if (arg == null) {
           return;
        }
        GoalItem goalItem = (GoalItem)arg.get(ITEM);
        title.setText(goalItem.getTitle());
        subTitle.setText(goalItem.getDescription());
        subTitle.setText(goalItem.getDescription());
        endDate.setText(Utils.getFormatData(Constants.DATE_FORMAT, goalItem.getTimeEnd()));
        endTime.setText(Utils.getFormatData(Constants.TIME_FORMAT, goalItem.getTimeEnd()));
        beginDate.setText(Utils.getFormatData(Constants.DATE_FORMAT, goalItem.getTimeStart()));
        beginTime.setText(Utils.getFormatData(Constants.TIME_FORMAT, goalItem.getTimeStart()));

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

    @Override
    protected Serializable getItem() {
        GoalItem goalItem = new GoalItem();
        goalItem.setRepetition(lastRepetition);
        goalItem.setTitle(getText(title));
        goalItem.setDescription(getText(subTitle));
        goalItem.setTimeStart(getTime(beginDate,beginTime));
        goalItem.setTimeEnd(getTime(endDate,endTime));
        return goalItem;
    }

    private String getText(TextView view){
         return  view.getText().toString();
    }

    private Date getTime(TextView view,TextView view2){
        Date time = new Date(0);
        //
        time = addTime(time,Constants.DATE_FORMAT,view);
        time = addTime(time,Constants.TIME_FORMAT,view2);
        return time;
    }

    private Date addTime(Date time,String format,TextView view){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String date = getText(view);
        if (!date.isEmpty()) {
            try {
                time.setTime(simpleDateFormat.parse(date).getTime() + time.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }
}
