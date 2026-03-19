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

    Button addButton, searchButton;
    EditText searchInput;
    public Adapter tasksAdapter, finishedTasksAdapter;
    ListView tasksList, finishedTasksList;
    List<Task> tasks, finishedTasks;
    TaskDao taskDao;
    AppDatabase dataBase;
    String searchText;

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

        onInitialize();

        searchButton.setOnClickListener(v -> {
            searchText = '%' + searchInput.getText().toString() + '%';

            finishedTasks = taskDao.searchDoneTasks(searchText);
            tasks = taskDao.searchTasks(searchText);

            updateAdapters(tasks, finishedTasks, tasksAdapter, finishedTasksAdapter);
        });

        addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivityFunctionAdd.class)));

    }

    @Override
    public void onRowChanged() {
        finishedTasks = taskDao.getDoneTasks();
        tasks = taskDao.getTasks();

        updateAdapters(tasks, finishedTasks, tasksAdapter, finishedTasksAdapter);
    }

    public void onInitialize() {
        tasksList = findViewById(R.id.tasksList);
        finishedTasksList = findViewById(R.id.finishedTasksList);

        dataBase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tasks").allowMainThreadQueries().build();
        taskDao = dataBase.getDao();

        finishedTasks = taskDao.getDoneTasks();
        tasks = taskDao.getTasks();

        addButton = findViewById(R.id.addButton);
        searchButton = findViewById(R.id.searchButton);

        searchInput = findViewById(R.id.searchInput);

        updateAdapters(tasks, finishedTasks, tasksAdapter, finishedTasksAdapter);
    }

    public void updateAdapters(List<Task> tasks, List<Task> finishedTasks, Adapter firstAdapter, Adapter secondAdapter) {
        firstAdapter = new Adapter(tasks, MainActivity.this, this);
        secondAdapter = new Adapter(finishedTasks,MainActivity.this,this);

        tasksList.setAdapter(firstAdapter);
        finishedTasksList.setAdapter(secondAdapter);
    }
}

