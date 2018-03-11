package info.lansachia.cryptoinvest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;

/**
 * Created by user on 3/1/2018.
 */

public class RCurrency extends Fragment{
    private RecyclerView mRecyclerView;
    public static List<CurrencyItem> mCurrencyItems;
    private SearchView mSearchView;
    private String currencies = "Currency";
    private CurrencyItemAdapter mCurrencyItemAdapter;
    private static final int NUMBER_OF_CURRENCY = 30;
    public static final String TAG = "RCurrency";
    public static final String CURRENCY_LIST = "CurrencyList";




    private Context mContext;
    RequestQueue requestQueue;
    final Handler mHandler = new Handler();
    public static final String URL_STRING = "https://api.coinmarketcap.com/v1/ticker/";



    public RCurrency(){
        //required blank constructor
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyItems = new ArrayList<>();
        Log.d(TAG, "onCreate Called");

    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        requestData();

        //passing data
        Bundle bundle = new Bundle();
        CurrencyItem serialCurrencyItem = new CurrencyItem();

        bundle.putSerializable(CURRENCY_LIST,serialCurrencyItem );
        NotificationSettingFragment notificationSettingFragment = new NotificationSettingFragment();
        notificationSettingFragment.setArguments(bundle);





        //fragment inflater
        View view = inflater.inflate(R.layout.fragment_currency_home,container, false);

        //search view
        mSearchView = (SearchView) view.findViewById(R.id.search_box);
        //recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.currency_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        mCurrencyItemAdapter = new CurrencyItemAdapter(getActivity(), mCurrencyItems);


        mRecyclerView.setAdapter(mCurrencyItemAdapter);

        //searching
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filter as user types
                mCurrencyItemAdapter.getFilter().filter(newText);
                return false;
            }
        });

        Log.d(TAG, "onCreateView Called");
        return view;
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
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void parseData(JSONArray jsonArray){
        try {
            JSONObject jsonObject;
            for (int i = 0; i <NUMBER_OF_CURRENCY; i++) {
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

            mCurrencyItemAdapter.notifyDataSetChanged();
            Log.d(TAG, "parseData Called");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
