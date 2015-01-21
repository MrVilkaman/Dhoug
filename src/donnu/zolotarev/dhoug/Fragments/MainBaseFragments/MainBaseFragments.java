package donnu.zolotarev.dhoug.Fragments.MainBaseFragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import butterknife.InjectView;
import butterknife.OnClick;
import donnu.zolotarev.dhoug.Adapters.IAdapter;
import donnu.zolotarev.dhoug.Fragments.BaseFragment;
import donnu.zolotarev.dhoug.R;
import donnu.zolotarev.dhoug.Utils.Constants;
import donnu.zolotarev.dhoug.Utils.Utils;

abstract class MainBaseFragments extends BaseFragment {

    static final int CM_EDIT = 0;
    static final int CM_DELETE = 1;

    protected ListAdapter baseAdapted;

    @InjectView(R.id.list)
    protected ListView listView;

    @InjectView(R.id.goals_current_data)
    protected TextView calendar;

    @InjectView(R.id.goals_goals_period)
    protected TextView period;

    protected PopupMenu popupMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateFragmentView(R.layout.fragment_goals,inflater,container);
        calendar.setText(Utils.getCurrentFormatDate(Constants.CURRENT_DATE));
        setup();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setAdapter(baseAdapted);
        listView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_EDIT, 0, R.string.context_menu_edit);
        menu.add(0, CM_DELETE, 1, R.string.context_menu_delete);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.goals_menu, menu);
    }


    private void setup() {
        popupMenu = new PopupMenu(getActivity(), period);
        popupMenu.inflate(R.menu.periodmenu); // Для Android 4.0
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                period.setText(item.getTitle());
                return true;
            }
        });
    }

    @OnClick(R.id.goals_goals_period)
    void onPediod(){
        popupMenu.show();
    }

   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, CM_EDIT, 0, R.string.context_menu_edit);
        menu.add(0, CM_DELETE, 1, R.string.context_menu_delete);
//        super.onCreateContextMenu(menu, v, menuInfo);
    }*/



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
       switch (item.getItemId()){
          case CM_DELETE:
               ((IAdapter)baseAdapted).delete(menuInfo.position);
               return true;
       }
        return false;
    }
}
