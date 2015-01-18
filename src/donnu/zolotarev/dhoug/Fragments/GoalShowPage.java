package donnu.zolotarev.dhoug.Fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import donnu.zolotarev.dhoug.Adapters.NotesAdapter;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Enums.ENTITY;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddBaseFragment;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddNotesFragment;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.Interface.IDataHolfer;
import donnu.zolotarev.dhoug.R;

import java.io.Serializable;
import java.util.ArrayList;

public class GoalShowPage extends BaseFragment {

    public static final String ITEM = "item";

    @InjectView(R.id.list)
    ListView listView;
    private NotesAdapter noteAdapted;
    private GoalItem goalItem;


    public static GoalShowPage open( GoalItem someItem) {
        GoalShowPage fragment = new GoalShowPage();
        Bundle boundle = new Bundle();
        boundle.putSerializable(ITEM, someItem);
        fragment.setArguments(boundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_full_goal, inflater, container);
        updateViews();
        addHeader();
      //  popupMenu = setupPopupMenu(period, R.menu.add_periodmenu, periodListener);
        return view;
    }

    private void addHeader() {
        View headerView = getActivity().getLayoutInflater().inflate(R.layout.header_full_goals, listView, false);
        TextView title =  ButterKnife.findById(headerView, R.id.full_goal_title);
        TextView desc =  ButterKnife.findById(headerView,R.id.full_goal_decription);

        title.setText(goalItem.getTitle());
        desc.setText(goalItem.getDescription());

        listView.addHeaderView(headerView);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (noteAdapted == null) {
            IDataHolfer dataHolfer = getData();
            //todo переместить
            ArrayList<NoteItem> nItems = new ArrayList<NoteItem>();
            for(Object item: dataHolfer.get(ENTITY.NOTES)){
                NoteItem noteItem = (NoteItem)item;
                if (noteItem != null && goalItem.getId().equals(noteItem.getGoalId())) {
                    nItems.add(noteItem);
                }
            }
            noteAdapted = new NotesAdapter(getActivity(),nItems);
//        }
        noteAdapted.setClickListener(new IClick() {
            @Override
            public void click(Serializable goalItem) {
                AddNotesFragment showPage = AddNotesFragment.open(GoalShowPage.this, (NoteItem) goalItem, AddBaseFragment.SHOW);
                showFragment(showPage, true);
            }
        });

        listView.setAdapter(noteAdapted);
    }

    @Override
    public void onDestroyView() {
        noteAdapted.setClickListener(null);
        super.onDestroyView();
    }

    private void updateViews() {
        Bundle arg = getArguments();
        if (arg != null) {
            goalItem = (GoalItem)arg.get(ITEM);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.yes_no_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_accept:
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
}
