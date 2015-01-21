package donnu.zolotarev.dhoug.Fragments.AddBaseFragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Fragments.MainBaseFragments.NotesFragment;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Convertors;

import java.io.Serializable;

public class AddNotesFragment extends AddBaseFragment {

    @InjectView(R.id.add_note_title)
    EditText title;

    @InjectView(R.id.add_note_subtitle)
    EditText subtitle;

    @InjectView(R.id.add_note_validity)
    EditText period;

    @InjectView(R.id.add_note_to_goal)
    AutoCompleteTextView noteToGoal;

    private PopupMenu popupMenu;
    private ArrayAdapter<GoalItem> dropListAdapter;

    private NoteItem noteItemTemp;
    private int mode;
//    private UUID dropSubId;


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
        dropListAdapter =  new ArrayAdapter<GoalItem>(getActivity(),android.R.layout.simple_list_item_1);
        updateViews();
        popupMenu = setupPopupMenu(period, R.menu.notes_validate_menu, notes_validate);
        noteToGoal.setAdapter(dropListAdapter);
        noteToGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != SHOW) {
                    noteToGoal.showDropDown();
                }
            }
        });
        noteToGoal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoalItem item = (GoalItem)parent.getItemAtPosition(position);
                    noteItemTemp.setGoalId(item.getId());
            }
        });

        return view;
    }

    private void updateViews() {
        Bundle arg = getArguments();

        noteItemTemp = (NoteItem)arg.get(ITEM);
        mode = getArguments().getInt(MODE);

        period.setText(R.string.add_period_never);
        noteToGoal.setText(R.string.note_to_goal_not_attached);

        dropListAdapter.clear();
        GoalItem goal = new GoalItem();
        goal.setTitle(getString(R.string.note_to_goal_not_attached));
        //todo dropSubId = goal.getId();
        dropListAdapter.add(goal);
        dropListAdapter.addAll( GoalItem.getAll());
        dropListAdapter.notifyDataSetChanged();

        if (arg == null || (arg != null && noteItemTemp == null)) {
            noteItemTemp = new NoteItem();
            return;
        }
        if (noteItemTemp.getGoalId() != -1) {
            String s = GoalItem.getTitleById(noteItemTemp.getGoalId());
            if (!s.isEmpty()) {
                noteToGoal.setText(s);
            }else {
                noteToGoal.setText(R.string.note_to_goal_not_attached);
                noteItemTemp.setGoalId(-1);
            }
        } else{
            noteToGoal.setText(R.string.note_to_goal_not_attached);
        }

        title.setText(noteItemTemp.getTitle());
        subtitle.setText(noteItemTemp.getDiscription());
        period.setText(Convertors.enumNotesToText(noteItemTemp.getValidate()));
        title.setEnabled(mode != SHOW);
        subtitle.setEnabled(mode != SHOW);


        // noteToGoal
    }

    @OnClick(R.id.add_note_validity)
    void clickPeriod(){
        if (mode != SHOW) {
            popupMenu.show();
        }
    }

    @Override
    protected boolean isDataValid() {
        if (getText(title).isEmpty()) {
            showAlert(R.string.error_text_empty_title,null);
            return false;
        }

        return true;
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
            noteItemTemp.setValidate(Convertors.menuIdToEnumNotes(item.getItemId()));
            return false;
        }
    };
}
