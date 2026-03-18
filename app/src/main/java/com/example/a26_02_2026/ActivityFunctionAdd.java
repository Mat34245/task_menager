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

    UserDao userDao;
    Button btn;
    EditText name;
    AutoCompleteTextView autoCompleteTextView;
    Integer[] iconsId = {R.drawable.desktop, R.drawable.build, R.drawable.school, R.drawable.work};
    DropdownAdapter dropdownAdapter;
    Spinner icon;
    DatePicker date;
    ImageView arrow;

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

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
                .allowMainThreadQueries().build();

        userDao = db.getDao();

        date = findViewById(R.id.date);
        icon = findViewById(R.id.icon);

        dropdownAdapter = new DropdownAdapter(iconsId,this);

        icon.setAdapter(dropdownAdapter);

        arrow = findViewById(R.id.arrow);
        arrow.setColorFilter(Color.parseColor("#0F2854"));
        btn = findViewById(R.id.btn2);

        btn.setOnClickListener(v -> {
            Integer selectedIcon = (Integer) icon.getSelectedItem();
            name = findViewById(R.id.name);
            date = findViewById(R.id.date);

            int day   = date.getDayOfMonth();
            int month = date.getMonth() + 1;
            int year  = date.getYear();

            String dateForm = String.format("%02d/%02d/%04d", month, day, year);

            User user = new User(selectedIcon, name.getText().toString(), false, dateForm);

            userDao.insertUser(user);

            startActivity(new Intent(ActivityFunctionAdd.this, MainActivity.class));

        });

        date.setMinDate(System.currentTimeMillis());

    }
}