package edu.uwgb.debuggingclass_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    /*
    There are errors in this code, that will cause various issues
    Your goal is to get the calculator working as good as possible
    1. Make sure all Numbers input the proper numbers
    2. Make sure the proper operation is applied when = is hit
    3. Think about as many edge cases as you can, can you break the calculator and then fix it?
    4. Can you make it ignore leading 0's if a bunch of 0's are pressed?
    5. BONUS: Can you get the current operation to display somewhere?
     */

    private String currentValue = "";
    private String lastValue = "";
    private String operation = "";

    //String that accumulates on the display
    private String displayString = "";

    //Instaces of helper classes
    private ShuntingYardConverter shuntingYardConverter;
    private PostfixEvaluator postfixEvaluator;
    
    //TextView to display the current value
    private TextView displayView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        
        //Link displayView to the proper view
        displayView = findViewById(R.id.displayValue);
        
        //Initialize helper class instances
        shuntingYardConverter = new ShuntingYardConverter();
        postfixEvaluator = new PostfixEvaluator();
    }

    private void insertNumber(int num) {
        if (currentValue.equals("0") && num == 0) {
            // Ignore leading zeros
            return;
        } else if (currentValue.equals("0")) {
            // Replace leading zero with the new number
            currentValue = Integer.toString(num);
        } else {
            currentValue = currentValue + Integer.toString(num);
        }
        updateDisplayOnScreen(displayString + currentValue);
    }

    private void updateDisplayOnScreen(String str) {
        displayView.setText(str);
    }

    public void onNum1(View view) {
        insertNumber(1);
    }

    public void onNum2(View view) {
        insertNumber(2);
    }

    public void onNum3(View view) {
        insertNumber(3);
    }

    public void onNum4(View view) {
        insertNumber(4);
    }

    public void onNum5(View view) {
        insertNumber(5);
    }

    public void onNum6(View view) {
        insertNumber(6);
    }

    public void onNum7(View view) {
        insertNumber(7);
    }

    public void onNum8(View view) {
        insertNumber(8);
    }

    public void onNum9(View view) {
        insertNumber(9);
    }

    public void onNum0(View view) {
        insertNumber(0);
    }

    public void onAdd(View view) {
        lastValue = currentValue;
        displayString = String.format("%s%s + ", displayString, lastValue);
        updateDisplayOnScreen(displayString);
        currentValue = "";
        operation = "add";
    }

    public void onSubtract(View view) {
        lastValue = currentValue;
        displayString = String.format("%s%s - ", displayString, lastValue);
        updateDisplayOnScreen(displayString);
        currentValue = "";
        operation = "subtract";
    }

    public void onMultiply(View view) {
        lastValue = currentValue;
        displayString = String.format("%s%s * ", displayString, lastValue);
        updateDisplayOnScreen(displayString);
        currentValue = "";
        operation = "multiply";
    }

    public void onDivide(View view) {
        lastValue = currentValue;
        displayString = String.format("%s%s / ", displayString, lastValue);
        updateDisplayOnScreen(displayString);
        currentValue = "";
        operation = "divider";
    }

    public void onEquals(View view) {
        if(lastValue.length() > 0 && currentValue.length() > 0) {
            String expressionToEvaluate;
            expressionToEvaluate = displayView.getText().toString();


        }
    }

    public void onClear(View view) {
        operation = "";
        currentValue = "";
        lastValue = "";
        displayString = "";
        updateDisplayOnScreen("0");
    }
}
