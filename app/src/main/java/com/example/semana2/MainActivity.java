package com.example.semana2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private TextView question;
    private EditText answer;
    private Button button;
    private Button buttonTryAgain;
    private TextView score;

    private Question currQuestion;
    private int time;
    private int scor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        button = findViewById(R.id.button);
        buttonTryAgain = findViewById(R.id.buttonTryAgain);
        score = findViewById(R.id.score);

        scor = 0;
        time = 30;
        generateNewQuestion();

        timer.setText("Tiempo: "+ time); //Incluir el tiempo real
        score.setText("Puntaje: "+scor); //Incluir el puntaje real


        //Para el timer
        new Thread(
            () -> {
                while(time > 0) {
                try {
                    time --;

                    runOnUiThread(
                        () -> {
                            timer.setText("Tiempo: "+ time);
                        });


                    Thread.sleep(1000);
                  }catch (InterruptedException e){
                    Log.e("ERROR", e.toString());
                    }
                }
             }
        ).start();


        //Cuando presione el botón:
        button.setOnClickListener(
                (view) -> {
                    checkAnswer(); //llamo el método
            }
        );
    }

    private void generateNewQuestion() {
        currQuestion = new Question();
        question.setText(currQuestion.getQuestion()); //para poner la pregunta
    }

    private void checkAnswer() {
        String userAnswer = answer.getText().toString();
        int answerInt = (int) Integer.parseInt(userAnswer); //pasar de string a int

        if (answerInt == currQuestion.getAnswer()) {
            Toast.makeText(this, "Correcto", Toast.LENGTH_SHORT).show();

            //suma puntaje
            scor += 5;
            score.setText("Puntaje: " + scor);

        } else {
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show();

            //resta puntaje
            scor -= 4;
            score.setText("Puntaje: " + scor);

        }
        generateNewQuestion();
    }
}