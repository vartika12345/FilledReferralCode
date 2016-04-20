package com.branch.test.refferraltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity {

    Branch branch = Branch.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.text);

        branch.initSession(new Branch.BranchReferralInitListener(){
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Toast.makeText(MainActivity.this,"got params ", Toast.LENGTH_LONG).show();
                    textView.setText("Referral code : " + referringParams.toString());

//                    Iterator<String> iter = referringParams.keys();
//                    while (iter.hasNext()) {
//                        String key = iter.next();
//                        if(key.equals("reffer_code")) {
//                            try {
//                                String value = referringParams.getString(key);
//                                textView.setText("Referral code : " + value);
//                            } catch (JSONException e) {
//                                // Something went wrong!
//                            }
//                        }
//                    }

                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
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
