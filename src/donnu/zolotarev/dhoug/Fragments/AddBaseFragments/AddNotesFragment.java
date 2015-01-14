package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.R;

import java.io.Serializable;

public class AddNotesFragment extends AddBaseFragment {


    @InjectView(R.id.add_note_validity)
    EditText period;

    @InjectView(R.id.add_note_to_goal)
    EditText noteToGoal;

    private PopupMenu popupMenu;

    private PopupMenu.OnMenuItemClickListener notes_validate = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            period.setText(item.getTitle());
            return false;
        }
    };

    public static AddNotesFragment edit(NoteItem noteItem){
        AddNotesFragment fragment = new AddNotesFragment();
        Bundle boundle = new Bundle();
        boundle.putSerializable(ITEM,noteItem);
        fragment.setArguments(boundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_add_notes, inflater, container);
        popupMenu = setupPopupMenu(period, R.menu.notes_validate_menu, notes_validate);
        period.setText(R.string.add_period_never);
        noteToGoal.setText(R.string.note_to_goal_not_attached);
        return view;
    }

    @OnClick(R.id.add_note_validity)
    void clickPeriod(){
        popupMenu.show();
    }

    @Override
    protected Serializable getItem() {
        return new NoteItem();
    }
}
