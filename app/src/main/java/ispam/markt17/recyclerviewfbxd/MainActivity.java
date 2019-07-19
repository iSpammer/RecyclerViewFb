package ispam.markt17.recyclerviewfbxd;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        final ArrayList<String> arrayList = new ArrayList<>();
        // Read from the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Places");
        arrayList.add("asd");
        arrayList.add("asd");
        arrayList.add("asd");
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref.child("Cairo").child("Cairo Festival City Mall").child("PlaceID").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayList.add(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayList.add(dataSnapshot.getValue(String.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    arrayList.add(key);
                    adapter.notifyDataSetChanged();
                    System.out.println(key+" aaaaaaaaaa");
                    System.out.println(Arrays.toString(arrayList.toArray()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ASD", "Failed to read value.", databaseError.toException());

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        sendFcmRegistrationToken();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void sendFcmRegistrationToken() {
        Intent intent = new Intent(this, FcmTokenRegistrationService.class);
        startService(intent);
    }

    private void checkGoogleApiAvailability() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (resultCode == ConnectionResult.SUCCESS) {
                Log.d("FCM", "GoogleApi is available");
            } else {
                apiAvailability.getErrorDialog(this, resultCode, 1).show();
            }
        }
    }
}
