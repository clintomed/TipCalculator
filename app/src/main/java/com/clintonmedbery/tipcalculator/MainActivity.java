package com.clintonmedbery.tipcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public EditText amountText;
    public TextView tipLabel;
    public TextView totalLabel;
    public double billAmount;
    public double newAmount;
    public double tipAmount;

    public RadioButton tenPercent;
    public RadioButton fifteenPercent;
    public RadioButton eighteenPercent;
    public RadioButton twentyPercent;

    public Button sendEmail;

    public RadioGroup percentGroup;

    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendEmail = (Button) findViewById(R.id.sendEmail);
        sendEmail.setVisibility(View.INVISIBLE);

        amountText = (EditText) findViewById(R.id.amountText);
        tipLabel = (TextView) findViewById(R.id.tipAmount);
        totalLabel = (TextView) findViewById(R.id.totalAmount);

        tenPercent = (RadioButton) findViewById(R.id.tenPercentRadio);
        fifteenPercent = (RadioButton) findViewById(R.id.fifteenPercentRadio);
        eighteenPercent = (RadioButton) findViewById(R.id.eighteenPercentRadio);
        twentyPercent = (RadioButton) findViewById(R.id.twentyPercentRadio);

        percentGroup = (RadioGroup) findViewById(R.id.radioTip);

        percentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.tenPercentRadio){
                    updateTip(.1);
                } else if(checkedId == R.id.fifteenPercentRadio){
                    updateTip(.15);
                } else if(checkedId == R.id.eighteenPercentRadio) {
                    updateTip(.18);
                } else if(checkedId == R.id.twentyPercentRadio) {
                    updateTip(.2);
                }
            }
        });

        amountText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){


                    if(tenPercent.isChecked()){
                        updateTip(.1);
                    } else if(fifteenPercent.isChecked()) {
                        updateTip(.15);
                    } else if(eighteenPercent.isChecked()){
                        updateTip(.18);
                    } else if(twentyPercent.isChecked()) {
                        updateTip(.2);
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "No Percentage Given!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }


                }
                return false;
            }
        });


    }

    public void updateTip(double percentage){
        newAmount = Double.parseDouble(amountText.getText().toString());
        tipAmount = newAmount * percentage;
        billAmount = newAmount + tipAmount;
        tipLabel.setText(currency.format(tipAmount));
        totalLabel.setText(currency.format(billAmount));
        sendEmail.setVisibility(View.VISIBLE);

    }

    public void sendEmail(View view){
        Intent emailIntent = new Intent(this, Email.class);
        String emailTip = currency.format(tipAmount);
        String emailTotal = currency.format(billAmount);
        emailIntent.putExtra("extraTip", emailTip);
        emailIntent.putExtra("extraTotal", emailTotal);
        startActivity(emailIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle outState){
        outState.putDouble("tip", tipAmount);
        outState.putDouble("billTotal", billAmount);
        outState.putString("amountEdit", amountText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle savedBundle){
        super.onRestoreInstanceState(savedBundle);
        amountText.setText(savedBundle.getString("amountEdit"));
        tipLabel.setText(currency.format(savedBundle.getDouble("tip")));
        totalLabel.setText(currency.format(savedBundle.getDouble("billTotal")));

    }
}
