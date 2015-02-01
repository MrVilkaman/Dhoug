package donnu.zolotarev.dhoug.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import donnu.zolotarev.dhoug.Adapters.DataSources.NotesForGoalData;
import donnu.zolotarev.dhoug.Adapters.NotesDBAdapter;
import donnu.zolotarev.dhoug.DataModels.GoalItem;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddBaseFragment;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddGoalFragment;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddNotesFragment;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

public class GoalShowPage extends BaseFragment {

    public static final String ITEM = "item";

    @InjectView(R.id.list)
    ListView listView;
    private NotesDBAdapter noteAdapted;
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

            noteAdapted = new NotesDBAdapter(getActivity(), new NotesForGoalData(goalItem.getId()));
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
        inflater.inflate(R.menu.add_item, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                NoteItem obj = new NoteItem();
                obj.setGoalId(goalItem.getId());
                showFragment(AddNotesFragment.open(this, obj, AddBaseFragment.CHANGE), true);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddGoalFragment.CHANGE:
                    NoteItem item = (NoteItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                    item.save();
                    noteAdapted.doQuery();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
