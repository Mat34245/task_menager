package com.example.a26_02_2026;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.util.List;
import java.util.Objects;

public class Adapter extends ArrayAdapter<Task> implements View.OnClickListener{

    private onRowChangedListener listener;
    private  List<Task> tasks;

    public Adapter(List<Task> tasks, Context context, onRowChangedListener listener) {
        super(context, 0, tasks);
        this.tasks = tasks;
        this.listener = listener;
    }

    @Override
    public void onClick(View v) { }
    public interface onRowChangedListener{ void onRowChanged(); }

    AppDatabase dataBase;
    TaskDao taskDao;
    Task current;
    TextView dateView, nameView, idView;
    ImageView iconView;
    CheckBox checkbox;
    Button deleteButton, editButton;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        onInitialize(position, currentItemView);

        if (current.isDone) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }

        checkbox.setOnClickListener(V -> {
            if (checkbox.isChecked()) {
                taskDao.updateIsDone(true, current.id);
                updateTasks();
            } else {
                taskDao.updateIsDone(false, current.id);
                updateTasks();
            }
        });

        deleteButton.setOnClickListener(v -> {
            taskDao.deleteTask(current.id);
            updateTasks();
        });

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(Adapter.this.getContext(), ActivityFunctionEdit.class);

            current = tasks.get(position);

            intent.putExtra("id", current.id);
            intent.putExtra("icon", current.icon);
            intent.putExtra("name", current.name);
            intent.putExtra("dueDate", current.dueDate);

            getContext().startActivity(intent);
        });

        return currentItemView;
    }

    public void onInitialize(int position, View currentItemView) {
        dataBase = Room.databaseBuilder(getContext(), AppDatabase.class, "tasks").allowMainThreadQueries().build();
        taskDao = dataBase.getDao();

        current = tasks.get(position);

        idView = currentItemView.findViewById(R.id.idView);
        iconView = currentItemView.findViewById(R.id.iconView);
        nameView = currentItemView.findViewById(R.id.nameView);
        dateView = currentItemView.findViewById(R.id.dateView);
        deleteButton = currentItemView.findViewById(R.id.deleteButton);
        editButton = currentItemView.findViewById(R.id.editButton);
        checkbox = currentItemView.findViewById(R.id.checkbox);

        iconView.setImageResource(current.icon);
        iconView.setColorFilter(Color.parseColor("#0F2854"));

        idView.setText(current.id + "");
        dateView.setText(current.dueDate);
        nameView.setText(current.name);
    }

    public void updateTasks() {
        listener.onRowChanged();
        tasks.remove(current);
        notifyDataSetChanged();
        listener.onRowChanged();
    }
}
