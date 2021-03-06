package donnu.zolotarev.dhoug.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import donnu.zolotarev.dhoug.Enums.TIME_PERIOD;

public abstract class QuickAdapter extends BaseAdapter {

    protected final DataSource mDataSource;
    protected int mSize = 0;
    protected Cursor mRowIds = null;
    protected final Context mContext;

    public QuickAdapter(Context context, DataSource dataSource){
        mDataSource = dataSource;
        mContext = context;
        doQuery();
    }

    public void doQuery(){
        if(mRowIds!=null){
            mRowIds.close();
        }
        mRowIds = mDataSource.getRowIds();
        mSize = mRowIds.getCount();
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public Object getItem(int position) {
        if(mRowIds.moveToPosition(position)){
            long rowId = mRowIds.getLong(0);
            Cursor c = mDataSource.getRowById(rowId);
            return c;
        }else{
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(mRowIds.moveToPosition(position)){
            long rowId = mRowIds.getLong(0);
            return rowId;
        }else{
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mRowIds.moveToPosition(position);
        long rowId = mRowIds.getLong(0);
        Cursor cursor = mDataSource.getRowById(rowId);
        cursor.moveToFirst();
        View v;
        if (convertView == null) {
            v = newView(mContext, cursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, mContext, cursor);
        cursor.close();
        return v;
    }

    public abstract View newView(Context context, Cursor cursor, ViewGroup parent);
    public abstract void bindView(View view, Context context, Cursor cursor);

    public interface DataSource {
        Cursor getRowIds();
        Cursor getRowById(long rowId);
        void setRepetition(TIME_PERIOD repetition);
    }

}
