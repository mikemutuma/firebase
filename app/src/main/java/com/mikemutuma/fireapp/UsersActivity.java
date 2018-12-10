package com.mikemutuma.fireapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class UsersActivity extends AppCompatActivity {
    ListView list;
    CustomAdapter adapter;
    ArrayList<Item> users;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        list = findViewById(R.id.listppl);
        users = new ArrayList<>();
        adapter = new CustomAdapter(this,users);
        list.setAdapter(adapter);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Watu");
        dialog.show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Item user = snap.getValue(Item.class);
                    users.add(user);
                    Collections.reverse(users);
                    dialog.dismiss();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UsersActivity.this, "Sorry, System Locked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
