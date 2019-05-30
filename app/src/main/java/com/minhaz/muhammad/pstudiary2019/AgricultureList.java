package com.minhaz.muhammad.pstudiary2019;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgricultureList extends AppCompatActivity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

@Override
public boolean onClose() {
    listAdapter.fillerData("");
    expandAll();
    return false;
}

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.fillerData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.fillerData(newText);
        expandAll();
        return false;
    }

    class ChildShortClick implements ExpandableListView.OnChildClickListener
    {

        ChildShortClick(){}



        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            TextView name = v.findViewById(R.id.nameText);
            TextView occupation = v.findViewById(R.id.occupationText);
            TextView mobile = v.findViewById(R.id.mobile);
            TextView email = v.findViewById(R.id.eemail);
            new CustomDialog(AgricultureList.this, name.getText().toString(), occupation.getText().toString(), mobile.getText().toString(), email.getText().toString(), ((Parent) AgricultureList.this.parentList.get(groupPosition)).getName()).show();
            return false;
        }
    }



    private CustomAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Parent> parentList = new ArrayList<>();
    Cursor c = null;           //zihan
    DatabaseHelper myDbHelper;      //zihan
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agriculture_list);
        myDbHelper = new DatabaseHelper(getApplicationContext());       //zihan

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView search = findViewById(R.id.search);


        assert searchManager != null;
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setFocusable(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        displayList();

        expandAll();



    }



    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }

    //method to expand all groups
    private void displayList() {

        //display the list
        loadSomeData();

        //get reference to the ExpandableListView
        myList = (ExpandableListView) findViewById(R.id.expagrilist);
        //create the adapter by passing your ArrayList data
        listAdapter = new CustomAdapter(this, parentList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);
        myList.setOnChildClickListener(new ChildShortClick());

    }


    private void loadSomeData() {




      datafromDb("ag_botany","এগ্রিকালচারাল বোটানি");
      datafromDb("ag_chemistry","এগ্রিকালচারাল কেমিস্ট্রি");
      datafromDb("ag_engg","এগ্রিকালচারাল ইঞ্জিনিয়ারিং");
      datafromDb("ag_ext","এগ্রিকালচারাল এক্সটেনশন এন্ড রুরাল ডেভেলপমেন্ট");
      datafromDb("ag_forest","এগ্রোফরেস্ট্রি");
      datafromDb("ag_krisitotto","এগ্রোনমি");
      datafromDb("ag_animal","এ্যানিমেল সায়েন্স");
        datafromDb("ag_biotech","বায়োটেকনোলজি");
      datafromDb("ag_kit","এন্টোমলজি");
      datafromDb("ag_kouli","জেনেটিক্স এন্ড প্লান্ট ব্রিডিং");
      datafromDb("ag_hort","হর্টিকালচার");
      datafromDb("ag_patho","প্লান্ট প্যাথলজি");
      datafromDb("ag_soil","সয়েল সায়েন্স");
      datafromDb("ag_stat","স্ট্যাটিসটিক্স");





    }



//zihan
    public void datafromDb(String table_name,String department_name){
        ArrayList<Child> childList = new ArrayList<Child>();


        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        myDbHelper.openDataBase();


        c = myDbHelper.query(table_name, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Child child = new Child( c.getString(0),  c.getString(1), c.getString(2),  c.getString(3));
                childList.add(child);
            } while (c.moveToNext());
            Parent parent = new Parent(department_name, childList);
            parentList.add(parent);

        }
    }



}
























