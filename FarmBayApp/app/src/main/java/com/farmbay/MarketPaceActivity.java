package com.farmbay;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MarketPaceActivity extends AppCompatActivity {

    ListView cropListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_pace);
        cropListView=(ListView)findViewById(R.id.cropListView);

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        for (int i=0;i<CreateBidActivity.marketCropList.size();i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("name",CreateBidActivity.marketCropList.get(i).getCrop());
            //hashMap.put("qty",CreateBidActivity.marketCropList.get(i).getQuantity());
            String desc = "Quantity:"+CreateBidActivity.marketCropList.get(i).getQuantity()+"  City:"+CreateBidActivity.marketCropList.get(i).getCity() + "  Min Bid:"+CreateBidActivity.marketCropList.get(i).getMin_bid();
            hashMap.put("qty",desc);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        String[] from={"name","qty"};//string array
        int[] to={R.id.textView1,R.id.textView2};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.list_view,from,to);//Create object and set the parameters for simpleAdapter
        cropListView.setAdapter(simpleAdapter);//sets the adapter for listView

        //perform listView item click event
        cropListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(CreateBidActivity.marketCropList.get(i).getCrop());
                // Toast.makeText(getApplicationContext(),CreateBidActivity.marketCropList.get(i).getCrop(),Toast.LENGTH_LONG).show();//show the selected image in toast according to position
            }
        });
    }
    public void showDialog(String cropName){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MarketPaceActivity.this,R.style.BidDialogTheme);
        alertDialog.setTitle("Bid For Crop "+cropName);
        alertDialog.setMessage("Enter your bid Amount");

        final EditText input = new EditText(MarketPaceActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplication(), "Your bid has been placed", Toast.LENGTH_LONG).show();

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

}
