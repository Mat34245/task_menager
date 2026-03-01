package com.example.a26_02_2026;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Adapter adapter;
    ListView listView;
    List<User> users;
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


        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
                .allowMainThreadQueries().build();

        userDao = db.getDao();

        users = userDao.getUsers();

        adapter = new Adapter(users, MainActivity.this);
        listView.setAdapter(adapter);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityFunctionAdd.class)));

//        btn.setOnClickListener(v -> {
//            userDao.insertUser(new User("folder/", "Kamil", true, "2026"));
//            System.out.println(users);
//        });
    }
}

