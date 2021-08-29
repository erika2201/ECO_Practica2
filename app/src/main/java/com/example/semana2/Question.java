package com.example.semana2;

public class Question {

    private int operA;
    private int operB;
    private String operator;
    private String[] operators ={ "+", "-", "x", "/" };


    //Constructor
    public Question(){
        this.operA = (int)(Math.random() * 11);
        this.operB = (int)((Math.random() * 11)+1); //+1 para que no se divida entre 0 y evitar error

        int position = (int) (Math.random()*4); //para acceder a las posiciones del arreglo
        this.operator = operators[position];
    }

    public String getQuestion() {
        return operA+ "" +operator+ "" +operB;
    }

    public int getAnswer() {
        int answer = 0;
        switch (operator){
            case "+":
                answer = this.operA + this.operB;
                break;
            case "-":
                answer = this.operA - this.operB;
                break;
            case "x":
                answer = this.operA * this.operB;
                break;
            case "/":
                answer = this.operA / this.operB;
                break;
        }

        return answer;
    }
}
