package epsi.projet.jicdsmdq.murmures.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import epsi.projet.jicdsmdq.murmures.Classes.DataHandler;
import epsi.projet.jicdsmdq.murmures.R;
import epsi.projet.jicdsmdq.murmures.Server.Server;

public class LoginActivity extends AppCompatActivity
{
    private EditText editTextPseudo;
    private TextInputLayout textInputLayoutPseudo;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
        setContentView(R.layout.acivity_login);
        setTitle(getString(R.string.login_label_menu));

        editTextPseudo = (EditText) findViewById(R.id.editTextPseudo);
        editTextPseudo.setText(android.os.Build.MODEL);
        textInputLayoutPseudo = (TextInputLayout) findViewById(R.id.textInputLayoutPseudo);
        loginButton = (Button) findViewById(R.id.buttonConnexion);

        editTextPseudo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    loginButton.callOnClick();
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextPseudo.getText().toString().trim().isEmpty())
                {
                    textInputLayoutPseudo.setError(getString(R.string.err_msg_pseudo));
                    //editTextPseudo.setHintTextColor(getResources().getColor(R.color.colorError));
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                    textInputLayoutPseudo.startAnimation(shake);
                }
                else
                {
                    textInputLayoutPseudo.setError(null);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("pseudo", editTextPseudo.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
