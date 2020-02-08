package com.example.aplicatiequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView questionLabel, questionCountLabel, scoreLabel;
    EditText answerEdt;
    Button submitButton;
    ProgressBar progressBar;
    ArrayList<QuestionModel> questionModelArrayList;
    int currentPosition = 0;
    int numberOfCorrectAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionCountLabel = findViewById(R.id.noQuestion);
        questionLabel = findViewById(R.id.question);
        scoreLabel = findViewById(R.id.score);

        answerEdt = findViewById(R.id.answer);
        submitButton = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        questionModelArrayList = new ArrayList<>();
        setUpQuestion();
        setData();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    public void checkAnswer() {
        String answerString = answerEdt.getText().toString().trim();
        if (answerString.equalsIgnoreCase(questionModelArrayList.get(currentPosition).getAnswer())) {
            numberOfCorrectAnswer++;//verificam daca raspunsul este corect , dupa Questionmodel si daca da incrementam nr de raspunsuri corecte
            Log.e("Raspuns", "corect");
            currentPosition++;
            setData();
            answerEdt.setText("");
        } else {
            Log.e("Raspuns", "gresit");
            currentPosition++;
            setData();
            answerEdt.setText("");
        }
        int x = ((currentPosition + 1) * 100) / questionModelArrayList.size();
        progressBar.setProgress(x);//pentru scor
    }

    public void setUpQuestion() {
        questionModelArrayList.add(new QuestionModel("In ce an s-a lansat CSGO ?", "2012"));
        questionModelArrayList.add(new QuestionModel("Care este cel mai mare level pe Steam ?", "5000"));
        questionModelArrayList.add(new QuestionModel("Cati jucatori a avut PUBG online simultan la lansare ?", "3500000"));
        questionModelArrayList.add(new QuestionModel("Se va lansa Escape from Tarkov pe Steam ?", "Nu"));
        questionModelArrayList.add(new QuestionModel("In ce an se va lansa GTA VI ?", "2021"));
    }

    public void setData() //punem datele in parte , intrebari/nr intrebare/scor
    {
        if (questionModelArrayList.size() > currentPosition) {
            questionLabel.setText(questionModelArrayList.get(currentPosition).getQuestionString());
            questionCountLabel.setText("Intrebarea nr : " + (currentPosition + 1));
            scoreLabel.setText("Scor : " + numberOfCorrectAnswer + "/" + questionModelArrayList.size());
        } else { // daca nu mai avem intrebari , creeam o noua activitate cu pagina de final a quiz-ului.
            Intent secondACtivityIntent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(secondACtivityIntent);
        }

    }
}
