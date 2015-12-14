package com.rakide.rasp_v.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rakide.rasp_v.R;
import com.rakide.rasp_v.object.Contact;
import com.rakide.rasp_v.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
    List<Contact> listContact = new ArrayList<>();
    Context context;

    public ContactAdapter(Context mContext, List<Contact> mlistContact){
        listContact = mlistContact;
        context = mContext;
    }

    @Override
    public int getCount() {
//        return listUsername.length;
        return listContact.size();
    }

    @Override
    public Contact getItem(int position) {
        return listContact.get(position);
//        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_contact,viewGroup,false);
        }

        TextView tvDisplayName = (TextView)view.findViewById(R.id.tvDisplayName);
        TextView tvUsername = (TextView)view.findViewById(R.id.tvUsername);

        Contact list = listContact.get(i);
        Collections.sort(
                listContact,
                new Comparator<Contact>() {
                    public int compare(Contact lhs, Contact rhs) {
                        return lhs.displayName.compareTo(rhs.displayName);
                    }
                }
        );

        tvDisplayName.setText(list.displayName);
        tvUsername.setText(list.username);

        return view;
    }
}
