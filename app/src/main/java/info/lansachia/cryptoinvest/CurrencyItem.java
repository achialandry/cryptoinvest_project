package info.lansachia.cryptoinvest;

/**
 * Created by user on 3/1/2018.
 */

public class CurrencyItem {

    //currency id for logo
    private  String currencyLogo;



    //sets name of currency
    private String mCurrencyName;

    //currency price
    private  String mPrice;

    //currency symbol
    private String mSymbol;

    //currency 24 hr volume
    private String m24Hvolume;

    //total supply of currency
    private String mTotalSupply;

    //available supply of currency
    private String mAvailableSupply;

    //change per 1 hour of currency
    private String mChangePerHourPercent;

    //Change per day  24hrs of currency in percentage
    private String mChangePerDayPercent;

    //change per 1 week (7weeks) of Currency in percent
    private  String mChangePerWeekPercent;

    //currency ranking:
    private String mCurrencyRanking;

    //currency market Cap
    private String mCurrencyMarketCap;

    public CurrencyItem(){

    }

    public String getCurrencyLogo() {
        return currencyLogo;
    }

    public void setCurrencyLogo(String currencyLogo) {
        if(currencyLogo.contains("-")){
            this.currencyLogo = currencyLogo.replace("-", "_");
        }else{
            this.currencyLogo = currencyLogo;
        }

    }

    public String getCurrencyName() {
        return mCurrencyName;
    }

    public void setCurrencyName(String currencyName) {
        mCurrencyName = currencyName;
    }

    public String getPrice() {
        return "$"+mPrice ;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String symbol) {
        mSymbol = symbol;
    }

    public String getM24Hvolume() {
        return m24Hvolume;
    }

    public void setM24Hvolume(String m24Hvolume) {
        this.m24Hvolume = m24Hvolume;
    }

    public String getTotalSupply() {
        return mTotalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        mTotalSupply = totalSupply;
    }

    public String getAvailableSupply() {
        return mAvailableSupply;
    }

    public void setAvailableSupply(String availableSupply) {
        mAvailableSupply = availableSupply;
    }

    public String getChangePerHourPercent() {
        return mChangePerHourPercent + "%";
    }

    public void setChangePerHourPercent(String changePerHourPercent) {
        mChangePerHourPercent = changePerHourPercent;
    }

    public String getChangePerDayPercent() {
        return mChangePerDayPercent;
    }

    public void setChangePerDayPercent(String changePerDayPercent) {
        mChangePerDayPercent = changePerDayPercent;
    }

    public String getChangePerWeekPercent() {
        return mChangePerWeekPercent;
    }

    public void setChangePerWeekPercent(String changePerWeekPercent) {
        mChangePerWeekPercent = changePerWeekPercent;
    }

    public String getCurrencyRanking() {
        return mCurrencyRanking;
    }

    public void setCurrencyRanking(String currencyRanking) {
        mCurrencyRanking = currencyRanking;
    }

    public String getCurrencyMarketCap() {
        return mCurrencyMarketCap;
    }

    public void setCurrencyMarketCap(String currencyMarketCap) {
        mCurrencyMarketCap = currencyMarketCap;
    }
}
