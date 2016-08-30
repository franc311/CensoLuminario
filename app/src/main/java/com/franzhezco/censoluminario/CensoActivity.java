package com.franzhezco.censoluminario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CensoActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 5;

    private static final int DB_VERSION = 1;
    private final int MENU_NUEVO_CENSO = 1;
    private final int MENU_MODIFICAR_CENSO = 2;
    private final int MENU_ELIMINAR_CENSO = 3;
    private final int GROUP_DEFAULT = 1;
    private final int ID_TEXT_ELIMINAR = 1;
    private final int ID_TEXT_MODIFICAR = 2;

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private ListView listView1;

    private ArrayList<Censo> censoList = new ArrayList<Censo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_censo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView1.setAdapter(adapter);
        updateCensoList();
        checkPuntero();

        registerForContextMenu((View) findViewById(R.id.listView1));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCensoNuevoActivity();
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openPuntoListActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(GROUP_DEFAULT, MENU_NUEVO_CENSO, 0, "Nuevo Censo");
        menu.add(GROUP_DEFAULT, MENU_MODIFICAR_CENSO, 0, "Modificar Censo");
        menu.add(GROUP_DEFAULT, MENU_ELIMINAR_CENSO, 0, "Eliminar Censo");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_NUEVO_CENSO:
                openCensoNuevoActivity();
                return true;
            case MENU_MODIFICAR_CENSO:
                return true;
            case MENU_ELIMINAR_CENSO:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu cMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(cMenu, v, menuInfo);
        if (v.getId() == R.id.listView1) {
            cMenu.add(0, ID_TEXT_ELIMINAR, 0, "Eliminar");
            cMenu.add(0, ID_TEXT_MODIFICAR, 0, "Modificar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case ID_TEXT_ELIMINAR:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int index = info.position;
                Censo c = censoList.get(index);
                deleteCenso(c);
                return true;
            case ID_TEXT_MODIFICAR:
                Toast.makeText(this, "modificar", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void openCensoNuevoActivity() {
        Intent censoNuevoActivity = new Intent(this, CensoNuevoActivty.class);
        startActivityForResult(censoNuevoActivity, REQUEST_CODE);
    }

    private void addCenso(String censoName){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, DB_VERSION);
        Censo censo = new Censo(censoName);
        dbHandler.addCenso(censo);
        dbHandler.close();
        updateCensoList();
        Toast.makeText(this, censoName + " agregado", Toast.LENGTH_SHORT).show();
    }

    private void deleteCenso(Censo censo) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, DB_VERSION);
        if (dbHandler.deleteCenso(censo)) {
            Toast.makeText(this, censo.getName() + " eliminado", Toast.LENGTH_SHORT).show();
            updateCensoList();
        } else {
            Toast.makeText(this, censo.getName() + " no fue posible eliminar", Toast.LENGTH_SHORT).show();
        }
        dbHandler.close();
        updateCensoList();
    }
    //waiting to move to heading section

    private void  updateCensoList() {
        listItems.clear();
        censoList.clear();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, DB_VERSION);
        censoList = dbHandler.getListCenso();
        if(!censoList.isEmpty()) {
            Censo censo;
            for (int i = 0; i < censoList.size(); i++) {
                censo = censoList.get(i);
                listItems.add(censo.getID() + ", " + censo.getName());
            }
            adapter.notifyDataSetChanged();
        } else {
            listItems.clear();
            adapter.notifyDataSetChanged();
        }
        dbHandler.close();
    }

    public void checkPuntero() {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, DB_VERSION);
        if (!dbHandler.isPuntero()) {
            dbHandler.addPuntero();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == REQUEST_CODE) && (resultCode == RESULT_OK)) {
            addCenso(data.getExtras().getString("returnCensoName"));
        } else {
            Toast.makeText(this, "Nuevo Censo Cancelado...", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void openPuntoListActivity (int position) {
        Censo censo = censoList.get(position);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, DB_VERSION);
        dbHandler.updatePuntero(censo.getID());
        dbHandler.close();
        Intent i = new Intent(this, PuntoListActivity.class);
        startActivity(i);
    }
}
