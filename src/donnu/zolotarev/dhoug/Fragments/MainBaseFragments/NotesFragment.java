package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.app.Activity;
import donnu.zolotarev.dhoug.Adapters.NotesAdapter;
import donnu.zolotarev.dhoug.DataModels.NoteItem;

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
}
