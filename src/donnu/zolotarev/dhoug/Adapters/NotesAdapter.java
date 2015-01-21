package donnu.zolotarev.dhoug.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.ButterKnife;
import donnu.zolotarev.dhoug.DataModels.NoteItem;
import donnu.zolotarev.dhoug.Interface.IClick;
import donnu.zolotarev.dhoug.R;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter implements IAdapter {

    private final ArrayList<NoteItem> items;
    private final LayoutInflater layoutInflater;

    private IClick clickListener;

    public NotesAdapter(Activity context) {
        items = new ArrayList<NoteItem>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public NotesAdapter(Activity context, ArrayList<NoteItem> items) {
        this.items = items;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    public NoteItem getSomeItem(int i){
        return (NoteItem)getItem(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        final NoteItem noteItem;
        if (view == null) {
            view = inflateNewView(viewGroup);
        }
        holder = (ViewHolder)view.getTag();
        noteItem = getSomeItem(i);
        view.setLongClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(noteItem);
            }
        });
        holder.title.setText(noteItem.getTitle());
         holder.subTitle.setText(noteItem.getDiscription());
        holder.checkBox.setVisibility(View.INVISIBLE);
        return view;
    }

    private View inflateNewView(ViewGroup parent) {
        View view  = layoutInflater.inflate(R.layout.item_goal, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    public void add(NoteItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public void delete(long id) {
            items.remove((int)id);
            notifyDataSetChanged();
    }

    @Override
    public void change(Object item) {
    }

    private class ViewHolder {
        private final CheckBox checkBox;
        private final TextView title;
        private final TextView subTitle;

        public ViewHolder(View view) {
            checkBox =  ButterKnife.findById(view, R.id.checkBox);
            title =  ButterKnife.findById(view, R.id.goal_item_title);
            subTitle =  ButterKnife.findById(view, R.id.goal_item_sub_title);
        }
    }

    public IClick getClickListener() {
        return clickListener;
    }

    public void setClickListener(IClick clickListener) {
        this.clickListener = clickListener;
    }
}
