package com.example.semana2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private TextView question;
    private EditText answer;
    private Button button;
    private Button tryAgainButton;
    private TextView score;

    private Question currQuestion;
    private int time;
    private int scor;

    //Skip pregunta
    private int pressTime;
    private boolean isPressing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timer);
        question = findViewById(R.id.question);
        answer = findViewById(R.id.answer);
        button = findViewById(R.id.button);
        tryAgainButton = findViewById(R.id.tryAgainButton);
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


        //Skip pregunta
        question.setOnTouchListener(
                (view, motionEvent) -> {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            isPressing = true;
                            new Thread(()->{
                                pressTime = 0;
                                while(pressTime <1500){
                                    try {
                                        Thread.sleep(150);
                                        pressTime+=150;
                                        if(!isPressing){
                                            return; //Matar el hilo c:
                                        }
                                    }catch (InterruptedException e){
                                    }
                                }
                                runOnUiThread(()->{
                                    Toast.makeText(this, "Pasó el tiempo", Toast.LENGTH_SHORT).show();
                                    generateNewQuestion();
                                });

                            }).start();
                            break;
                        case MotionEvent.ACTION_UP:
                            isPressing = false;
                            break;
                    }
                    return true;
                });


        //Intentar de nuevo
        tryAgainButton.setOnClickListener(
                (view) -> {
                    retry();
                });
    }

    //MÉTODOS
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

    private void retry(){
        generateNewQuestion();
        scor=0;
        score.setText("Puntaje:"+ scor);
        time=30;
        timer.setText("Tiempo: "+ time);
        answer.setText("");
        tryAgainButton.setVisibility(View.GONE);

        new Thread(
                () ->{
                    while(time>0){
                        time--;
                        tryAgainButton.setVisibility(View.GONE);
                        runOnUiThread(
                                () ->{
                                    timer.setText("Tiempo: "+ time);
                                });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    }
                    runOnUiThread(
                            ()->{
                                tryAgainButton.setVisibility(View.VISIBLE);
                            });
                }
        ).start();
    }
}