package com.rakide.rasp_v.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.rakide.rasp_v.MainActivity;
import com.rakide.rasp_v.R;
import com.rakide.rasp_v.adapter.ContactAdapter;
import com.rakide.rasp_v.object.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private FloatingActionButton fab;
    private ListView lvContact;
    private Context context;
    Contact list = new Contact();
    List<Contact> listContact = new ArrayList<>();

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        // =================

        list.displayName = "Fany Agriansyah";
        list.username = "(fanyagriansyah)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Yoga Bayu Aji Pranawa";
        list.username = "(ybpranawa)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Kharisma Nur Annisa";
        list.username = "(kharismana)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Tities Novaninda Ovaria";
        list.username = "(novanindaovaria)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Nindyasari";
        list.username = "(nindyasaridu)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Yohana Desy";
        list.username = "(yohanaseul)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Demsy Iman Mutasyar";
        list.username = "(demsyiman)";
        listContact.add(list);

        list = new Contact();
        list.displayName = "Imagine Clara Arabella";
        list.username = "(inearabella)";
        listContact.add(list);

        // =================
        lvContact = (ListView)view.findViewById(R.id.lvContact);
        lvContact.setAdapter(new ContactAdapter(this.getActivity(),listContact));
        registerForContextMenu(lvContact);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "onItemClick()", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.lvContact) {
            AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(listContact.get((int)info.id).displayName);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list_contact, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.call_contact:
                Toast.makeText(getActivity(),"Calling "+listContact.get((int)info.id).displayName+"..." , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_contact:
                Toast.makeText(getActivity(),listContact.get((int)info.id).displayName+" has been deleted ceritanya" , Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
