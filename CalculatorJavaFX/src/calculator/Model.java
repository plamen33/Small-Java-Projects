package calculator;

public class Model {

    public long calculate(long number1, long number2, String operator) {  // we create a method named calculate
        switch (operator) {
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "*":
                return number1 * number2;
            case "/":
                if (number2 == 0){
                    return 0;
                }
                return number1 / number2;
        }

        System.out.println("Unknown operator - " + operator);  // if we send non-standard input - type Unknown operator
        return 0;
    }
}