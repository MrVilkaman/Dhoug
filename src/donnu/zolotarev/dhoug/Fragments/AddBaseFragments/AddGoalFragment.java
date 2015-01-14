package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.Fragments.Dialogs.DatePickerFragment;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;
import donnu.zolotarev.dhoug.Utils.Utils;

import java.io.Serializable;
import java.util.Date;

public class AddGoalFragment extends AddBaseFragment {

    @InjectView(R.id.add_goal_repeat)
    EditText period;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupPopupMenu(period);
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
        return new GoalItem();
    }
}
