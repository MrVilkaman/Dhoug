package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import donnu.zolotarev.dhoug.Adapters.NotesAdapter;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddGoalFragment;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddNotesFragment;
import donnu.zolotarev.dhoug.R;

import java.util.ArrayList;

public class NotesFragment extends MainBaseFragments {

    private NotesAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (baseAdapted == null) {
            ArrayList<NoteItem> items = new ArrayList<NoteItem>();
            items.add(new NoteItem());
            items.add(new NoteItem());
            items.add(new NoteItem());
            baseAdapted = new NotesAdapter(activity,items);
            adapter = (NotesAdapter)baseAdapted;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                AddNotesFragment notesFragment = new AddNotesFragment();
                notesFragment.setTargetFragment(this, AddNotesFragment.ADD_NEW);
                showFragment(notesFragment, true);
                return true;
            case R.id.menu_edit:
                return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AddGoalFragment.ADD_NEW:
                NoteItem item = (NoteItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                adapter.add(item);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
