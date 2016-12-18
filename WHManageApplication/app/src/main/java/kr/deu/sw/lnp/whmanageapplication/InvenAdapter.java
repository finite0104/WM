package kr.deu.sw.lnp.whmanageapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admit on 2016-11-14.
 */
public class InvenAdapter extends BaseAdapter {

    Context context = null;
    LayoutInflater inflater = null;
    ArrayList<WHData> arrayList = null;

    InvenAdapter(Context context, ArrayList<WHData> arrayList) {
        //리스트 입력받을 경우에 리스트도 저장함
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            //view가 null값을 가지고있을때만 새로 생성함
            viewHolder = new ViewHolder();

            convertView.setTag(viewHolder);
        }else{
            //view가 한번 만들어진 적 있는 경우에는 viewholder를 가져옴
            viewHolder = (ViewHolder)convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {
        //리스트 한 줄에 들어가는 뷰들을 저장하는 클래스
        TextView tvPid;
        TextView tvPname;
    }
}
