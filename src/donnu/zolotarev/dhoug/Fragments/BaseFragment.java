package donnu.zolotarev.dhoug.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import donnu.zolotarev.dhoug.Activities.MainActivity;
import donnu.zolotarev.dhoug.Activities.SingleFragmentActivity;
import donnu.zolotarev.dhoug.R;

public abstract class BaseFragment extends Fragment {


    protected View inflateFragmentView(int layoutResource, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(layoutResource, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


    protected abstract int getTitleId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    protected void toast(int messageId) {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, messageId, Toast.LENGTH_SHORT).show();
        }
    }

    protected void toast(String message) {
        Activity activity = getActivity();
        if (activity != null) {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }

    protected void toastInRun(final String message){
        if (getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast(message);
                }
            });
        }
    }

    protected void toastInRun(final int message){
        if (getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast(message);
                }
            });
        }
    }

    public void showAlert(int messageId, String title){
        showAlert(getString(messageId), title);
    }

    public void showAlert(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(title == null){
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle(R.string.default_alert_title);
        } else {
            builder.setTitle(title);
        }
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();
    }

    protected void back(){
        SingleFragmentActivity activity = getMainActivity();
        if(activity != null){
            activity.popBackStack();
        }
    }

    protected MainActivity getMainActivity(){
        Activity activity = getActivity();
        if(activity != null && activity instanceof MainActivity){
            return (MainActivity) activity;
        }

        return null;
    }

    protected void showFragment(BaseFragment fragment, boolean addToBackStack) {
        SingleFragmentActivity activity = getMainActivity();
        if(activity != null){
            activity.loadRootFragment(fragment, addToBackStack);
        }
    }

    public void hideKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = getActivity().getCurrentFocus();
            if (view != null){
                view.clearFocus();
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e){

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int id = getTitleId();
        if (id != 0) {
            getMainActivity().setTitleText(id);
        }
    }
}
