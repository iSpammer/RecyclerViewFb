package ispam.markt17.recyclerviewfb;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Places");
        arrayList.add("asd");
        arrayList.add("asd");
        arrayList.add("asd");
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
}
