package com.example.krcho.clozetmanager.recycle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.krcho.clozetmanager.R;

import java.util.ArrayList;

public class RecyclerExampleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager recyclerLayoutManager;
    ExampleRecyclerAdapter adapter;
    ArrayList<Request> list = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_example);

        //test data
        for (int i = 0; i < 10; i++) {
            Request temp = new Request();
            temp.setRoom(i);
            temp.setName("test");

            list.add(temp);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_example);
        recyclerLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerLayoutManager);
        adapter = new ExampleRecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener agreeClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //수락버튼 누른 경우
            Toast.makeText(context, "수락!!", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //거절버튼 누른 경우
            Toast.makeText(context, "거절!!", Toast.LENGTH_SHORT).show();
        }
    };
}
