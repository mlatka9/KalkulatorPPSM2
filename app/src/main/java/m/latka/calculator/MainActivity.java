package m.latka.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String allInput = "";
    private boolean operatorFlag = false;
    private boolean numberFlag = true;
    private boolean commaFlag = true;
    private boolean factorialFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("KEY_allInput",allInput);
        outState.putBoolean("KEY_operatorFlag",operatorFlag);
        outState.putBoolean("KEY_numberFlag",numberFlag);
        outState.putBoolean("KEY_commaFlag",commaFlag);
        outState.putBoolean("KEY_factorialFlag",factorialFlag);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allInput = savedInstanceState.getString("KEY_allInput","");
        operatorFlag = savedInstanceState.getBoolean("KEY_operatorFlag",false);
        numberFlag = savedInstanceState.getBoolean("KEY_numberFlag",true);
        commaFlag = savedInstanceState.getBoolean("KEY_commaFlag",true);
        factorialFlag = savedInstanceState.getBoolean("KEY_factorialFlag",false);
        printAllInput(allInput);
    }

    public void readInput(View view) {
        CalcLogic calcLogic = new CalcLogic();

        Button button = (Button) view;
        String singleInput = (String) button.getText();

        if(allInput.equals("@")){
            allInput="";
            operatorFlag = false;
            numberFlag = true;
            commaFlag = true;
        }
        if(allInput.equals("Infinity")){
            allInput="";
            operatorFlag = false;
            numberFlag = true;
            commaFlag = true;
        }
        switch (singleInput) {
            case "AC":
                allInput = "";
                operatorFlag = false;
                numberFlag = true;
                commaFlag = true;
                break;
            case "+/-":
                if (operatorFlag) {
                    allInput += "#";
                    operatorFlag = true;
                    numberFlag = false;
                    commaFlag = true;
                }
                break;
            case "%":
                if (operatorFlag) {
                  //  allInput += "/100";
                    allInput += "%";
                    operatorFlag = true;
                    numberFlag = false;
                    commaFlag = true;
                }
                break;
            case "-":
            case "/":
            case "x":
            case "+":
                if (operatorFlag) {
                    allInput += singleInput;
                    operatorFlag = false;
                    numberFlag = true;
                    commaFlag = true;
                }
                break;
            case ".":
                if(commaFlag){
                    allInput += singleInput;
                    operatorFlag = false;
                    commaFlag = false;
                    numberFlag = true;
                }
                break;
            case "x^2": //^2
                if(operatorFlag){
                    allInput += "<";
                    operatorFlag = true;
                    commaFlag = false;
                    numberFlag = false;
                }
                break;
            case "x^3": //^3
                if(operatorFlag){
                    allInput += ">";
                    operatorFlag = true;
                    commaFlag = false;
                    numberFlag = false;
                }
                break;
            case "x!": //^3
                if(factorialFlag){
                    allInput += "!";
                    operatorFlag = true;
                    numberFlag = false;
                    commaFlag = true;
                    factorialFlag = false;
                }
                break;
            case "sqrt(x)":
                if(numberFlag && commaFlag &&!operatorFlag){
                    allInput += "S"; //sqrt
                    operatorFlag = false;
                    numberFlag = true;
                    commaFlag = false;
                    factorialFlag = false;
                }
                break;
            case "log10":
                if(numberFlag && commaFlag &&!operatorFlag){
                    allInput += "L"; //log10
                    operatorFlag = false;
                    numberFlag = true;
                    commaFlag = false;
                    factorialFlag = false;
                }
                break;
            case "=":
                if (operatorFlag) {
                    allInput += singleInput;
                    try {
                        allInput = calcLogic.calculate(allInput);
                    } catch (Exception e) {
                        e.printStackTrace();
                        allInput = "@";
                    }
                    operatorFlag = true;
                    numberFlag = true;
                    commaFlag = false;
                    factorialFlag = true;
                    if(!(allInput.contains("."))) {
                        commaFlag = true;
                    }
                }
                break;
                //numbers
            default:
                if (numberFlag) {
                    allInput += singleInput;
                    operatorFlag = true;
                    factorialFlag = true;
                    commaFlag = true;

                }
                break;
        }
        printAllInput(allInput);
    }

    private void printAllInput(String allInput) {
        TextView resultView = findViewById(R.id.textView3);
        StringBuilder sb = new StringBuilder();
        if(allInput.equals("@")){
            resultView.setText(getResources().getString(R.string.error));
        }
        else if(allInput.equals("")){
            resultView.setText("0");
        } else {
            for(int i=0; i<allInput.length(); i++){
                if(allInput.charAt(i)=='#') {
                    sb.append("*-1");
                }
                else if(allInput.charAt(i)=='S') {
                    sb.append("sqrt");
                }
                else if(allInput.charAt(i)=='<') {
                    sb.append("^2");
                }
                else if(allInput.charAt(i)=='>') {
                    sb.append("^3");
                }
                else if(allInput.charAt(i)=='L') {
                    sb.append("log(10)");
                }else {
                    sb.append(allInput.charAt(i));
                }
            }
            resultView.setText(sb.toString());
        }
    }
}