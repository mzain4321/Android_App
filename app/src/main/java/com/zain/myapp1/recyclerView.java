package com.zain.myapp1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zain.myapp1.Adapter.MyAdapter;
import com.zain.myapp1.models.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class recyclerView extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ItemModel> itemList;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        itemList.add(new ItemModel(R.drawable.ic_launcher_foreground, "Item 1"));
        itemList.add(new ItemModel(R.drawable.ic_launcher_foreground, "Item 2"));
        itemList.add(new ItemModel(R.drawable.ic_launcher_foreground, "Item 3"));
        itemList.add(new ItemModel(R.drawable.ic_launcher_foreground, "Item 4"));

        adapter = new MyAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
    }
}
