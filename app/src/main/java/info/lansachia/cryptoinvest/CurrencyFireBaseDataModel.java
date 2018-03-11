package info.lansachia.cryptoinvest;

/**
 * Created by user on 3/9/2018.
 */

public class CurrencyFireBaseDataModel {
    String currencyId;
    String currencyPrice;
    String userSettingCurrencyValue;
    String userSettingCurrencyName;

    public CurrencyFireBaseDataModel(){
        //required constructor for serialization
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public String getUserSettingCurrencyValue() {
        return userSettingCurrencyValue;
    }

    public void setUserSettingCurrencyValue(String userSettingCurrencyValue) {
        this.userSettingCurrencyValue = userSettingCurrencyValue;
    }

    public String getUserSettingCurrencyName() {
        return userSettingCurrencyName;
    }

    public void setUserSettingCurrencyName(String userSettingCurrencyName) {
        this.userSettingCurrencyName = userSettingCurrencyName;
    }
}
