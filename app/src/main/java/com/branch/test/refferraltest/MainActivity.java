package com.branch.test.refferraltest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.logging.Logger;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity {

    Branch branch = Branch.getInstance();
    SharedPreferences sharedpreferences;
    String key;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
   // Button btnShare;
    Button btnShareEarn;
    TextView txtReferral;
    TextView txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_earn);

       // final TextView textView = (TextView) findViewById(R.id.text);
     //   btnShare = (Button)findViewById(R.id.btnShare);
        btnShareEarn = (Button)findViewById(R.id.btnShareEarn);
        txtReferral = (TextView)findViewById(R.id.txtReferral);
        txtCode = (TextView)findViewById(R.id.txtCode);


        btnShareEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_VIEW);
               // shareIntent.setType("text/plain");
                  shareIntent.setData(Uri.parse("https://bnc.lt/referralcode"));
                //shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,Uri.parse("https://bnc.lt/referralcode"));
                startActivity(Intent.createChooser(shareIntent, "Share link using"));
            }
        });

        branch.initSession(new Branch.BranchReferralInitListener(){
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Toast.makeText(MainActivity.this,"got params ", Toast.LENGTH_LONG).show();
                    Log.i("ReferringParams" ,referringParams.toString());
                    try {
                        key = referringParams.getString("key");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    txtReferral.setText("Referral code : " + key);

                   // txtCode.setVisibility(View.GONE);
                   // btnShareEarn.setVisibility(View.GONE);

                } else {
                    Log.i("MyApp", error.getMessage());
                    Toast.makeText(MainActivity.this,"got params error : "+error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Lifecycle callback method
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
