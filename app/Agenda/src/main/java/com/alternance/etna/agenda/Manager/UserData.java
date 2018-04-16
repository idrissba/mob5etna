package com.alternance.etna.agenda.Manager;

import android.app.Activity;
import android.util.Log;

import com.alternance.etna.agenda.models.ContactEntity;
import com.alternance.etna.agenda.models.ContactRelationEntity;
import com.alternance.etna.agenda.models.EventEntity;
import com.alternance.etna.agenda.models.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.alternance.etna.agenda.Class.Constant.FORMAT_DATE_STORED;
import static com.alternance.etna.agenda.Class.Constant.FORMAT_TIME;


public class UserData {
    private static UserData _instance = null;
    UserEntity _currentUser;
    public Activity _currentActivity;

    public List<EventEntity> _myEvents = new ArrayList<>();

    public List<ContactEntity> _myContactsNotAddYet = new ArrayList<>();
    public List<ContactEntity> _myContacts = new ArrayList<>();

    private UserData() {
    }

    public static UserData getInstance() {
        if (_instance == null)
            _instance = new UserData();
        return _instance;
    }

    public UserEntity getCurrentUser() {
        return this._currentUser;
    }

    public void setCurrentUser(UserEntity user) {
        this._currentUser = user;
    }

    private static String readUrl(String urlString) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {

                }
            }
        }
        return null;
    }

    public static String FixJsonOn(String url, String tableName) {
        StringBuilder sb = new StringBuilder();

        try {
            int index;
            int objIndex;
            String key;
            String value;
            String json = readUrl(url);
            JSONObject obj = new JSONObject(json);
            List<String> columns = new ArrayList<>();
            JSONArray columnArr = obj.getJSONObject(tableName).getJSONArray("columns");
            JSONArray recordArr = obj.getJSONObject(tableName).getJSONArray("records");

            if (json == null || json.isEmpty())
                return null;
            index = recordArr.length();
            if (index < 1)
                return null;
            //je recupere le noms des colonnes
            index = columnArr.length();
            for (int i = 0; i < index; ++i) {
                key = columnArr.get(i).toString();
                columns.add(key);
            }
            index = recordArr.length();
            if (index > 1)
                sb.append("[");
            //je recree un texte json en ajoutant (tableau si besoin) / cle / valeur
            for (int i = 0; i < index; ++i) {
                sb.append("{");
                JSONArray objRec = recordArr.getJSONArray(i);
                objIndex = objRec.length();
                for (int j = 0; j < objIndex; ++j) {
                    key = columns.get(j).toString();
                    value = objRec.getString(j);
                    sb.append("\"" + key + "\" : " + "\"" + value + "\"");
                    if (j != objRec.length() - 1)
                        sb.append(",");
                }
                sb.append("}");
                if (i != index - 1)
                    sb.append(",");
            }
            if (sb.charAt(0) == '[')
                sb.append("]");
        } catch (java.lang.Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }


    public static boolean Login(String username, String password) {
        String json = FixJsonOn("https://www.luminousapps.fr/mob5/api.php/user?filter=Login,eq," + username, "user");
        if (json == null || json.isEmpty()) // si aucune ligne retournee depuis la base de donnee = pas d'utilisateur avec ce username, donc on sort
            return false;
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserEntity user = mapper.readValue(json, UserEntity.class);
            if (user.getLogin().equals(username) && user.getPassword().equals(password)) {
                UserData.getInstance()._currentUser = user;
                return true;
            }

            //EN CAS DE RETOUR DE PLUSIEURS DONNEES, LES PARCOURIR COMME CI DESSOUS ;)
            /*
            UserEntity[] users = mapper.readValue(json, UserEntity[].class);
            int length = users.length;
            for (int i = 0; i < length; ++i)
            {
                UserEntity tmp = users[i];
                if (tmp.getLogin().equals(username) && tmp.getPassword().equals(password)) {
                    UserData.getInstance()._currentUser = tmp;
                    return true;
                }
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public boolean register() {
        //return true if login is correct, return false if wrong (based on sql request
        return true;
    }

    public List<ContactRelationEntity> getRelationships()
    {
        ObjectMapper mapper = new ObjectMapper();
        List<ContactRelationEntity> list = new ArrayList<>();
        String json = FixJsonOn("https://www.luminousapps.fr/mob5/api.php/relationship", "relationship");
        if (json == null || json.isEmpty())
            return list;
        try {
            if (json.charAt(0) != '[')
            {
                list.add(mapper.readValue(json, ContactRelationEntity.class));
                return list;
            }
            ContactRelationEntity[] relationships = mapper.readValue(json, ContactRelationEntity[].class);
            int size = relationships.length;
            for (int i = 0; i < size; ++i)
                list.add(relationships[i]);
            return list;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ContactEntity> getContactList() // Add whichever parameters needed for fitering
    {
        List<ContactEntity> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<ContactRelationEntity> relationships;
        int currentUserId = this._currentUser.getUserId();

        //GET http://localhost/api.php/categories?columns=categories.name
        String json = FixJsonOn("https://www.luminousapps.fr/mob5/api.php/user?columns=user.UserId,user.FirstName,user.LastName,user.Number", "user");
        if (json == null || json.isEmpty()) // si aucune ligne retournee depuis la base de donnee = pas d'utilisateur avec ce username, donc on sort
            return list;

        try {
            ContactEntity[] contacts = mapper.readValue(json, ContactEntity[].class);
            int contactLen = contacts.length;
            relationships = getRelationships();
            int relationLen = relationships.size();

            for (int i = 0; i < contactLen; ++i) {
                int j = 0;
                while (j < relationLen) {
                    ContactRelationEntity tmp = relationships.get(j);
                    int contactId = contacts[i].getId();
                    if ((tmp.getUserId() == contactId || tmp.getRelationshipUserId() == contactId)
                            && currentUserId != contactId) {
                            list.add(contacts[i]);
                    }
                    j++;
                }
            }
            return list;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ContactEntity> getContactListNotAddYet() // Add whichever parameters needed for fitering
    {
        List<ContactEntity> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<ContactRelationEntity> relationships;
        int currentUserId = this._currentUser.getUserId();

        //GET http://localhost/api.php/categories?columns=categories.name
        String json = FixJsonOn("https://www.luminousapps.fr/mob5/api.php/user?columns=user.UserId,user.FirstName,user.LastName,user.Number", "user");
        if (json == null || json.isEmpty()) // si aucune ligne retournee depuis la base de donnee = pas d'utilisateur avec ce username, donc on sort
            return list;

        try {
            ContactEntity[] contacts = mapper.readValue(json, ContactEntity[].class);
            int contactLen = contacts.length;
            relationships = getRelationships();
            int relationLen = relationships.size();

            for (int i = 0; i < contactLen; ++i) {
                int j = 0;
                while (j < relationLen) {
                    ContactRelationEntity tmp = relationships.get(j);
                    int contactId = contacts[i].getId();
                    if ((tmp.getUserId() != contactId && tmp.getRelationshipUserId() != contactId)
                            && currentUserId != contactId) {
                        list.add(contacts[i]);
                    }
                    j++;
                }
            }
            return list;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EventEntity> getEventList() // Add whichever parameters needed for fitering
    {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        mapper.setDateFormat(df);


        List<EventEntity> list = new ArrayList<>();
        String json = FixJsonOn("https://www.luminousapps.fr/mob5/api.php/event", "event");
        if (json == null || json.isEmpty())
            return list;
        try {
            if (json.charAt(0) != '[')
            {
                list.add(mapper.readValue(json, EventEntity.class));
                return list;
            }
            EventEntity[] events = mapper.readValue(json, EventEntity[].class);
            int size = events.length;
            for (int i = 0; i < size; ++i)
                list.add(events[i]);
            return list;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean CreateEvent() {
        //return true if login is correct, return false if wrong (based on sql request
        return true;
    }

    public boolean AddContact() {
        //return true if login is correct, return false if wrong (based on sql request
        return true;
    }
}
