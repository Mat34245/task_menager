package com.example.a26_02_2026;

import android.content.Context;
import android.content.Intent;
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

public class Adapter extends ArrayAdapter<User> implements View.OnClickListener{
    private onRowChangedListener listener;
    private  List<User> users;
    public Adapter(List<User> users, Context context, onRowChangedListener listener) {
        super(context, 0, users);
        this.users = users;
        this.listener = listener;
    }
    AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "users")
            .allowMainThreadQueries().build();

    UserDao userDao;

    @Override
    public void onClick(View v) {
        System.out.println("Kliknięto na element");
    }

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
//        User current = getItem(position);
        User current = users.get(position);
        TextView id = currentItemView.findViewById(R.id.id);
        Button delbutton = currentItemView.findViewById(R.id.del);
        ImageView icon = currentItemView.findViewById(R.id.icon);
        LinearLayout listItem = currentItemView.findViewById(R.id.listItem);
        CheckBox checkbox = currentItemView.findViewById(R.id.checkbox);
        int[] icons = {R.drawable.desktop, R.drawable.build, R.drawable.school, R.drawable.work};
        String[] iconsName = {"desktop", "build", "school", "work"};
        userDao = db.getDao();

        checkbox.setOnClickListener(V -> {
            if (checkbox.isChecked()) {
                userDao.updateIsDone(true, current.id);
                System.out.println("yup");
                listener.onRowChanged();
                return;
            }
            userDao.updateIsDone(false, current.id);
            listener.onRowChanged();
            System.out.println("nope");
        });

        listItem.setOnClickListener(v -> {
            System.out.println("Kliknięto na");
            System.out.println(current.id);
            notifyDataSetChanged();
        });
        delbutton.setOnClickListener(v -> {
            System.out.println("Usun");
            System.out.println(current.id);
            userDao.deleteUserById(current.id);
            //ts usuwa obiekt z adaptera right?
            users.remove(current);
            //to refreshuje listview (i think)
            notifyDataSetChanged();
            //to updatuje activity chyba to jest useless
            listener.onRowChanged();
        });

        id.setText(current.id + "");

        for (int i = 0; i < icons.length; i++) {
            if (current.icon.equals(iconsName[i])) {
                icon.setImageResource(icons[i]);
                System.out.println(current.icon + iconsName[i]);
            }
        }

        TextView name = currentItemView.findViewById(R.id.name);
        name.setText(current.name);
        return currentItemView;
    }
}
