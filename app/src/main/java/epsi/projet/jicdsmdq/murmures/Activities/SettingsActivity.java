package epsi.projet.jicdsmdq.murmures.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import epsi.projet.jicdsmdq.murmures.Classes.DataHandler;
import epsi.projet.jicdsmdq.murmures.R;

/**
 * Created by Simon on 14/11/2016.
 */
public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Options");

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        final EditText text = (EditText)findViewById(R.id.editvibrator);
        text.setText(DataHandler.options_vibratorPattern);
        Button testVibrator = (Button)findViewById(R.id.buttontestvibrator);
        testVibrator.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataHandler.options_vibratorPattern = text.getText().toString();
                DataHandler.vibrer();
            }
        });

        CheckBox vibrator = (CheckBox)findViewById(R.id.checkBox);
        vibrator.setChecked(DataHandler.options_vibrator);
        vibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                DataHandler.options_vibrator = isChecked;
            }
        });
    }
}
