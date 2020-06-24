package in.amiete.currencyselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder>  {
    List<String> arrCountry,arrCurrency;
    Context context;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(List<String> arrData, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public CurrencyAdapter(List<String> arrCountry , List<String> arrCurrency) {
        this.arrCountry = arrCountry;
        this.arrCurrency = arrCurrency;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.binding_currency, parent, false);
        ViewHolder evh = new ViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String symbol;
        String country = arrCountry.get(position);
        try{
            symbol = "("+Utils.getCurrencySymbol(arrCurrency.get(position))+")";
        } catch (Exception e ){
            symbol = "";
        }
        holder.countryName.setText(country+" "+symbol);
    }

    @Override
    public int getItemCount() {
        return arrCountry.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            countryName = itemView.findViewById(R.id.countryName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(arrCountry,position);
                        }
                    }
                }
            });
        }
    }

    static class Utils {
        public static SortedMap<Currency, Locale> currencyLocaleMap;

        static {
            currencyLocaleMap = new TreeMap<Currency, Locale>(new Comparator<Currency>() {
                public int compare(Currency c1, Currency c2) {
                    return c1.getCurrencyCode().compareTo(c2.getCurrencyCode());
                }
            });
            for (Locale locale : Locale.getAvailableLocales()) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    currencyLocaleMap.put(currency, locale);
                } catch (Exception e) {
                }
            }
        }

        public static String getCurrencySymbol(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }

        public static String getCurrencyCode(String currencyCode) {
            Currency currency = Currency.getInstance(currencyCode);
            System.out.println(currencyCode + ":-" + currency.getSymbol(currencyLocaleMap.get(currency)));
            return currency.getSymbol(currencyLocaleMap.get(currency));
        }

    }
}
