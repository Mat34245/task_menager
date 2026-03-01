package com.example.a26_02_2026;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
    EditText name, date;
    AutoCompleteTextView autoCompleteTextView;
    String[] icons = {"desktop", "build", "school", "work"};
    ArrayAdapter<String> dropdownAdapter;
    TextInputLayout icon;

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

        autoCompleteTextView = findViewById(R.id.iconAutoComplete);
        dropdownAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, icons);
        autoCompleteTextView.setAdapter(dropdownAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(ActivityFunctionAdd.this, "Icon " + item, Toast.LENGTH_SHORT).show();
            }
        });


        btn = findViewById(R.id.btn2);

        btn.setOnClickListener(v -> {
            icon = findViewById(R.id.icon);
            name = findViewById(R.id.name);
            date = findViewById(R.id.date);

            User user = new User(icon.getEditText().getText().toString(), name.getText().toString(), false, date.getText().toString());

            userDao.insertUser(user);

            startActivity(new Intent(ActivityFunctionAdd.this, MainActivity.class));
        });

    }
}