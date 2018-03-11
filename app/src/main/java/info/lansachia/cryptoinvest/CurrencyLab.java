package info.lansachia.cryptoinvest;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by user on 3/10/2018.
 */

public class CurrencyLab {
    private static CurrencyLab sCurrencyLab;
    private Context mContext;
    public static List<CurrencyItem> mCurrencyItems;
    RequestQueue requestQueue;
    final Handler mHandler = new Handler();
    private static final String TAG = "Currency Lab";
    public static final String URL_STRING = "https://api.coinmarketcap.com/v1/ticker/";

    private static CurrencyLab get(Context context){
        if (sCurrencyLab == null){
            sCurrencyLab = new CurrencyLab(context);
        }
        return sCurrencyLab;
    }
    public CurrencyLab(Context context){

        mContext = context.getApplicationContext();
    }

    public void requestData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_STRING,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            parseData(jsonArray);
                            Log.d(TAG, "requestData Called");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Crypto Invest", error + "failed");
                    }
                });
        //to handle refresh to query database
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                requestData();
            }
        }, 300000);
        //add request to the volley queue
//        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void parseData(JSONArray jsonArray){
        try {
            JSONObject jsonObject;
            for (int i = 0; i <= 9; i++) {

                jsonObject = jsonArray.getJSONObject(i);
                CurrencyItem currencyItem = new CurrencyItem();

                currencyItem.setCurrencyLogo(jsonObject.getString("id"));
                currencyItem.setSymbol(jsonObject.getString("symbol"));
                currencyItem.setCurrencyName(jsonObject.getString("name"));
                currencyItem.setPrice(jsonObject.getString("price_usd"));
                currencyItem.setM24Hvolume(jsonObject.getString("24h_volume_usd"));
                currencyItem.setCurrencyMarketCap(jsonObject.getString("market_cap_usd"));
                currencyItem.setAvailableSupply(jsonObject.getString("available_supply"));
                currencyItem.setTotalSupply(jsonObject.getString("total_supply"));
                currencyItem.setChangePerHourPercent(jsonObject.getString("percent_change_1h"));
                currencyItem.setChangePerDayPercent(jsonObject.getString("percent_change_24h"));
                currencyItem.setChangePerWeekPercent(jsonObject.getString("percent_change_7d"));


                mCurrencyItems.add(currencyItem);
            }


            Log.d(TAG, "parseData Called");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
