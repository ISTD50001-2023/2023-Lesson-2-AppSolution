package com.example.norman_lee.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonConvert;
    Button buttonSetExchangeRate;
    EditText editTextValue;
    TextView textViewResult;
    TextView textViewExchangeRate;
    //double exchangeRate; //<--- REMOVE THIS
    public final String TAG = "Logcat";
    private SharedPreferences mPreferences;
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    public static final String RATE_KEY = "Rate_Key";
    ExchangeRate exchangeRate; // <-- ADD THIS
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 4.5 Get a reference to the sharedPreferences object
        //TODO 4.6 Retrieve the value using the key, and set a default when there is none
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE );
        /*** app is already been used, or app is used for the first time **/
        String exchangeRateString = mPreferences.getString(RATE_KEY,
                getString(R.string.default_exchange_rate));


        //TODO 3.13 Get the intent, retrieve the values passed to it, and instantiate the ExchangeRate class
        //TODO 3.13a See ExchangeRate class --->

        /***
         * 1. back to here using an Intent ==> data is available in the intent
         * 2. app is started for the first time ==> no intent data is available
         * ==> user click on icon on phone ==> data comes from SharedPreferences
         * i.e. default exchange rate
         */
        Intent intent = getIntent();
        String home = intent.getStringExtra(SubActivity.HOME_KEY);
        String foreign = intent.getStringExtra(SubActivity.FOREIGN_KEY);
        if ( home == null || foreign == null){
            //exchangeRate = new ExchangeRate(); // MODIFY vvvvvvvv
            exchangeRate = new ExchangeRate( exchangeRateString );
        }else{
            exchangeRate  = new ExchangeRate( home, foreign);
        }

        //TODO 2.1 Use findViewById to get references to the widgets in the layout
        buttonConvert = findViewById(R.id.buttonConvert);
        buttonSetExchangeRate = findViewById(R.id.buttonSetExchangeRate);
        editTextValue = findViewById(R.id.editTextValue);
        textViewResult = findViewById(R.id.textViewResult);
        textViewExchangeRate = findViewById(R.id.textViewExchangeRate);
        //TODO 2.2 Assign a default exchange rate of 2.95 to the textView

        ///exchangeRate = new ExchangeRate(); // MOVE THIS UP ^^^^^
        //textViewExchangeRate.setText(R.string.default_exchange_rate);
        textViewExchangeRate.setText( exchangeRate.getExchangeRate().toString() );
        //^^^^^ change this  ^^^^^^

        //TODO 2.3 Set up setOnClickListener for the Convert Button
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*** 1. get the text entered into the editText widget
                 *   2. if the text is empty, display a Toast
                 *      else, do the calculation and display the answer
                 */
                String value = editTextValue.getText().toString();
                if( value.isEmpty() ){
                    // this - in the inner class, MainActivity.this - outer class
                    Toast.makeText( MainActivity.this,
                            R.string.empty_edit_text_message,Toast.LENGTH_LONG ).show();
                }else{
                    // calculateAmount() returns a BigDecimal object ...
                    String result = exchangeRate.calculateAmount(value).toString();
                    textViewResult.setText(result);
                }
            }
        });
        //TODO 2.4 Display a Toast & Logcat message if the editTextValue widget contains an empty string
        //TODO 2.5 If not, calculate the units of B with the exchange rate and display it
        //TODO 2.5a See ExchangeRate class --->

        //TODO 3.1 Modify the Android Manifest to specify that the parent of SubActivity is MainActivity
        //TODO 3.2 Get a reference to the Set Exchange Rate Button
        //TODO 3.3 Set up setOnClickListener for this
        //TODO 3.4 Write an Explicit Intent to get to SubActivity

        buttonSetExchangeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this,
                        SubActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString(RATE_KEY, exchangeRate.getExchangeRate().toString());
        preferencesEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //TODO 4.1 Go to res/menu/menu_main.xml and add a menu item Set Exchange Rate
    //TODO 4.2 In onOptionsItemSelected, add a new if-statement and code accordingly

    //TODO 5.1 Go to res/menu/menu_main.xml and add a menu item Open Map App
    //TODO 5.2 In onOptionsItemSelected, add a new if-statement
    //TODO 5.3 code the Uri object and set up the intent

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /** example for you, does nothing */
        if (id == R.id.action_settings) {
            return true;
        }
        if( id == R.id.menu_open_google_maps){
            Uri.Builder uriBuilder = new Uri.Builder();
            /** geo:0.0?q=ChangiAirport */

            uriBuilder.scheme("geo").opaquePart("0.0")
                    .appendQueryParameter("q","ChangiAirport");

            Uri uri = uriBuilder.build();
            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            if( intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
        }
        if( id == R.id.menu_set_exchange_rate){
            Intent intent = new Intent( this, SubActivity.class);
            startActivity(intent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    //TODO 4.3 override the methods in the Android Activity Lifecycle here
    //TODO 4.4 for each of them, write a suitable string to display in the Logcat

    //TODO 4.7 In onPause, get a reference to the SharedPreferences.Editor object
    //TODO 4.8 store the exchange rate using the putString method with a key

}
