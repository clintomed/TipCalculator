package com.clintonmedbery.tipcalculator;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Email extends ActionBarActivity {

    public EditText recipientText;
    public EditText subjectText;
    public EditText bodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        bodyText = (EditText) findViewById(R.id.bodyText);
        recipientText = (EditText) findViewById(R.id.recipientsText);
        subjectText = (EditText) findViewById(R.id.subjectText);
        Intent receiveIntent = getIntent();
        String tip = receiveIntent.getStringExtra("extraTip");
        String total = receiveIntent.getStringExtra("extraTotal");
        String body = "Tip: " + tip + "\n" + "Total: " + total;
        bodyText.setText(body);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_email, menu);
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

    public void send(View view){
        String[] recipients = {recipientText.getText().toString()};
        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        email.setType("message/rfc822");

        email.putExtra(Intent.EXTRA_EMAIL, recipients);
        email.putExtra(Intent.EXTRA_SUBJECT, subjectText.getText().toString());
        email.putExtra(Intent.EXTRA_TEXT, bodyText.getText().toString());

        try {
            startActivity(Intent.createChooser(email, "Choose an email client..."));
        } catch (ActivityNotFoundException error) {
            Toast.makeText(Email.this, "No email client installed.", Toast.LENGTH_LONG).show();
        }
    }
}
