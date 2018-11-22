package com.xhy.xhyapp.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xhy.xhyapp.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class ShangpinTwoAdapter extends BaseAdapter{

    List<String> list;
    Activity act;

    public ShangpinTwoAdapter(Activity act , List<String> list){
        this.list = list;
        this.act = act;
    };

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = View.inflate(act, R.layout.fragment_shangpinguanli_two_item,null);
            viewHolder.pplus = ((TextView) view.findViewById(R.id.fragment_shangpin_two_pplus));
            viewHolder.pjian = ((TextView) view.findViewById(R.id.fragment_shangpin_two_pjian));
            viewHolder.tpuls = ((TextView) view.findViewById(R.id.fragment_shangpin_two_tplus));
            viewHolder.tjian= ((TextView) view.findViewById(R.id.fragment_shangpin_two_tjian));
            viewHolder.t = ((EditText) view.findViewById(R.id.fragment_shangpin_two_t));
            viewHolder.p = ((EditText) view.findViewById(R.id.fragment_shangpin_two_p));
            final ViewHolder vvv = viewHolder;
            viewHolder.pplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = vvv.p.getText().toString();
                    if (s.length()>0){
                        vvv.p.setText(Integer.parseInt(s)+1+"");
                        vvv.p.setSelection(s.length(),s.length());
                    }else {
                        vvv.p.setText(0+"");
                        vvv.p.setSelection(s.length(),s.length());
                    }
                }
            });
            viewHolder.pjian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = vvv.p.getText().toString();
                    if (Integer.parseInt(s)-1>0 && s.length()>0){
                        vvv.p.setText(Integer.parseInt(s)-1+"");
                        vvv.p.setSelection(s.length(),s.length());
                    }else {
                        vvv.p.setText(0+"");
                        vvv.p.setSelection(s.length(),s.length());
                    }
                }
            });
            viewHolder.tpuls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = vvv.t.getText().toString();
                    if (s.length()>0){
                        vvv.t.setText(Integer.parseInt(s)+1+"");
                        vvv.t.setSelection(s.length(),s.length());
                    }else {
                        vvv.t.setText(0+"");
                        vvv.t.setSelection(s.length(),s.length());
                    }
                }
            });
            viewHolder.tjian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s = vvv.t.getText().toString();
                    if (Integer.parseInt(s)-1>0 && s.length()>0){
                        vvv.t.setText(Integer.parseInt(s)-1+"");
                        vvv.t.setSelection(s.length(),s.length());
                    }else {
                        vvv.t.setText(0+"");
                        vvv.t.setSelection(s.length(),s.length());
                    }
                }
            });
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        return view;
    }

    private class ViewHolder {
        TextView tpuls,tjian,pplus,pjian;
        EditText t,p;
    }
}
