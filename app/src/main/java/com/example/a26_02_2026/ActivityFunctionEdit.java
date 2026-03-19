package com.example.a26_02_2026;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class ActivityFunctionEdit extends AppCompatActivity {

    AppDatabase dataBase;
    TaskDao taskDao;
    Integer[] iconsId = {R.drawable.desktop, R.drawable.build, R.drawable.school, R.drawable.work};
    DropdownAdapter dropdownAdapter;
    EditText nameInput;
    Spinner iconInput;
    ImageView arrowImg;
    DatePicker dateInput;
    Button editButton;
    int iconFromIntent, idFromIntent;
    String nameFromIntent, dueDateFromIntent;
    String[] parts = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_function_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();

        idFromIntent = extras.getInt("id");
        iconFromIntent = extras.getInt("icon");
        nameFromIntent = extras.getString("name");
        dueDateFromIntent = extras.getString("dueDate");

        onInitialize();

        editButton.setOnClickListener(v -> {
            Integer selectedIcon = (Integer) iconInput.getSelectedItem();

            int day   = dateInput.getDayOfMonth();
            int month = dateInput.getMonth() + 1;
            int year  = dateInput.getYear();

            String formatedDate = String.format("%02d/%02d/%04d", month, day, year);

            taskDao.updateTask(idFromIntent, nameInput.getText().toString(), selectedIcon, formatedDate);

            startActivity(new Intent(ActivityFunctionEdit.this, MainActivity.class));
        });
    }

    public void onInitialize() {
        dataBase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tasks").allowMainThreadQueries().build();
        taskDao = dataBase.getDao();

        parts = dueDateFromIntent.split("/");

        nameInput = findViewById(R.id.nameInput);
        dateInput = findViewById(R.id.dateInput);
        iconInput = findViewById(R.id.iconInput);
        editButton = findViewById(R.id.editButton);

        nameInput.setText(nameFromIntent);

        int m = Integer.parseInt(parts[0]) - 1;
        int d   = Integer.parseInt(parts[1]);
        int y  = Integer.parseInt(parts[2]);

        dateInput.updateDate(y, m, d);

        arrowImg = findViewById(R.id.arrowImg);
        arrowImg.setColorFilter(Color.parseColor("#0F2854"));

        dropdownAdapter = new DropdownAdapter(iconsId,this);
        iconInput.setAdapter(dropdownAdapter);

        for (int i = 0; i < iconsId.length; i++) {
            if (Integer.compare(iconsId[i], iconFromIntent) == 0) {
                iconInput.setSelection(i);
            }
        }

        dateInput.setMinDate(System.currentTimeMillis());
    }
}