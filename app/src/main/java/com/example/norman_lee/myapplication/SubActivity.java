package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    Button buttonBackToCalculator;
    EditText editTextSubValueOfHome;
    EditText editTextSubValueOfForeign;
    public final static String INTENT_EXCH_RATE = "Exchange Rate";
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.subsharedprefs";
    public final static String HOME_KEY = "HOME_KEY";
    public final static String FOREIGN_KEY = "FOREIGN_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);


        //TODO 4.9 Implement saving to shared preferences for the contents of the EditText widget

        //TODO 3.5 Get references to the editText widgets
        //TODO 3.6 Get a reference to the Back To Calculator Button

        buttonBackToCalculator = findViewById(R.id.buttonBackToCalculator);
        editTextSubValueOfHome = findViewById(R.id.editTextSubValueHome);
        editTextSubValueOfForeign = findViewById(R.id.editTextSubValueForeign);
        //TODO 3.7 Set up setOnClickListener
        buttonBackToCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Assume that the text in the EditText widgets are well-formed numbers
                 *  i.e. 0 or positive numbers, or empty string
                 */
                String home = editTextSubValueOfHome.getText().toString();
                String foreign = editTextSubValueOfForeign.getText().toString();

                Intent intent = new Intent(SubActivity.this,
                        MainActivity.class);
                intent.putExtra(HOME_KEY, home);
                intent.putExtra(FOREIGN_KEY, foreign);
                startActivity(intent);

            }
        });
        //TODO 3.8 Obtain the values stored in the editTextWidgets
        //TODO 3.9 Check that these values are valid --> See the Utils class
        //TODO 3.10 Set up an explicit intent and pass the exchange rate back to MainActivity
        //TODO 3.11 Decide how you are going to handle a divide-by-zero situation or when negative numbers are entered
        //TODO 3.12 Decide how you are going to handle a situation when the editText widgets are empty

    }

    //TODO 4.10 Don't forget to override onPause()

}
