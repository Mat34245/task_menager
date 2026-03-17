package com.example.a26_02_2026;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.onRowChangedListener {

    Button btn;
    Button btn2;
    Adapter adapter;
    EditText wyszukaj;

    public Adapter adapter2;
    Adapter adapter3;
    Adapter adapter4;
    ListView listView;

    ListView listView2;
    List<User> users;

    List<User> DoneTasks;

    List<User> searchDoneTasks;

    List<User> searchTasks;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listView);
        listView2 = findViewById(R.id.listView2);


        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
                .allowMainThreadQueries().build();


        userDao = db.getDao();

        users = userDao.getUsers();
        DoneTasks= userDao.getDoneTasks();

        adapter = new Adapter(users, MainActivity.this, this);
        adapter2 = new Adapter(DoneTasks,MainActivity.this,this);
        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);


        btn = findViewById(R.id.btn);

        btn2 = findViewById(R.id.btn2);

        btn2.setOnClickListener(v -> {
            wyszukaj = findViewById(R.id.wyszukiwanie);
            String wyszukiwanie = '%'+wyszukaj.getText().toString()+'%';

            searchDoneTasks = userDao.searchDoneTasks(wyszukiwanie);
            searchTasks = userDao.searchTasks(wyszukiwanie);

            adapter3 = new Adapter(searchTasks, MainActivity.this,this);
            adapter4 = new Adapter(searchDoneTasks, MainActivity.this,this);

            listView.setAdapter(adapter3);
            listView2.setAdapter(adapter4);

            System.out.println(wyszukiwanie);

        });

        btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityFunctionAdd.class)));

//        btn.setOnClickListener(v -> {
//            userDao.insertUser(new User("folder/", "Kamil", true, "2026"));
//            System.out.println(users);
//        });

    }

    @Override
    public void onRowChanged() {
          DoneTasks = userDao.getDoneTasks();
          users = userDao.getUsers();
          adapter = new Adapter(users, MainActivity.this, this);
          adapter2 = new Adapter(DoneTasks,MainActivity.this,this);
          listView.setAdapter(adapter);
          listView2.setAdapter(adapter2);
    }
}

