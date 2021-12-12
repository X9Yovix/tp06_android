package com.yovix.tp06;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private CustomAdapter myAdapter;
    private List<Pojo> dataModalArrayList;

    private TextView textview_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.mRecyclerview);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        rv.setLayoutManager(llm);

        dataModalArrayList = new ArrayList<>();
/*
        String[] country_names = getResources().getStringArray(R.array.country_names);
        for (String pays : country_names) {
            dataModalArrayList.add(new Pojo(pays));
        }
 */

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
                dataModalArrayList.add(new Pojo(country));
            }
        }

        myAdapter = new CustomAdapter(dataModalArrayList, this);
        rv.setAdapter(myAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addElement = findViewById(R.id.addBtn);
        addElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("add element");
                showAddingDialog(MainActivity.this);
            }
        });

        textview_list = findViewById(R.id.textview_list);
        textview_list.setOnClickListener(onShowPopupMenu());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.asc: {
                //Collections.sort(dataModalArrayList, Pojo.listAtoZ);
                //for (Pojo x:dataModalArrayList){
                //    System.out.println(x.toString());
                //}
                //myAdapter.notifyDataSetChanged();
                myAdapter.sortByName(dataModalArrayList);
                Toast.makeText(this.getApplicationContext(), "asc", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.desc: {
                myAdapter.reverseByName(dataModalArrayList);
                Toast.makeText(this.getApplicationContext(), "desc", Toast.LENGTH_SHORT).show();
                System.out.println("desc");
                return true;
            }
            default:
                System.out.println("none");
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddingDialog(Context context) {
        final EditText input = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Add new country:");
        builder.setView(input);
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Valid",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (input.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "field is empty", Toast.LENGTH_SHORT).show();
                        } else {
                            myAdapter.addItem(input.getText().toString());
                        }
                    }
                });
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onContextItemSelected( MenuItem item) {
        switch(item.getItemId()){
            case(R.id.delete):{
                Toast.makeText(getApplicationContext(), "item : "+myAdapter.pays.get(item.getGroupId()).getName()+", has been deleted", Toast.LENGTH_SHORT).show();
                myAdapter.removeItem(item.getGroupId());
                return true;
            }
            case(R.id.wiki):{
                myAdapter.openBrowser(myAdapter.pays.get(item.getGroupId()).getName());
                return true;
            }
            default:
                System.out.println("none");
        }
        return super.onContextItemSelected(item);
    }
    private PopupMenu.OnMenuItemClickListener PopupListener(){
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case(R.id.show_hide):{
                        if(rv.getVisibility() == View.VISIBLE){
                            rv.setVisibility(View.INVISIBLE);
                        }else{
                            rv.setVisibility(View.VISIBLE);
                        }
                        return true;
                    }
                    case(R.id.change_title_bg):{
                        textview_list.setBackgroundColor(new Random().nextInt());
                        return true;
                    }
                    default:
                        System.out.println("none");
                }
                return false;
            }
        };
    }
    private View.OnClickListener onShowPopupMenu(){

        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this,v);
                popupMenu.setOnMenuItemClickListener(PopupListener());
                popupMenu.inflate(R.menu.pop_up);
                popupMenu.show();
            }
        };
    }
}