package info.lansachia.cryptoinvest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by user on 3/8/2018.
 */

public class NotificationSettingFragment extends Fragment {
    private EditText mCurrencyTrackName;
    private Spinner mCurrencyValueTrack;
    private Button mApplySettingButton;
    private DatabaseReference mDatabaseReference;
    private FirebaseDbNotificationHelper mDbNotificationHelper;
    private Context mContext;

    RequestQueue requestQueue;
    final Handler mHandler = new Handler();
    public static final String TAG = "Currency";
    private static final int NUMBER_OF_CURRENCY = 30;
    public static final String URL_STRING = "https://api.coinmarketcap.com/v1/ticker/";


    private static final String  CURRENCY_LIST_RECEIVED = "CurrencyList";



    private String currencyUserPriceIncrementValueForNotification;
    private String userCurrencyNameForNotification;
    private RCurrency mRCurrency = new RCurrency();
    private List<CurrencyItem> mItem;
    private CurrencyItem mCurrencyItemR;
    String currentPriceCurrency;
    String currencyId;

    //required empty controller
    public NotificationSettingFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //receive data
        Bundle bundle = new Bundle();
        mCurrencyItemR = (CurrencyItem)bundle.getSerializable(CURRENCY_LIST_RECEIVED);

        //Setting up firebase Db
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDbNotificationHelper = new FirebaseDbNotificationHelper(mDatabaseReference);
        //inflate the layout from this view
        View notificationView = inflater.inflate(R.layout.notification_setting_fragment, container, false);

        //initalize the views
        mCurrencyTrackName = (EditText)notificationView.findViewById(R.id.currency_track_name_on_fragment);
        mCurrencyValueTrack = (Spinner)notificationView.findViewById(R.id.currency_track_value_spinner);
        mApplySettingButton = (Button)notificationView.findViewById(R.id.apply_notification_setting);

        fillTrackValues();

        mApplySettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseData();
                sendData();
            }
        });
        return notificationView;
    }


    //fill values into spinner

    private void fillTrackValues() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1);
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.add("5");
        adapter.add("10");
        adapter.add("25");
        adapter.add("50");
        adapter.add("100");
        adapter.add("150");
        adapter.add("250");
        adapter.add("300");
        adapter.add("500");
        adapter.add("750");
        adapter.add("1000");
        adapter.add("1500");
        adapter.add("2000");
        adapter.add("2500");
        adapter.add("5000");

        //set adapter instance to spinner
        mCurrencyValueTrack.setAdapter(adapter);

    }

    private void sendData()
    {

        //intent object
        Intent i = new Intent(getActivity().getBaseContext(),
                MainActivity.class);

        //package data
        i.putExtra("FRAGMENT_CURRENCY_NAME_KEY", "NotificationSettingFragment");
        i.putExtra("TRACK_NAME_KEY", mCurrencyTrackName.getText().toString());
        i.putExtra("TRACK_VALUE_KEY", Integer.valueOf(mCurrencyValueTrack.getSelectedItem().toString()));

        //reset widgets
        mCurrencyTrackName.setText("");
        mCurrencyValueTrack.setSelection(0);

        //Start activity
        getActivity().startActivity(i);
    }

    //properties to be saved to firebase
    private void firebaseData(){
        CurrencyFireBaseDataModel model = new CurrencyFireBaseDataModel();
        //get data from user entry on fragment
        currencyUserPriceIncrementValueForNotification = mCurrencyValueTrack.getSelectedItem().toString();
        userCurrencyNameForNotification = mCurrencyTrackName.getText().toString();

        Log.d("Firebase Db", "Created Firebase");


        //get data from api call

//check home
        if (mItem != null){
            if (mItem.contains(userCurrencyNameForNotification.toLowerCase())){

                    currentPriceCurrency = mCurrencyItemR.getPrice();
                    currencyId = mCurrencyItemR.getCurrencyLogo();


            }
        }

        //returns null check
        model.setCurrencyId(currencyId);
        //returns null check
        model.setCurrencyPrice(currentPriceCurrency);

        model.setUserSettingCurrencyName(userCurrencyNameForNotification);
        model.setUserSettingCurrencyValue(currencyUserPriceIncrementValueForNotification);
        //saving the data
        if(userCurrencyNameForNotification.length()>0){
            mDbNotificationHelper.saveDataToFirebase(model);
            Log.d("Firebase Db", "Saved to Firebase");
        }else {
            Log.d("Firebase Db", "Saved to Firebase Failed");
            Toast.makeText(getActivity(), "Invalid Currency name or No Input", Toast.LENGTH_SHORT).show();
        }

    }

}
