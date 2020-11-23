package com.example.projectgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectgame.db.AppDatabase;
import com.example.projectgame.model.User;
import com.example.projectgame.util.AppExecutors;

public class AddUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        Button scoreButton = findViewById(R.id.btnViewAll);
        final EditText nameEditText = findViewById(R.id.add_name);
        final EditText bbeEditText = findViewById(R.id.bbe_edit_text);
        final EditText mfgEditText = findViewById(R.id.mfg_edit_text);
        final EditText expEditText = findViewById(R.id.exp_edit_text);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEditText.getText().toString().length()==0) {
                    Toast.makeText(AddUserActivity.this,"Name is null",Toast.LENGTH_SHORT).show();
                }
                else if(bbeEditText.getText().toString().length()==0) {
                    Toast.makeText(AddUserActivity.this,"BBE is null",Toast.LENGTH_SHORT).show();
                }
                else if(mfgEditText.getText().toString().length()==0) {
                    Toast.makeText(AddUserActivity.this,"MFG is null",Toast.LENGTH_SHORT).show();
                }
                else if(expEditText.getText().toString().length()==0) {
                    Toast.makeText(AddUserActivity.this,"EXP is null",Toast.LENGTH_SHORT).show();
                }
                else {
                    String name = nameEditText.getText().toString();
                    String bbe = bbeEditText.getText().toString();
                    String mfg = mfgEditText.getText().toString();
                    String exp = expEditText.getText().toString();

                    final User user = new User(0, name,bbe,mfg,exp);

                    AppExecutors executors = new AppExecutors();
                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() { // worker thread
                            AppDatabase db = AppDatabase.getInstance(AddUserActivity.this);
                            db.userDao().addUser(user);
                            finish();
                        }
                    });

                }

            }
        });


    }
}


