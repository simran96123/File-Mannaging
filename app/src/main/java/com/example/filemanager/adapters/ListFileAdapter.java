package com.example.filemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.filemanager.R;

import java.io.File;
import java.lang.reflect.Array;

public class ListFileAdapter extends ArrayAdapter<File> {

    private Context context;
    private File[] files;
    public  ListFileAdapter(Context context , File[] files)
    {
        super(context , R.layout.list_file_layout ,  files);
        this.context = context;
        this.files = files;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_file_layout , null);
        File file = files[position];
        TextView textViewFileName = view.findViewById(R.id.textViewFileName);
        textViewFileName.setText(file.getName());
        TextView textViewFileSize = view.findViewById(R.id.textViewFileSize);
        textViewFileSize.setText(String.valueOf(file.length()) + "byte(s)");

       return view;
    }
}
