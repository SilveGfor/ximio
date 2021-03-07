package com.example.ximio.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ximio.R;

public class FirstTBLAdapter extends BaseAdapter {
    private Context mContext;
    LayoutInflater inflater;

    private String[] mThumbIds = {
            "NaCl",
            "MgO",
            "MgO",
            "Mg4O4",
            "MgO",
            "NaCl",
            "NaCl",
            "MgO",
            "MgO",
            "Mg4O4",
            "MgO",
            "NaCl",
            "MgO",
            "MgO",
            "Mg4O4",
            "MgO",
            "NaCl",
            "NaCl",
            "MgO",
            "MgO",
            "Mg4O4",
            "MgO",
            "NaCl"
    };

    public FirstTBLAdapter(Context mContext)
    {
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null) inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_firsttbl, null);
        }
        TextView textView1 = convertView.findViewById(R.id.text_view_item1);
        TextView textView2 = convertView.findViewById(R.id.text_view_item2);

        textView1.setText(mThumbIds[position]);
        Log.d("a", mThumbIds[position]);
        textView2.setText("Good!");
        Log.d("a", "я тут");
        return convertView;
    }
}
