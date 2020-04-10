package com.example.inclassassignment08_lingyiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    EditText key;
    EditText value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        key = (EditText)findViewById(R.id.key);
        value = (EditText)findViewById(R.id.value);
    }

    void uploadInfo(String key, String value, DatabaseReference parent){
        parent.child(key).setValue(value);
    }

    void readInfo(DatabaseReference key){
        key.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String data = dataSnapshot.getValue(String.class);
                    value.setText(data);
                }else{
                    value.setText("null");
                    Toast.makeText(MainActivity.this, "Cannot find the key", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error loading Firebase", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void writeToCloud(View view) {
        uploadInfo(key.getText().toString(), value.getText().toString(), database.getReference("Massage"));
    }

    public void readFromCloud(View view) {
        readInfo(database.getReference("Massage").child(key.getText().toString()));
    }
}
