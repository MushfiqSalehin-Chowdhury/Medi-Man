package com.mediman.testmediman;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //Android Layout
    private CircleImageView mDisplayImage;
    private TextView mName;
    private Button searchBtn, profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = (CircleImageView) findViewById(R.id.setting_image);
        mName = (TextView) findViewById(R.id.textView);
        searchBtn = (Button) findViewById(R.id.settings_search_btn);
        profileBtn = (Button) findViewById(R.id.setting_profile_btn);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Toast.makeText(SettingsActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                String name = dataSnapshot.child("name").getValue().toString();
                //String image = dataSnapshot.child("image").getValue().toString();
                //String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void gotoSearch(View view) {
        Intent intent = new Intent(SettingsActivity.this, SearchActivity.class);
        startActivity(intent);
    }
}
