package info.lansachia.cryptoinvest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button logInBtn;
    private Context mContext;
    private TextView mCurrencyTrackingName;
    private TextView mCurrencyTrackingByValue;
    private FloatingActionButton mFloatingActionButton;


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

        //reference Views for currency tracking name and values
        mCurrencyTrackingName = (TextView)findViewById(R.id.currency_tracking_name);
        mCurrencyTrackingByValue = (TextView)findViewById(R.id.currency_tracking_value);

        //reference to notification
        mFloatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotificationSettingFragment();
            }
        });



    }

    //When activity resumes
    @Override
    protected void onResume(){
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            //determines what fragment started the activity
            final String sender = bundle.getString("FRAGMENT_CURRENCY_NAME_KEY");

            //If its the fragment then data is allocated to it
            if(sender != null)
            {
                this.registerCurrencySettingFromFragment();
                Toast.makeText(this, "Notification Setting Registerd!", Toast.LENGTH_LONG).show();
            }
        }


    }

    //open currency notification setting fragment on click
    private void openNotificationSettingFragment()
    {
        //
        NotificationSettingFragment notificationSettingFragment = new NotificationSettingFragment();
        //show fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity, notificationSettingFragment).commit();
    }

    /*
    Receive data from fragment
     */
    private void registerCurrencySettingFromFragment()
    {
        //receive setting data from fragment via intents
        Intent i = getIntent();
        String currencyName = i.getStringExtra("TRACK_NAME_KEY");
        int currencyValue = i.getIntExtra("TRACK_VALUE_KEY",0);

        //Set currency data to views
        mCurrencyTrackingName.setText(currencyName + ":");
        mCurrencyTrackingByValue.setText("$ "+ String.valueOf(currencyValue));
    }


}
