package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.NotesFragment;
import donnu.zolotarev.dhoug.R;

import java.io.Serializable;

public class AddNotesFragment extends AddBaseFragment {

    @InjectView(R.id.add_note_title)
    EditText title;

    @InjectView(R.id.add_note_subtitle)
    EditText subtitle;

    @InjectView(R.id.add_note_validity)
    EditText period;

    @InjectView(R.id.add_note_to_goal)
    EditText noteToGoal;

    private PopupMenu popupMenu;

    private NoteItem noteItemTemp;


    @SuppressLint("ValidFragment")
    private AddNotesFragment() {
    }

    public static AddNotesFragment open(Fragment target, NoteItem someItem, int mode) {
        AddNotesFragment fragment = new AddNotesFragment();
        Bundle boundle = new Bundle();
        boundle.putSerializable(ITEM,someItem);
        boundle.putSerializable(MODE,mode);
        fragment.setArguments(boundle);
        fragment.setTargetFragment(target,mode);
        return fragment;
    }
    public static AddNotesFragment createNew(NotesFragment notesFragment) {
        return open(notesFragment,null,ADD_NEW);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_add_notes, inflater, container);
        popupMenu = setupPopupMenu(period, R.menu.notes_validate_menu, notes_validate);
        updateViews();
        return view;
    }

    private void updateViews() {
        Bundle arg = getArguments();
        noteItemTemp = (NoteItem)arg.get(ITEM);
        if (arg == null || (arg != null && noteItemTemp == null)) {
            noteItemTemp = new NoteItem();
            period.setText(R.string.add_period_never);
            noteToGoal.setText(R.string.note_to_goal_not_attached);
            return;
        }
        title.setText(noteItemTemp.getTitle());
        subtitle.setText(noteItemTemp.getDiscription());
        // noteToGoal
    }

    @OnClick(R.id.add_note_validity)
    void clickPeriod(){
        popupMenu.show();
    }

    @Override
    protected Serializable getItem() {
        noteItemTemp.setTitle(getText(title));
        noteItemTemp.setDiscription(getText(subtitle));
        return noteItemTemp;
    }

    private PopupMenu.OnMenuItemClickListener notes_validate = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            period.setText(item.getTitle());
            return false;
        }
    };


}
