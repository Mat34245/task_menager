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

public class ActivityFunctionEdit extends AppCompatActivity {

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
        setContentView(R.layout.activity_function_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);

        btn = findViewById(R.id.btn2);

        Bundle extras = getIntent().getExtras();

        int id = extras.getInt("id");
        String iconFromIntent = extras.getString("icon");
        String nameFromIntent = extras.getString("name");
        String dueDateFromIntent = extras.getString("dueDate");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users")
                .allowMainThreadQueries().build();

        userDao = db.getDao();

        name.setText(nameFromIntent);
        date.setText(dueDateFromIntent);

        icon.getEditText().setText(iconFromIntent);

        autoCompleteTextView = findViewById(R.id.iconAutoComplete);
        dropdownAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, icons);
        autoCompleteTextView.setAdapter(dropdownAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(ActivityFunctionEdit.this, "Icon " + item, Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(v -> {
            icon = findViewById(R.id.icon);
            name = findViewById(R.id.name);
            date = findViewById(R.id.date);

            userDao.update(id, name.getText().toString(), icon.getEditText().getText().toString(), date.getText().toString());

            startActivity(new Intent(ActivityFunctionEdit.this, MainActivity.class));
        });

    }
}