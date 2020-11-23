package com.example.projectgame;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectgame.adapter.UserAdapter;
import com.example.projectgame.db.AppDatabase;
import com.example.projectgame.model.User;
import com.example.projectgame.util.AppExecutors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private RecyclerView mRecycleView;


    @Override
    protected void onResume() {
        super.onResume();

        final AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                final User[] users = db.userDao().getAllUsers();

                executors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        UserAdapter adapter = new UserAdapter(MainActivity.this, users);
                        mRecycleView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText nameEditText = findViewById(R.id.add_name);
        final EditText bbeEditText = findViewById(R.id.bbe_edit_text);
        final EditText mfgEditText = findViewById(R.id.mfg_edit_text);
        final EditText expEditText = findViewById(R.id.exp_edit_text);

        mRecycleView = findViewById(R.id.user_recycler_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Button addButton = findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(i);
            }
        });

        Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String bbe = bbeEditText.getText().toString();
                String mfg = mfgEditText.getText().toString();
                String exp = expEditText.getText().toString();
                final User user = new User(0, name,bbe,mfg,exp);

                    AppExecutors executors = new AppExecutors();
                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase db = AppDatabase.getInstance(MainActivity.this);
                            db.userDao().deleteUser(user);
                            finish();
                        }
                    });
            }
        });
    }
}

