package donnu.zolotarev.dhoug.Fragments.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.R;


public class DialogFragment extends android.app.DialogFragment {

    @InjectView(R.id.text)
    TextView titleTextView;

    String title = null;
    private View.OnClickListener onOk;
    private View.OnClickListener onCancel;

    @InjectView(R.id.ok)
    Button buttonOK;

    @InjectView(R.id.cansel)
    Button buttonCancel;

    @InjectView(R.id.linearLayout)
    LinearLayout linearLayout;

    private int viewResId = 0;
    private String message;

    @InjectView(R.id.textMessage)
    TextView messageTextView;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  LayoutInflater inflater  = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        View view = null;

            view = inflateFragmentView(R.layout.fragment_dialog, inflater, null);

            if (viewResId != 0){
                View view2 =  inflater.inflate(viewResId,null, true);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lp.setMargins(10,5,10,5);
                view2.setLayoutParams(lp);
                linearLayout.addView(view2);
            }
        if (this.view != null){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(10,5,10,5);
            this.view.setLayoutParams(lp);
            linearLayout.addView(this.view);
        }

        if (onCancel !=null){
            buttonCancel.setVisibility(View.VISIBLE);
        }


            buttonOK.setVisibility(View.VISIBLE);

        if (message != null){
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(message);
        }

        if (title!=null){
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }

        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT_ARGB_PACKED_INT));

        return view;
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

    public void setTitle(String s){
        title = s;
    }

    public void setViewResId(int viewResId) {
        this.viewResId = viewResId;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setOkListener(View.OnClickListener listener){
       onOk = listener;
    //    buttonOK.setVisibility(View.VISIBLE);
    }

    public void setCancelListener(View.OnClickListener listener){
        onCancel = listener;
    }

    @OnClick(R.id.ok)
    void clickOk(){
        if (onOk !=null){
            onOk.onClick(null);
        }
        dismiss();
    }

    @OnClick(R.id.cansel)
    void clickCancel(){
        onCancel.onClick(null);
        dismiss();
    }

    protected View inflateFragmentView(int layoutResource, LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(layoutResource, container, false);
        try {
            ButterKnife.inject(this, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
