package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.PopupMenu;
import android.widget.TextView;
import donnu.zolotarev.dhoug.Fragments.BaseFragment;
import donnu.zolotarev.dhoug.R;

import java.io.Serializable;

public abstract class AddBaseFragment extends BaseFragment {

    public static final int ADD_NEW = 0;
    public static final int SHOW = 1;
    public static final int CHANGE = 2;
    public static final String ITEM = "item";
    protected static final String MODE = "mode";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.yes_no_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard();
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

    protected PopupMenu setupPopupMenu(final TextView view, int layoutID, PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.inflate(layoutID); // Для Android 4.0
        popupMenu.setOnMenuItemClickListener(listener);
        return popupMenu;
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

    public String getText(TextView view){
        return  view.getText().toString();
    }

}
