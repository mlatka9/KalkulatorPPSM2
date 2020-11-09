package m.latka.calculator;

import java.util.ArrayList;

class CalcLogic {

    private ArrayList<Double> numberList = new ArrayList<>();
    private ArrayList<String> operatorList = new ArrayList<>();

    String calculate(String allInput1) throws Exception{

        String tempNumber = "";

        if (allInput1.charAt(0) == '-') {
            allInput1 = "0" + allInput1;
        }

        for (int i = 0; i < allInput1.length(); i++) {
            String temp = String.valueOf(allInput1.charAt(i));
            switch (temp) {
                case "<": //^2
                case ">": //^3
                case "#":
                case "%":
                case "!":
                case "x":
                case "/":
                case "-":
                case "+":
                case "S":
                case "L":
                    operatorList.add(temp);
                case "=":
                    if (!(tempNumber.equals(""))) {
                        numberList.add(Double.valueOf(tempNumber));
                        tempNumber = "";
                    }
                    break;
                //numbers
                default:
                    tempNumber += temp;
                    break;
            }
        }

        while (operatorList.size() > 0) {

            //tell which function should we choose
            String maxOperator = "";

            //priority of current operator
            int prioOperator = 0;

            for(int i=0; i<operatorList.size(); i++){

                String tempOperator = operatorList.get(i);

                switch (tempOperator) {
                    case "S": //sqrt
                        if(prioOperator<4){
                            prioOperator = 4;
                            maxOperator = "sqrt";
                        }
                        break;
                    case "L": //log10
                        if(prioOperator<4){
                            prioOperator = 4;
                            maxOperator = "log10";
                        }
                        break;
                    case "<": //^2
                        if(prioOperator<3){
                            prioOperator = 3;
                            maxOperator = "power2";
                        }
                        break;
                    case ">": //^3
                        if(prioOperator<3){
                            prioOperator = 3;
                            maxOperator = "power3";
                        }
                        break;
                    case "#": //-+1
                        if(prioOperator<2){
                            prioOperator = 2;
                            maxOperator = "reciprocal";
                        }
                        break;
                    case "%":
                        if(prioOperator<2){
                            prioOperator = 2;
                            maxOperator = "percent";
                        }
                        break;
                    case "!":
                        if(prioOperator<2){
                            prioOperator = 2;
                            maxOperator = "factorial";
                        }
                        break;
                    case "/":
                        if(prioOperator<2){
                            prioOperator = 2;
                            maxOperator = "division";
                        }
                        break;
                    case "x":
                        if(prioOperator<2){
                            prioOperator = 2;
                            maxOperator = "multiplication";
                        }
                        break;
                    case "+":
                        if(prioOperator<1){
                            prioOperator = 1;
                            maxOperator = "addition";
                        }
                        break;
                    case "-":
                        if(prioOperator<1){
                            prioOperator = 1;
                            maxOperator = "subtraction";
                        }
                        break;
                }
            }

            switch (maxOperator) {
                case "reciprocal":
                    reciprocal();
                    break;
                case "division":
                    try{
                        division();
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException(e);
                    }
                    break;
                case "multiplication":
                    multiplication();
                    break;
                case "addition":
                    addition();
                    break;
                case "subtraction":
                    subtraction();
                    break;
                case "percent":
                    percent();
                    break;
                case "factorial":
                    try{
                        factorial();
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                    break;
                case "power2":
                    power2();
                    break;
                case "power3":
                    power3();
                    break;
                case "sqrt":
                    sqrt();
                    break;
                case "log10":
                    log10();
                    break;
            }
        }

        Integer integer = numberList.get(0).intValue();

        if(Math.abs(numberList.get(0)- integer)>0){
            return String.valueOf(numberList.get(0));
        } else {
            return String.valueOf(integer);
        }
    }

    private void multiplication(){
        while (operatorList.contains("x")) {
            int index = operatorList.indexOf("x");
            Double value1 = numberList.get(operatorList.indexOf("x"));
            Double value2 = numberList.get(operatorList.indexOf("x") + 1);
            numberList.set(index, value1 * value2);
            operatorList.remove(index);
            numberList.remove(index + 1);
        }
    }

    private void reciprocal(){
        while (operatorList.contains("#")) {
            int index = operatorList.indexOf("#");
            Double value1 = numberList.get(operatorList.indexOf("#"));
            numberList.set(index, value1 * -1);
            operatorList.remove(index);
        }
    }

    private void power2(){
        while (operatorList.contains("<")) {
            int index = operatorList.indexOf("<");
            Double value1 = numberList.get(operatorList.indexOf("<"));
            numberList.set(index, value1 * value1);
            operatorList.remove(index);
        }
    }

    private void power3(){
        while (operatorList.contains(">")) {
            int index = operatorList.indexOf(">");
            Double value1 = numberList.get(operatorList.indexOf(">"));
            numberList.set(index, value1 * value1 * value1);
            operatorList.remove(index);
        }
    }

    private void division() throws IllegalArgumentException {
        while (operatorList.contains("/")) {
            int index = operatorList.indexOf("/");
            Double value1 = numberList.get(operatorList.indexOf("/"));
            Double value2 = numberList.get(operatorList.indexOf("/") + 1);
            if(value2.equals(0.0)) throw new DivisionByZeroException();
            numberList.set(index, value1 / value2);
            operatorList.remove(index);
            numberList.remove(index + 1);
        }
    }

    private void subtraction(){
        while (operatorList.contains("-")) {
            int index = operatorList.indexOf("-");
            Double value1 = numberList.get(operatorList.indexOf("-"));
            Double value2 = numberList.get(operatorList.indexOf("-") + 1);
            numberList.set(index, value1 - value2);
            operatorList.remove(index);
            numberList.remove(index + 1);
        }
    }

    private void addition(){
        while (operatorList.contains("+")) {
            int index = operatorList.indexOf("+");
            Double value1 = numberList.get(operatorList.indexOf("+"));
            Double value2 = numberList.get(operatorList.indexOf("+") + 1);
            numberList.set(index, value1 + value2);
            operatorList.remove(index);
            numberList.remove(index + 1);
        }
    }

    private void percent(){
        while (operatorList.contains("%")) {
            int index = operatorList.indexOf("%");
            Double value1 = numberList.get(operatorList.indexOf("%"));
            numberList.set(index, value1 / 100.0);
            operatorList.remove(index);
        }
    }

    private void sqrt(){
        while (operatorList.contains("S")) {
            int index = operatorList.indexOf("S");
            Double value1 = numberList.get(operatorList.indexOf("S"));
            numberList.set(index, Math.sqrt(value1));
            operatorList.remove(index);
        }
    }

    private void log10(){
        while (operatorList.contains("L")) {
            int index = operatorList.indexOf("L");
            Double value1 = numberList.get(operatorList.indexOf("L"));
            numberList.set(index, Math.log10(value1));
            operatorList.remove(index);
        }
    }

    private void factorial() throws Exception{
        while (operatorList.contains("!")) {
            int index = operatorList.indexOf("!");
            Double value1 = numberList.get(operatorList.indexOf("!"));
            try{
                numberList.set(index, Double.valueOf(factorialCalc(value1.intValue())) );
            } catch (Exception e){
                throw new Exception(e);
            }
            operatorList.remove(index);
        }
    }

    private int factorialCalc(int value) throws Exception{
        if(value>100000.0) throw new Exception();
        if((value==0) || (value==1)){
            return 1;
        } else {
            return value * factorialCalc(value-1);
        }
    }
}