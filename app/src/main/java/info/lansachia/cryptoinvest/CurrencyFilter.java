package info.lansachia.cryptoinvest;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/7/2018.
 */

public class CurrencyFilter extends Filter {
    private CurrencyItemAdapter mCurrencyItemAdapter;
    private List<CurrencyItem> mCurrencyItemFilteredList;

    public CurrencyFilter(List<CurrencyItem> currencyItems, CurrencyItemAdapter currencyItemAdapter){
//        super();
        this.mCurrencyItemAdapter = currencyItemAdapter;
        this.mCurrencyItemFilteredList = currencyItems;
    }

    //perfoming filtering
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();

        //check constraint validity
        if (constraint !=null && constraint.length() > 0){
            //change to lower case
            constraint = constraint.toString().toLowerCase();

            //store the filtered currency
            List<CurrencyItem> filteredCurrencies = new ArrayList<>();

            for (int i = 0; i<mCurrencyItemFilteredList.size(); i++){
                //check if exist in list
                if (mCurrencyItemFilteredList.get(i).getCurrencyName().toLowerCase().contains(constraint)){
                    //add currency to filtered currencies
                    filteredCurrencies.add(mCurrencyItemFilteredList.get(i));
                }
            }
            filterResults.count = filteredCurrencies.size();
            filterResults.values = filteredCurrencies;
        }else {
            filterResults.count = mCurrencyItemFilteredList.size();
            filterResults.values = mCurrencyItemFilteredList;
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults filterResults) {
        mCurrencyItemAdapter.mCurrencyItems = (ArrayList<CurrencyItem>) filterResults.values;
        //refresh
        mCurrencyItemAdapter.notifyDataSetChanged();

    }
}
