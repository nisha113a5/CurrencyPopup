package in.amiete.currencyselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> arrCountry;
    List<String> arrCurrency;
    Button selectCurrency;
    TextView currentSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectCurrency = findViewById(R.id.selectCurrency);
        currentSelection = findViewById(R.id.currentSelection);
        arrCountry = Arrays.asList(getResources().getStringArray(R.array.countries_array));
        arrCurrency = Arrays.asList(getResources().getStringArray(R.array.currency_array));
//        To Select Country
//        currentSelection.setText(arrCountry.get(0));
        currentSelection.setText(arrCurrency.get(111));
        selectCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currencyPopUp();
            }
        });
    }


    public void currencyPopUp(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_currency_selection);
        RecyclerView mRecyclerView =  dialog.findViewById(R.id.currencyList);
        CurrencyAdapter mAdapter = new CurrencyAdapter(arrCountry,arrCurrency);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CurrencyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(List<String> arrData, int position) {
                currentSelection.setText(arrCurrency.get(position));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
