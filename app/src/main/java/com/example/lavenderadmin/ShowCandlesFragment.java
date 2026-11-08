package com.example.lavenderadmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowCandlesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CandleAdapter adapter;
    private List<Candle> candleList;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_candles, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCandles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        candleList = new ArrayList<>();
        adapter = new CandleAdapter(getContext(), candleList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        // جلب البيانات من فايرستور وعرضها بالقائمة
        loadCandlesFromFirebase();

        return view;
    }

    private void loadCandlesFromFirebase() {
        db.collection("Candles")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    candleList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Candle candle = document.toObject(Candle.class);
                        candleList.add(candle);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}