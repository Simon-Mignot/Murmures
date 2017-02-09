package epsi.projet.jicdsmdq.murmures.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import epsi.projet.jicdsmdq.murmures.Handlers.DataHandler;
import epsi.projet.jicdsmdq.murmures.Handlers.MiscHandler;
import epsi.projet.jicdsmdq.murmures.R;

public class LoginActivity extends AppCompatActivity
{
    private EditText editTextPseudo;
    private TextView textInputLayoutPseudo;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Ajout Layout LOGIN
        setContentView(R.layout.acivity_login);
        // Modification Titre Bar Principal
        setTitle(getString(R.string.login_label_menu));

        // Version de l'application
        ((TextView)findViewById(R.id.textCreditVersion)).setText(MiscHandler.version);

        // Variables Accès aux Inputs
        textInputLayoutPseudo = (TextView) findViewById(R.id.textInputLayoutPseudo);
        loginButton = (Button) findViewById(R.id.buttonConnexion);
        editTextPseudo = (EditText) findViewById(R.id.editTextPseudo);

        // Set OS Tel dans le Pseudo
        editTextPseudo.setText(android.os.Build.MODEL);

        // Si Click Bouton OK clavier
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

        // Si Click sur le Bouton pour Ajouter Pseudo
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextPseudo.getText().toString().trim().isEmpty())
                {
                    // Set Les Erreurs
                    editTextPseudo.setError(getString(R.string.err_msg_pseudo));
                    textInputLayoutPseudo.setTextColor(getResources().getColor(R.color.colorError));
                    // Animation Tremblement
                    Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
                    textInputLayoutPseudo.startAnimation(shake);
                }
                else
                {
                    // Remet Erreurs à Zéro
                    textInputLayoutPseudo.setError(null);
                    textInputLayoutPseudo.setTextColor(getResources().getColor(R.color.cardview_light_background));

                    // Démarrage Activité HOME
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("pseudo", editTextPseudo.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
