package donnu.zolotarev.practice.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.InjectView;
import donnu.zolotarev.practice.Adapters.NotesAdapter;
import donnu.zolotarev.practice.DataModels.NoteItem;
import donnu.zolotarev.practice.R;

import java.util.ArrayList;

public class NotesFragment extends BaseFragment {

    private NotesAdapter adapted;

    @InjectView(R.id.list)
    ListView listView;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (adapted == null) {
            ArrayList<NoteItem> items = new ArrayList<NoteItem>();
            items.add(new NoteItem());
            items.add(new NoteItem());
            items.add(new NoteItem());
            adapted = new NotesAdapter(activity,items);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_goals,inflater,container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setAdapter(adapted);
    }


}
