package info.lansachia.cryptoinvest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 3/1/2018.
 */

public class RCurrency extends Fragment {
    RecyclerView mRecyclerView;
    List<CurrencyItem> mCurrencyItems;
    String currencies = "Currency";
    CurrencyItemAdapter mCurrencyItemAdapter;
    public static final String TAG = "RCurrency";

    Context mContext;
//    CurrencyItemAdapter mCurrencyItemAdapter;
    public static final String URL_STRING = "https://api.coinmarketcap.com/v1/ticker/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyItems = new ArrayList<>();
        Log.d(TAG, "onCreate Called");
        requestData();
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        //fragment inflater
        View view = inflater.inflate(R.layout.fragment_currency_home,container, false);

        //recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.currency_recycler_view);
        mCurrencyItemAdapter = new CurrencyItemAdapter(getContext(), mCurrencyItems);
        mRecyclerView.setAdapter(mCurrencyItemAdapter);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

        mCurrencyItemAdapter.notifyDataSetChanged();

//        mCurrencyItemAdapter = new CurrencyItemAdapter(getContext(), mCurrencyItems);
//        mRecyclerView.setAdapter(mCurrencyItemAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        Log.d(TAG, "onCreateView Called");
        return view;
    }

    public void requestData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_STRING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
//                            JSONObject jsonObject;
//                            for (int i = 0; i <= 4; i++) {
//                                jsonObject = jsonArray.getJSONObject(i);
//
//                                CurrencyItem currencyItem = new CurrencyItem();
//
//                                currencyItem.setCurrencyLogo(jsonObject.getString("id"));
//                                currencyItem.setSymbol(jsonObject.getString("symbol"));
//                                currencyItem.setCurrencyName(jsonObject.getString("name"));
//                                currencyItem.setPrice(jsonObject.getString("price_usd"));
//                                currencyItem.setM24Hvolume(jsonObject.getString("24h_volume_usd"));
//                                currencyItem.setCurrencyMarketCap(jsonObject.getString("market_cap_usd"));
//                                currencyItem.setAvailableSupply(jsonObject.getString("available_supply"));
//                                currencyItem.setTotalSupply(jsonObject.getString("total_supply"));
//                                currencyItem.setChangePerHourPercent(jsonObject.getString("percent_change_1h"));
//                                currencyItem.setChangePerDayPercent(jsonObject.getString("percent_change_24h"));
//                                currencyItem.setChangePerWeekPercent(jsonObject.getString("percent_change_7d"));
//
//                                mCurrencyItems.add(currencyItem);
//                            }
//
//                            mCurrencyItemAdapter = new CurrencyItemAdapter(getContext(), mCurrencyItems);
//
//                            mRecyclerView.setAdapter(mCurrencyItemAdapter);
//
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//                            mRecyclerView.setLayoutManager(layoutManager);
//                            mRecyclerView.setHasFixedSize(true);
//                            mCurrencyItemAdapter.notifyDataSetChanged();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",currencies);

                return params;
            }

            //add request to the volley queue

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void parseData(JSONArray jsonArray){
        try {
            JSONObject jsonObject;
            for (int i = 0; i <= 4; i++) {
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