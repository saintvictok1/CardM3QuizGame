package com.example.Kernopedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {


     TextView scorelabel, questionnumlabel, questionlabel;
     RadioGroup radioanswergroup = null;
     RadioButton radioAnsA, radioAnsB, radioAnsC, radioAnsD;
     CheckBox checkAnsA, checkAnsB, checkAnsC, checkAnsD;
     CardView linearradio, linearcheck;
     Button submitanswer;
     int rightanswercount = 0;
     int quizCount = 1;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    String[][] quizData = {
            // {"Number of correct answers","Question", "Choice 1", "Choice 2" "Choice 3" "Choice 4"}
            //single - first answer in array is correct
            //double - first two answers in array are correct
            //triple - first three answers in array are correct

            {"One", "When was the first computer invented?", "1943", "1989", "1949", "2001"},
            {"One", "What is the capital city of Spain?", "Madrid", "Barcelona", "Valencia", "Seville"},
            {"One", "Who discovered penicillin?", "Alexander Fleming", "Thomas Edison", "Will Smith", "Albert Einstein"},
            {"One", "What does “www” stand for in a website browser?", "World Wide Web", "Wild Wild West", "Wacky World Web", "Wacky Web Wiki"},
            {"One", "What geometric shape is generally used for stop signs?", "Octagon", "Pentagon", "Triangle", "Decagon"},
            {"One", "What is Cynophobia?", "Fear of dogs", "Fear of Cats", "Fear of Drums", "Fear of Signs"},
            {"Two", "Which are primary colors?", "Red", "Green", "Orange", "Purple"},
            {"Three", "Which are primary colors?", "Red", "Green", "Blue", "Purple"},
            {"Three", "What Residence Halls are located at Montclair State University?", "Blanton Hall", "Russ Hall", "Stone Hall", "Widget Hall"},

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        scorelabel = findViewById(R.id.scorelabel);
        questionlabel = findViewById(R.id.tv_questionlabel);
        scorelabel.append(String.valueOf(rightanswercount));
        questionnumlabel = findViewById(R.id.question_num_label);
        //group of variables for Linear Layout containing radio buttons for single answer questions
        linearradio = findViewById(R.id.linearRadio);

        radioanswergroup = findViewById(R.id.Radioanswergroup);
        radioAnsA = findViewById(R.id.radioOptA);
        radioAnsB = findViewById(R.id.radioOptB);
        radioAnsC = findViewById(R.id.radioOptC);
        radioAnsD = findViewById(R.id.radioOptD);

        //group of variables for Linear Layout containing check boxes for multi answer questions
        linearcheck = findViewById(R.id.linearCheck);
        checkAnsA = findViewById(R.id.checkAnsA);
        checkAnsB = findViewById(R.id.checkAnsB);
        checkAnsC = findViewById(R.id.checkAnsC);
        checkAnsD = findViewById(R.id.checkAnsD);

        //Onclick listener for submitanswer button
        /*submitanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        //create quizArray from Quizdata
        for (int i = 0; i < quizData.length; i++) {

            //prepare array
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); //question type
            tmpArray.add(quizData[i][1]); //question
            tmpArray.add(quizData[i][2]); //choice 1
            tmpArray.add(quizData[i][3]); //choice 2
            tmpArray.add(quizData[i][4]); //choice 3
            tmpArray.add(quizData[i][5]); //choice 4

            //Add tmpArray to quizArray.

            quizArray.add(tmpArray);
        }
        showNextQuiz();

    }

    public void showNextQuiz() {
        //Update quizCountlabel
        questionnumlabel.setText("Q" + quizCount);

        //Generate random number between 0 and Arrazysize-1
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        //pick a random quiz question from arraylist
        ArrayList<String> quiz = quizArray.get(randomNum);

        //set Question to questionlabel
        questionlabel.setText(quiz.get(1));

        // {"Number of correct answers","Question", "Choice 1", "Choice 2" "Choice 3" "Choice 4"}

        //check question type then set choices
        if (quiz.get(0).equals("One")) {
            String rightanswer = quiz.get(2); //correct answer
            quiz.remove(0); //remove type
            quiz.remove(0); // remove question
            switchtoRadiobuttons(quiz);
            //Collections.shuffle(quiz);


        } else if(quiz.get(0).equals("Two")){
            String rightanswer1 = quiz.get(2);//first correct answer
            String rightanswer2 = quiz.get(3);//second correct answer
            quiz.remove(0);//remove type
            quiz.remove(0);//remove question
            switchtoCheckboxes(quiz);
            //Collections.shuffle(quiz);

        }  else if(quiz.get(0).equals("Three")){
            String rightanswer1 = quiz.get(2);//first correct answer
            String rightanswer2 = quiz.get(3);//second correct answer
            String rightanswer3 = quiz.get(4);//third correct answer
            quiz.remove(0);//remove type
            quiz.remove(0);//remove question
            switchtoCheckboxes(quiz);

        }


    }
//disable checkboxes, enable radio buttons, add quiz choices
    public void switchtoRadiobuttons(ArrayList<String> quiz) {
        linearradio.setVisibility(View.VISIBLE);
        linearcheck.setVisibility(View.GONE);
        radioAnsA.setText(quiz.get(0));
        radioAnsB.setText(quiz.get(1));
        radioAnsC.setText(quiz.get(2));
        radioAnsD.setText(quiz.get(3));
        Collections.shuffle(quiz);


    }

//disable radio buttons, enable checkboxes, add quiz choices
    public void switchtoCheckboxes(ArrayList<String> quiz) {
        linearradio.setVisibility(View.GONE);
        linearcheck.setVisibility(View.VISIBLE);
        checkAnsA.setText(quiz.get(0));
        checkAnsB.setText(quiz.get(1));
        checkAnsC.setText(quiz.get(2));
        checkAnsD.setText(quiz.get(3));
        Collections.shuffle(quiz);




    }
}