package info.lansachia.cryptoinvest;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by user on 3/1/2018.
 */

public class CurrencyItemAdapter extends RecyclerView.Adapter<CurrencyItemAdapter.MyViewHolder> implements Filterable {
     List<CurrencyItem> mCurrencyItems, mCurrencyFilterList;
    private Context mContext;
    private CurrencyFilter mCurrencyFilter;



    //implementing on click listener to each item of the recycler view

    /**
     * Creating an interface called onRecyclerviewItemClickListener
     */
    public interface RecyclerItemClickListener
    {
        //called when an item within recycler view is clicked

        /**
         *
         * @param view the position of the item
         * @param position the id the view which is clicked within the item or -1 if the item itself is clicked
         */
        public void reCyclerClickItemListener(View view, int position, boolean isLongClick);
    }


    public CurrencyItemAdapter(Context context, List<CurrencyItem> currencyItems){
        this.mContext = context;
        this.mCurrencyItems = currencyItems;
        this.mCurrencyFilterList = currencyItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        CurrencyItem currencyItem = mCurrencyItems.get(position);
        holder.bind(currencyItem);
        holder.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void reCyclerClickItemListener(View view, int position, boolean isLongClick) {
                if (isLongClick){

                    Snackbar.make(view, mCurrencyItems.get(position).getCurrencyName()+" LongClick", Snackbar.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(mContext,"#" + position + "-" +mCurrencyItems.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("CryptoInvest", String.valueOf(mCurrencyItems.size()));
        return mCurrencyItems.size();

    }

    //return the filter object
    @Override
    public Filter getFilter() {
        if (mCurrencyFilter == null){
            mCurrencyFilter = new CurrencyFilter(mCurrencyFilterList, this);
        }
        return mCurrencyFilter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView mSymbol;
        TextView mPrice;
        TextView mName;
        TextView mRecommendation;
        ImageView mCurrencyLogo;
        String mImageName;
        ConstraintLayout parentView;


        private RecyclerItemClickListener mRecyclerItemClickListener;


        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_currency, parent, false));

            mSymbol = (TextView)itemView.findViewById(R.id.currency_symbol);
            mPrice = (TextView)itemView.findViewById(R.id.currency_price);
            mName = (TextView)itemView.findViewById(R.id.currency_name);
            mRecommendation = (TextView)itemView.findViewById(R.id.currency_recommendation);
            mCurrencyLogo= (ImageView)itemView.findViewById(R.id.currency_icon);
            parentView =(ConstraintLayout) itemView.findViewById(R.id.parent_view_recycler_view);


            parentView.setOnClickListener(this);
            parentView.setOnLongClickListener(this);
        }

        public void bind(CurrencyItem currencyItem){

            //setting image logo
            mImageName = "drawable/" + currencyItem.getCurrencyLogo();
            int imageResource = mContext.getResources().getIdentifier(mImageName, null, mContext.getPackageName());
//            Drawable image = ContextCompat.getDrawable(mContext, imageResource);
            mCurrencyLogo.setImageResource(imageResource);

            //setting text to currency symbol
            mSymbol.setText(currencyItem.getSymbol());
            mSymbol.setTextSize(25);

            //currency name
            mName.setText(currencyItem.getCurrencyName());
            mName.setTextSize(20);

            //currency prize
            mPrice.setText(currencyItem.getPrice());
            mPrice.setTextSize(23);

            //recommendation

            if (currencyItem.getChangePerHourPercent().contains("-")){
                mRecommendation.setText( currencyItem.getChangePerHourPercent());
                mRecommendation.setTextSize(19);
                mRecommendation.setTextColor(ContextCompat.getColor(mContext,R.color.color_of_bad_percent));
            }else {
                mRecommendation.setText("+ " + currencyItem.getChangePerHourPercent());
                mRecommendation.setTextSize(19);
                mRecommendation.setTextColor(ContextCompat.getColor(mContext, R.color.color_of_good_percent));
            }

        }

        public void  setClickListener(RecyclerItemClickListener recyclerItemClickListener){
                this.mRecyclerItemClickListener = recyclerItemClickListener;
        }

        @Override
        public void onClick(View view) {
            mRecyclerItemClickListener.reCyclerClickItemListener(view, getLayoutPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            mRecyclerItemClickListener.reCyclerClickItemListener(view, getLayoutPosition(), true);
            return true;
        }
    }


}
