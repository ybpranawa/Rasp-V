package com.informatika.rasp_v.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.informatika.rasp_v.MainActivity;
import com.informatika.rasp_v.R;

public class ContactAdapter extends BaseAdapter {
    String[] listUsername;
    Context context;
    private TextView tvListContactNama;
    private Button btnContactCall;

    private static LayoutInflater inflater = null;

    public ContactAdapter(MainActivity mainActivity, String[] getListUsername){
        context = mainActivity;
        listUsername = getListUsername;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listUsername.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView tvListContactNama;
        Button btnContactCall;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.list_contact, null);

        holder.tvListContactNama = (TextView)rowView.findViewById(R.id.tvListContactNama);
        holder.btnContactCall = (Button)rowView.findViewById(R.id.btnContactCall);

        holder.tvListContactNama.setText(listUsername[i]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, listUsername[i], Toast.LENGTH_LONG).show();
            }
        });
        holder.btnContactCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Make call to "+listUsername[i], Toast.LENGTH_SHORT).show();
            }
        });
        return rowView;
    }
}
