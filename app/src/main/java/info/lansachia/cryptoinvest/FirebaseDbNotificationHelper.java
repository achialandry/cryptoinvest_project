package info.lansachia.cryptoinvest;

import android.util.Log;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by user on 3/9/2018.
 */

public class FirebaseDbNotificationHelper {
    DatabaseReference mDatabaseReference;
    Boolean mSaved = null;

    //constructor
    public FirebaseDbNotificationHelper(DatabaseReference databaseReference){
        this.mDatabaseReference = databaseReference;
    }

    //Saving data to database
    public Boolean saveDataToFirebase(CurrencyFireBaseDataModel currencyFireBaseDataModel){
        if (currencyFireBaseDataModel == null){
            mSaved = false;
        }else{
            try {
                mDatabaseReference.child("UserSetting").setValue(currencyFireBaseDataModel);
                mSaved = true;
                Log.d("Firebase Db", "Saving data to Fb method");
            }catch (DatabaseException e){
                e.printStackTrace();
                mSaved = false;
            }
        }

        return mSaved;
    }


}
