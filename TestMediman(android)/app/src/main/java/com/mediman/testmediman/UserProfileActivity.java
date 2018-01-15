package com.mediman.testmediman;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private CircleImageView mProfileImage;
    private Button sendReqBtn, declineReqBtn;
    private TextView  mProfileName;

    //database
    private DatabaseReference mUserDatabase;

    private ProgressDialog mProgressDialog;
    private String mCurrent_state;

    private DatabaseReference mFriendRequestDatabase, mFriendDatabase;
    private FirebaseUser mCurrent_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final String user_id = getIntent().getStringExtra("user_id");

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mFriendRequestDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friends");
        mCurrent_user = FirebaseAuth.getInstance().getCurrentUser();

        //mDisplayID = (TextView) findViewById(R.id.profile_displayName);
        //mDisplayID.setText(user_id);
        //mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        sendReqBtn = (Button) findViewById(R.id.profile_send_req_btn);
        declineReqBtn = (Button) findViewById(R.id.profile_decline_req_btn);
        mProfileImage = (CircleImageView) findViewById(R.id.profileImage);

        mCurrent_state = "not_friends";

        mProgressDialog = new ProgressDialog(UserProfileActivity.this);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();



        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String display_name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mProfileName.setText(display_name);


                //Picasso.with(UserProfileActivity.this).load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);
                Glide.with(UserProfileActivity.this).load(image).asBitmap().placeholder(R.drawable.default_avatar).into(mProfileImage);

                mFriendRequestDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(user_id)){
                            String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();
                            if(req_type.equals("recieved")){

                                mCurrent_state = "req_recieved";
                                sendReqBtn.setText("Accept Request");
                            } else if (req_type.equals("sent")){
                                mCurrent_state = "req_sent";
                                sendReqBtn.setText("Cancel Request");

                            }
                        }
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //mProgressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReqBtn.setEnabled(false);

                //Not Friends

                if(mCurrent_state.equals("not_friends")){

                    mFriendRequestDatabase.child(mCurrent_user.getUid()).child(user_id).child("request_type")
                            .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mFriendRequestDatabase.child(user_id).child(mCurrent_user.getUid()).child("request_type")
                                        .setValue("recieved").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //sendReqBtn.setEnabled(true);
                                        mCurrent_state = "req_sent";
                                        sendReqBtn.setText("Cancel Request");

                                        //Toast.makeText(UserProfileActivity.this, "Successfully Sent Request" + mCurrent_state, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(UserProfileActivity.this, "Failed Sending Request",Toast.LENGTH_SHORT).show();
                            }
                            sendReqBtn.setEnabled(true);
                        }
                    });
                }

                //Cancel Request
                if(mCurrent_state.equals("req_sent")){
                    mFriendRequestDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendRequestDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    sendReqBtn.setEnabled(true);
                                    mCurrent_state = "not_friends";
                                    sendReqBtn.setText("Send Request");
                                }
                            });
                        }
                    });
                }

                //Req Recieved

                if(mCurrent_state.equals("req_recieved")){

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    mFriendDatabase.child(mCurrent_user.getUid()).child(user_id).setValue(currentDate)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mFriendDatabase.child(user_id).child(mCurrent_user.getUid()).setValue(currentDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mFriendRequestDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            mFriendRequestDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    sendReqBtn.setEnabled(true);
                                                    mCurrent_state = "friends";
                                                    sendReqBtn.setText("Block Request");
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}
