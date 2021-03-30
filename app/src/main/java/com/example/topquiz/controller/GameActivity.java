package com.example.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;

import java.lang.reflect.Array;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView question;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private Button choice4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    //public static final String BUNDLE_EXTRA_LEVEL = "BUNDLE_EXTRA_LEVEL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        mScore = 0;
        mNumberOfQuestions = mQuestionBank.sizeOfmQuestionBank();


        //Wire widgets
        question = findViewById(R.id.game_question_text);
        choice1 = findViewById(R.id.game_answer1);
        choice2 = findViewById(R.id.game_answer2);
        choice3 = findViewById(R.id.game_answer3);
        choice4 = findViewById(R.id.game_answer4);

        //Use the tag property to "name" the buttons
        choice1.setTag(0);
        choice2.setTag(1);
        choice3.setTag(2);
        choice4.setTag(3);

        choice1.setOnClickListener(this);
        choice2.setOnClickListener(this);
        choice3.setOnClickListener(this);
        choice4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Who is the creator of Android?", Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"), 0);
        Question question2 = new Question("When did the first man land on the moon", Arrays.asList("1958", "1962", "1967", "1969"), 3);
        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);
        Question question4 = new Question("Who is this century human compiler?",
                Arrays.asList("Steve Jobs","Mark Zuckerberg","Antoine Spicher","Halimatou BAH"),
                3);
        Question question5= new Question("Where is France capital?",
                Arrays.asList("Monaco", "Paris", "Madrid", "Marseille"),
                1);
        return new QuestionBank(Arrays.asList(question1, question2, question3, question4, question5));
    }

    //Mise à jour des champs de l'interface graphique
    private void displayQuestion(final Question question) {
        this.question.setText(question.getQuestion());
        choice1.setText(question.getChoiceList().get(0));
        choice2.setText(question.getChoiceList().get(1));
        choice3.setText(question.getChoiceList().get(2));
        choice4.setText(question.getChoiceList().get(3));
    }

    @Override
    public void onClick(View view) {
        int responseIndex = (int) view.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            //Good Answer
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            //this.displayQuestion(mCurrentQuestion);
            mScore++;
        } else {
            //Wrong answer
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
        if (--mNumberOfQuestions == 0) {
            //End the game
            endGame();
        } else {
            mCurrentQuestion = mQuestionBank.getQuestion();
            this.displayQuestion(mCurrentQuestion);
        }
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done");
        builder.setMessage("Your score is " + mScore);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //End the activity, return to the last activity
                Intent intent = new Intent();
                intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);

                //intent.putExtra(BUNDLE_EXTRA_LEVEL,mNumberOfQuestions);

                setResult(RESULT_OK, intent);

                finish();
            }
        });
        builder.create()
                .show(); // same to builder.create(); builder.show();
    }
}