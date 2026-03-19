package com.example.a26_02_2026;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;

public class ActivityFunctionAdd extends AppCompatActivity {

    AppDatabase dataBase;
    TaskDao taskDao;
    Integer[] iconsId = {R.drawable.desktop, R.drawable.build, R.drawable.school, R.drawable.work};
    DropdownAdapter dropdownAdapter;
    EditText nameInput;
    Spinner iconInput;
    ImageView arrowImg;
    DatePicker dateInput;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_function_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        onInitialize();

        addButton.setOnClickListener(v -> {
            Integer selectedIcon = (Integer) iconInput.getSelectedItem();

            int day   = dateInput.getDayOfMonth();
            int month = dateInput.getMonth() + 1;
            int year  = dateInput.getYear();

            String formatedDate = String.format("%02d/%02d/%04d", month, day, year);

            Task task = new Task(selectedIcon, nameInput.getText().toString(), false, formatedDate);
            taskDao.insertTask(task);

            startActivity(new Intent(ActivityFunctionAdd.this, MainActivity.class));
        });
    }

    public void onInitialize() {
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tasks").allowMainThreadQueries().build();
        taskDao = dataBase.getDao();

        nameInput = findViewById(R.id.nameInput);
        dateInput = findViewById(R.id.dateInput);
        iconInput = findViewById(R.id.iconInput);
        addButton = findViewById(R.id.addButton);

        arrowImg = findViewById(R.id.arrowImg);
        arrowImg.setColorFilter(Color.parseColor("#0F2854"));

        dropdownAdapter = new DropdownAdapter(iconsId,this);
        iconInput.setAdapter(dropdownAdapter);

        dateInput.setMinDate(System.currentTimeMillis());
    }
}