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

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

public class Adapter extends ArrayAdapter<Task> implements View.OnClickListener{

    private onRowChangedListener listener;
    private  List<Task> tasks;
    AppDatabase dataBase;
    TaskDao taskDao;

    public Adapter(List<Task> tasks, Context context, onRowChangedListener listener) {
        super(context, 0, tasks);
        this.tasks = tasks;
        this.listener = listener;
        dataBase = Room.databaseBuilder(getContext(), AppDatabase.class, "tasks").allowMainThreadQueries().build();
        taskDao = dataBase.getDao();
    }

    @Override
    public void onClick(View v) { System.out.println("Kliknięto na element"); }
    public interface onRowChangedListener{
        void onRowChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Task current = tasks.get(position);

        TextView idView = currentItemView.findViewById(R.id.idView);
        ImageView iconView = currentItemView.findViewById(R.id.iconView);
        TextView nameView = currentItemView.findViewById(R.id.nameView);
        TextView dateView = currentItemView.findViewById(R.id.dateView);
        Button deleteButton = currentItemView.findViewById(R.id.deleteButton);
        Button editButton = currentItemView.findViewById(R.id.editButton);
        CheckBox checkbox = currentItemView.findViewById(R.id.checkbox);

        iconView.setImageResource(current.icon);
        iconView.setColorFilter(Color.parseColor("#0F2854"));

        idView.setText(current.id + "");
        dateView.setText(current.dueDate);
        nameView.setText(current.name);

        if (current.isDone) {
            checkbox.setChecked(true);
        }

        checkbox.setOnClickListener(V -> {
            if (checkbox.isChecked()) {
                taskDao.updateIsDone(true, current.id);
                updateTasks(current);
            } else {
                taskDao.updateIsDone(false, current.id);
                updateTasks(current);
            }
        });

        deleteButton.setOnClickListener(v -> {
            taskDao.deleteTask(current.id);
            updateTasks(current);
        });

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(Adapter.this.getContext(), ActivityFunctionEdit.class);

            intent.putExtra("id", current.id);
            intent.putExtra("icon", current.icon);
            intent.putExtra("name", current.name);
            intent.putExtra("dueDate", current.dueDate);

            getContext().startActivity(intent);
        });

        return currentItemView;
    }

    public void updateTasks(Task current) {
        listener.onRowChanged();
        tasks.remove(current);
        notifyDataSetChanged();
        listener.onRowChanged();
    }
}
