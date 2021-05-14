package com.example.room_db_basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private UserDao userDao;
    private AppDatabase db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(this, AppDatabase.class, "user_DB").allowMainThreadQueries().build();
        userDao = db.userDao();
        listView = findViewById(R.id.lvThing);
        getDBConnection();
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnDel = findViewById(R.id.btnDelete);
        Button btnCan = findViewById(R.id.btnCan);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(v);
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(v);
            }
        });
        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelUser(v);
            }
        });

    }
    public void getDBConnection() {
        List<User> users = userDao.getAll();
        List<String> ada= userDao.getAllName();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ada);
        listView.setAdapter(adapter);
    }
    public void addUser(View view) {
        User user = new User();
        EditText name = findViewById(R.id.edtName);
        user.setName(String.valueOf(name.getText()));
        user.setId(new Random().nextInt(1000));
        userDao.insert(user);
        getDBConnection();
    }
    public void deleteUser(View view) {
        EditText name = findViewById(R.id.edtName);
        userDao.delete(String.valueOf(name.getText()));
        getDBConnection();
    }

    public void cancelUser(View view) {
        EditText name = (EditText) findViewById(R.id.edtName);
        name.getText().clear();
    }
}