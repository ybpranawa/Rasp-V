package com.informatika.rasp_v.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.informatika.rasp_v.MainActivity;
import com.informatika.rasp_v.R;
import com.informatika.rasp_v.adapter.ContactAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private FloatingActionButton fab;
    private ListView lvContact;
    private Context context;

    public static String[] listUsername = {
            "fanyagriansyah","ybpranawa","kharismana","novanindaovari","nindyasaridu",
            "yohanaseul","demsyiman","imaginearabella"
    };

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        lvContact = (ListView)view.findViewById(R.id.lvContact);
        lvContact.setAdapter(new ContactAdapter(this.getActivity(), listUsername));
        registerForContextMenu(lvContact);
        lvContact.setOnCreateContextMenuListener(this);

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
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Menu", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
//        lvContact.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Toast.makeText(getActivity(), "onTouch()", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
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
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_list_contact, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.v("Contacts", "Contact deleted ceritanya");
                        return super.onContextItemSelected(item);
//        AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//
//        switch(item.getItemId()) {
//            case R.id.delete_contact:
//                Toast.makeText(getActivity(), "Contact deleted ceritanya"+info.id, Toast.LENGTH_SHORT).show();
//                Log.v("Contacts", "Contact deleted ceritanya" + info.id);
//
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
    }
}
