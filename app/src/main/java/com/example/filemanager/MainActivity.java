package com.example.filemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filemanager.adapters.ListFileAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.attribute.FileOwnerAttributeView;

public class MainActivity extends AppCompatActivity {

    private ListView listViewFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewFiles = findViewById(R.id.listViewFiles);
        registerForContextMenu(listViewFiles);
        loadData();
    }

    private void loadData()
    {
        File dir = getFilesDir();
        ListFileAdapter listFileAdapter = new ListFileAdapter(getApplicationContext() , dir.listFiles());
        listViewFiles.setAdapter(listFileAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.file_menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.createFile)
        {
            openCreateFileDialog();

        }

        return true;
    }

    private void openCreateFileDialog()    {
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.create_file_dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getText(R.string.cancel));
       builder.setView(view);
        builder.setCancelable(false);
        final Dialog dialog = builder.show();
        final EditText editTextFileName = view.findViewById(R.id.editTextFileName);
        final EditText editTextContent = view.findViewById(R.id.editTextContent);
        Button buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        Button buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFile(editTextFileName.getText().toString() , editTextContent.getText().toString());
                dialog.dismiss();
            }
        });

    }

    private void saveFile(String fileName , String content) {
        try {
           // EditText editTextFileName = view.findViewById(R.id.editTextFileName);
           // EditText editTextContent = view.findViewById(R.id.editTextContent);

            File file = new File(getFilesDir() + File.separator + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            loadData();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.listViewFiles)
        {
            menu.add(0 , 0 , 0 , getText(R.string.delete));
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == 0)
        {
            deleteFile(item);

        }
        return true;
    }
    private void deleteFile( MenuItem item)
    {
        try {
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            View view = adapterContextMenuInfo.targetView;
            TextView textViewFileName = view.findViewById(R.id.textViewFileName);
            String fileName = textViewFileName.getText().toString();
           for(File file : getFilesDir().listFiles())
           {
               if(file.getName().equalsIgnoreCase(fileName))
               {
                   file.delete();
                   break;
               }
           }
            loadData();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}