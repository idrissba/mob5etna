package com.alternance.etna.agenda.Popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alternance.etna.agenda.Class.Constant;
import com.alternance.etna.agenda.Manager.UserData;
import com.alternance.etna.agenda.R;
import com.alternance.etna.agenda.models.ContactEntity;

import java.util.ArrayList;
import java.util.List;


public class AddContactActivity extends Activity {

    private SearchView contactSearch;
    private ListView contactListView;
    private List<ContactEntity> contactList = new ArrayList<>();
    private List<ContactEntity> contactListDisplayed = new ArrayList<>();
    private AddContactActivity.CustomContactAdapter customContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.popup_add_contact);
            customContactAdapter = new AddContactActivity.CustomContactAdapter();

            //Ce block permet de reduire la taille de la fenètre pour lui donner un look de popup
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = (int) (dm.widthPixels * 0.8);
            int height = (int) (dm.heightPixels * 0.6);
            getWindow().setLayout(width, height);

            //Recupération des elements de la vue
            contactSearch = findViewById(R.id.contactSearch);
            contactListView = findViewById(R.id.contactList);

            //création de la liste de contact
            contactList.clear();
            contactList.addAll(UserData.getInstance().getContactListNotAddYet());

            //Fonction de rechercher de contact
            contactSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    callSearch(query);
                    return true;                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    callSearch(newText);
                    return true;
                }

                public void callSearch(String query) {
                    try {
                        contactListDisplayed.clear();
                        for (ContactEntity contact : contactList) {
                            if (contact.getFirstName().toLowerCase().contains(query.toLowerCase())
                                    || contact.getLastName().toLowerCase().contains(query.toLowerCase())
                                    || contact.getNumber().toLowerCase().contains(query.toLowerCase()))
                                contactListDisplayed.add(contact);
                        }
                        Update();
                    }
                    catch (Exception exp){
                        Log.e("Exception", "AddContactActivity.onCreate().callSearch() => " + exp.getMessage());
                    }
                }
            });

            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    try {
                        ContactEntity contactEntity = contactList.get(position);
                        Intent i = new Intent();
                        ContactEntity contact;
                        if (contactEntity != null)
                            contact = new ContactEntity(contactEntity.getId(), contactEntity.getFirstName(), contactEntity.getLastName(), contactEntity.getNumber());
                        else
                            contact = new ContactEntity(contactEntity.getId(), "FirstName", "LastName", "Number");

                        i.putExtra("ContactEntity", contact);
                        setResult(Constant.ACTION_ADD, i);
                        finish();
                    }
                    catch (Exception exp){
                        Log.e("Exception", "AddContactActivity.onCreate().onItemClick().ADD => " + exp.getMessage());
                    }
                }
            });

            contactListDisplayed.addAll(contactList);
            Update();
        }
        catch (Exception exp){
            Log.e("Exception", "AddContactActivity.onCreate() => " + exp.getMessage());
        }
    }

    private void Update()
    {
        try {
            contactListView.setAdapter(customContactAdapter);
        }
        catch (Exception exp){
            Log.e("Exception", "AddContactActivity.Update() => " + exp.getMessage());
        }
    }

    class CustomContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contactListDisplayed.size();
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
                view = getLayoutInflater().inflate(R.layout.layout_contact_item_simple, null);

                //Recuperation des elements de notre vue (Item)
                final TextView contactName = view.findViewById(R.id.contactName);
                final TextView contactFirstName = view.findViewById(R.id.contactFirstName);
                final TextView ContactNumber = view.findViewById(R.id.contactNumber);

                //Remplissage de notre item
                contactName.setText(contactListDisplayed.get(i).getLastName().toUpperCase());
                contactFirstName.setText(contactListDisplayed.get(i).getFirstName());
                ContactNumber.setText(contactListDisplayed.get(i).getNumber());
            }
            catch (Exception exp){
                Log.e("Exception", "AddContactActivity.CustomContactAdapter.getView() => " + exp.getMessage());
            }
            return view;
        }
    }
}
