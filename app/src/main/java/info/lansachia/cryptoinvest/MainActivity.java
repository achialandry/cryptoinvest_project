package info.lansachia.cryptoinvest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button logInBtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        fragment manager to manage fragments and transactions

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.currency_main_act);
        if (fragment == null){
            fragment = new RCurrency();

            fragmentManager.beginTransaction()

                    .replace(R.id.currency_main_act, fragment)
                    .addToBackStack(null)
                    .commit();
            Log.d("Crypto Invest", "Fragment added");
        }

        logInBtn = (Button) findViewById(R.id.log_in_main_page);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, info.lansachia.cryptoinvest.LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

}
