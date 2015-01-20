package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.AdapterView;
import com.activeandroid.Model;
import donnu.zolotarev.dhoug.Adapters.NotesAdapter;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddBaseFragment;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddGoalFragment;
import donnu.zolotarev.dhoug.Fragments.AddBaseFragments.AddNotesFragment;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

import java.io.Serializable;

public class NotesFragment extends MainBaseFragments {

    private NotesAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (baseAdapted == null) {
            //todo убрать

            baseAdapted = new NotesAdapter(activity,NoteItem.getAll());
            adapter = (NotesAdapter)baseAdapted;
        }
        adapter.setClickListener(new IClick() {
            @Override
            public void click(Serializable goalItem) {
                AddNotesFragment showPage = AddNotesFragment.open(NotesFragment.this,(NoteItem)goalItem, AddBaseFragment.SHOW);
                showFragment(showPage, true);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter.setClickListener(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                AddNotesFragment notesFragment = AddNotesFragment.createNew(this);
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
                item.save();
                adapter.add(item);
            break;
            case AddGoalFragment.CHANGE:
                item = (NoteItem) data.getExtras().getSerializable(AddGoalFragment.ITEM);
                item.save();
                adapter.change(item);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case CM_EDIT:
                AddNotesFragment notesFragment = AddNotesFragment.open(this, Model.load(NoteItem.class, menuInfo.id),AddNotesFragment.CHANGE);
                showFragment(notesFragment, true);
                return true;
            case CM_DELETE:
                NoteItem.delete(menuInfo.id);
                return true;
        }
        return false;
    }
}
