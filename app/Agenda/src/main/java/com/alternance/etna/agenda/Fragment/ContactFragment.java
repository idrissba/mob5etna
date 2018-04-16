package com.alternance.etna.agenda.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alternance.etna.agenda.Class.Constant;
import com.alternance.etna.agenda.Class.MyCalendar;
import com.alternance.etna.agenda.Manager.UserData;
import com.alternance.etna.agenda.Popup.AddContactActivity;
import com.alternance.etna.agenda.R;
import com.alternance.etna.agenda.models.ContactEntity;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public class ContactsTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            UserData ud = UserData.getInstance();

            ud._myContacts = ud.getContactList();
            if (ud._myContacts.size() > 0) {
                ContactFragment.this.mContactList.clear();
                ContactFragment.this.mContactList.addAll((ud._myContacts));
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean backgroundRet) {

            if (backgroundRet) {
                ContactFragment.this.mCustomContactAdapter.notifyDataSetChanged();
            }
        }
    }


    private List<ContactEntity> mContactList = new ArrayList<>();
    private TextView mContactTitle;
    private ListView mContactListView;
    private ContactFragment.CustomContactAdapter mCustomContactAdapter;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ContactsTask task = new ContactsTask();
        task.execute();
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            mContactTitle = view.findViewById(R.id.contactTitle);
            mContactListView = view.findViewById(R.id.contactListView);
            mCustomContactAdapter = new ContactFragment.CustomContactAdapter();

            mContactTitle.setText(R.string.ContactFragment_contact_tilte);
            mContactList.clear();
            mContactList.addAll(UserData.getInstance()._myContacts);

            ImageButton contactAdd = view.findViewById(R.id.contactAddButton);
            contactAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivityForResult(new Intent(getActivity(), AddContactActivity.class), 997);
                    }
                    catch (Exception exp){
                        Log.e("Exception", "ContactFragment.onViewCreated().onClick() => " + exp.getMessage());
                    }
                }
            });

            Update();
        }
        catch (Exception exp){
            Log.e("Exception", "ContactFragment.onViewCreated() => " + exp.getMessage());
        }
    }

    /*
     * Cette fonction permet de rafraichir la liste de contact
     */
    private void Update()
    {
        try {
            mContactListView.setAdapter(mCustomContactAdapter);
        }
        catch (Exception exp){
            Log.e("Exception", "ContactFragment.Update() => " + exp.getMessage());
        }
    }

    /*
     * Cette classe indique comment remplir chaque element personalisé de notre liste de contact.
     */
    class CustomContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mContactList.size();
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
        public View getView(int i, View view, ViewGroup parent) {
            try {
                view = getLayoutInflater().inflate(R.layout.layout_contact_item, null);

                //Recuperation des elements de notre vue (Item)
                final TextView contactId = view.findViewById(R.id.contactId);
                final TextView contactName = view.findViewById(R.id.contactName);
                final TextView contactFirstName = view.findViewById(R.id.contactFirstName);
                final TextView contactNumber = view.findViewById(R.id.contactNumber);
                ImageButton contactWatch = view.findViewById(R.id.contactWatchButton);
                ImageButton contactDelete = view.findViewById(R.id.contactDeleteButton);

                //Remplissage de notre item
                contactId.setText(String.valueOf(mContactList.get(i).getId()));
                contactName.setText(mContactList.get(i).getLastName().toUpperCase());
                contactFirstName.setText(mContactList.get(i).getFirstName());
                contactNumber.setText(mContactList.get(i).getNumber());

                final int position = i;

                contactWatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            MyCalendar c = new MyCalendar(mContactList.get(position).getFirstName().substring(0, 1).toUpperCase() + mContactList.get(position).getFirstName().substring(1).toLowerCase() + " " + mContactList.get(position).getLastName().toUpperCase(), UserData.getInstance().getEventList());
//                        mfCalendar = CalendarFragment.MyCalendarFragment(c, false);
                        }
                        catch (Exception exp){
                            Log.e("Exception", "ContactFragment.CustomAdapter.getView().onClick() => " + exp.getMessage());
                        }
                    }
                });

                contactDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContactList.remove(position); // a modifier, pour ajouter la suppression bdd
                        Update();
                    }
                });
            }
            catch (Exception exp){
                Log.e("Exception", "ContactFragment.ContactFragment.getView() => " + exp.getMessage());
            }

            return view;
        }
    }

    /*
     * Cette fonction gère les retours des Intents appelés avec la méthode "startActivityForResult()"
     * Comme c'est le cas pour AddContact
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == 997 && resultCode == Constant.ACTION_ADD && data != null) {
                ContactEntity contact = (ContactEntity)data.getSerializableExtra("ContactEntity");
                String FirstName = contact.getFirstName();
                String LastName = contact.getLastName();
                String Number = contact.getNumber();
                mContactList.add(new ContactEntity(mContactList.size(), FirstName == null ? "" : FirstName, LastName == null ? "" : LastName, Number == null ? "" : Number));
                Update();
            }
        }
        catch (Exception exp){
            Log.e("Exception", "ContactFragment.onActivityResult() => " + exp.getMessage());
        }
    }

}
