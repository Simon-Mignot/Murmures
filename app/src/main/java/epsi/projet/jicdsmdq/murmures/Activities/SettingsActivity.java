package epsi.projet.jicdsmdq.murmures.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import epsi.projet.jicdsmdq.murmures.Handlers.MiscHandler;
import epsi.projet.jicdsmdq.murmures.Handlers.OptionsHandler;
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
        text.setText(OptionsHandler.options_vibratorPattern);
        Button testVibrator = (Button)findViewById(R.id.buttontestvibrator);
        testVibrator.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OptionsHandler.options_vibratorPattern = text.getText().toString();
                MiscHandler.vibrate();
            }
        });

        CheckBox vibrator = (CheckBox)findViewById(R.id.checkBox);
        vibrator.setChecked(OptionsHandler.options_vibrator);
        vibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                OptionsHandler.options_vibrator = isChecked;
            }
        });
    }
}
