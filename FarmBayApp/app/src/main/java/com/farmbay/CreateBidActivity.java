package com.farmbay;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CreateBidActivity extends AppCompatActivity {

    EditText crop,quantity, city, state, min_bid;
    Button createBid;
    FirebaseDatabase database;
    DatabaseReference ref;
    public static List<CropData> marketCropList = new ArrayList<CropData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bid);
        crop = (EditText) findViewById(R.id.crop);
        quantity = (EditText) findViewById(R.id.quantity);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        min_bid = (EditText) findViewById(R.id.bid);
        createBid = (Button) findViewById(R.id.createBid);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("CropDatabase");

        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            CropData crop2 = new CropData();
            crop2.setCrop(crop.getText().toString());
            crop2.setQuantity(quantity.getText().toString());
            crop2.setCity(city.getText().toString());
            crop2.setState(state.getText().toString());
            crop2.setMin_bid(min_bid.getText().toString());

            ref.push().setValue(crop2);
          //  Toast.makeText(getApplication(), "Your crop is placed on market", Toast.LENGTH_LONG).show();
            showDialog(crop.getText().toString());
            }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CropData cropData = dataSnapshot.getValue(CropData.class);
                marketCropList.add(cropData);
                Log.d("Database", "Value is: " + cropData.getCrop());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void showDialog(String cropName){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateBidActivity.this,R.style.BidDialogTheme);
        alertDialog.setTitle("Message "+cropName);
        alertDialog.setMessage(cropName+"crop is placed on market for bidding");

        final EditText input = new EditText(CreateBidActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        crop.setText("");
                        quantity.setText("");
                        city.setText("");
                        state.setText("");
                        min_bid.setText("");
                    }
                });
        alertDialog.show();
    }
}
