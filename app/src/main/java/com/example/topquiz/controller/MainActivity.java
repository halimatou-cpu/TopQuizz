 package com.example.topquiz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.model.User;

 public class MainActivity extends AppCompatActivity {

     private TextView mGreetingText;
     private EditText mNameInput;
     private Button mPlaybtn;
     private User mUser; // m pour model
     public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
     private SharedPreferences mPreferences ;

     public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data); // generation automatique
         if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
             int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0); //0 par defaut

             mPreferences.edit().putInt("score",score).apply();
         }
     }

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();

        mPreferences = getPreferences(MODE_PRIVATE); //mode d'accès, seule notre appli y a acces

        mGreetingText = findViewById(R.id.textView);
        mNameInput = findViewById(R.id.editTextPersonName2);
        mPlaybtn = findViewById(R.id.playbutton1);

        mPlaybtn.setEnabled(false);

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //charSequence content the inputtext, so if it contains at least one char, button on else off
                mPlaybtn.setEnabled(charSequence.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPlaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = mNameInput.getText().toString();
                mUser.setFirstname(firstname);
//je souhaite éditer les valeurs de ses pref,je veux mettre une val string,la cle "firstname" permettra
                //de récup la val plus tard, on lui associe le prenom de l'user recup par getFirstname
                // apply : ok tu peux prendre en compte les modifs
                //mPreferences.edit().putString("firstname",mUser.getFirstname()).apply();
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME    ,mUser.getFirstname()).apply();

                //User clicked the button
                Intent gameActivityIntent = new Intent(MainActivity.this,GameActivity.class);
                //startActivity(gameActivityIntent);
                startActivityForResult(gameActivityIntent,GAME_ACTIVITY_REQUEST_CODE);
            }
        });


    }
}