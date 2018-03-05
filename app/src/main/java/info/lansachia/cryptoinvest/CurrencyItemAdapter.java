package info.lansachia.cryptoinvest;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 3/1/2018.
 */

public class CurrencyItemAdapter extends RecyclerView.Adapter<CurrencyItemAdapter.MyViewHolder> {
    private List<CurrencyItem> mCurrencyItems;
    private Context mContext;

    public CurrencyItemAdapter(Context context, List<CurrencyItem> currencyItems){
        this.mContext = context;
        this.mCurrencyItems = currencyItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        return new MyViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CurrencyItem currencyItem = mCurrencyItems.get(position);
        holder.bind(currencyItem);

        //logo
//        holder.mCurrencyLogo.setVisibility(View.VISIBLE);

        //using Glide library to push logo to image view
//        Glide.with(mContext).load(currencyItem.getCurrencyLogo()).into(holder.mCurrencyLogo);

    }

    @Override
    public int getItemCount() {
        return mCurrencyItems.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mSymbol;
        TextView mPrice;
        TextView mName;
        TextView mRecommendation;
        ImageView mCurrencyLogo;
        String mImageName;


        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_currency, parent, false));


            mSymbol = (TextView)itemView.findViewById(R.id.currency_symbol);
            mPrice = (TextView)itemView.findViewById(R.id.currency_price);
            mName = (TextView)itemView.findViewById(R.id.currency_name);
            mRecommendation = (TextView)itemView.findViewById(R.id.currency_recommendation);
            mCurrencyLogo= (ImageView)itemView.findViewById(R.id.currency_icon);
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

        @Override
        public void onClick(View view) {

        }
    }


}
