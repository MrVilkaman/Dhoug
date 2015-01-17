package donnu.zolotarev.dhoug.Fragments.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import donnu.zolotarev.dhoug.R;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String DIALOG_DATE = "dialog_date";
    public static final int REQUEST_DATE = 1;
    public static final int REQUEST_TIME = 2;
    public static final int REQUEST_YEAR_ONLY = 3;

    public static final String EXTRA_DATA = "date";
    public static final String EXTRA_VIEW_ID = "view_id";
    private Date date;

    public static DatePickerFragment newInstance(Date date, int id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_DATA, date);
        bundle.putSerializable(EXTRA_VIEW_ID, id);
        DatePickerFragment dpf = new DatePickerFragment();
        dpf.setArguments(bundle);
        return dpf;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = (Date) getArguments().getSerializable(EXTRA_DATA);
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hout = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        View view;
        if (getTargetRequestCode() == REQUEST_DATE){
            view = createDateDialog(year, month, day, hout, minute);
        } else if (getTargetRequestCode() == REQUEST_TIME) {
            view = createTimeDialog(year, month, day, hout, minute);
        } else {
            view = createYearDialog(year, month, day, hout, minute);
        }

        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setView(view)
                .create();
    }

    private View createTimeDialog(final int year, final int month, final int day, final int hout, final int minute) {

        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setCurrentHour(hout);
        timePicker.setCurrentMinute(minute);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                date = new GregorianCalendar(year, month, day, i, i2, 0).getTime();
                getArguments().putSerializable(EXTRA_DATA, date);
            }
        });

        return view;
    }

    private View createDateDialog(final int year, final int month, final int day, final int hout, final int minute) {
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.setCalendarViewShown(false);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                date = new GregorianCalendar(i, i2, i3, hout, minute).getTime();
                getArguments().putSerializable(EXTRA_DATA, date);
            }
        });
        return view;
    }

    private View createYearDialog(final int year, final int month, final int day, final int hout, final int minute) {
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        findAndHideField(datePicker,"mDaySpinner");
        findAndHideField(datePicker,"mMonthSpinner");
        datePicker.setCalendarViewShown(false);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                date = new GregorianCalendar(i, i2, i3, hout, minute).getTime();
                getArguments().putSerializable(EXTRA_DATA, date);
            }
        });
        return view;
    }

    private void findAndHideField(DatePicker datepicker, String name) {
        try {
            Field field = DatePicker.class.getDeclaredField(name);
            field.setAccessible(true);
            View fieldInstance = (View) field.get(datepicker);
            fieldInstance.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendResult(int requetCode) {
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATA, date);
        intent.putExtra(EXTRA_VIEW_ID, getArguments().getSerializable(EXTRA_VIEW_ID));
        getTargetFragment().onActivityResult(getTargetRequestCode(), requetCode, intent);

    }
}
