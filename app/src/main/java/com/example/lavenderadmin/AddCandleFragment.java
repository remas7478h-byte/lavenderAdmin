package com.example.lavenderadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddCandleFragment extends Fragment {

    private ImageView imgCandle;
    private EditText etCandleName, etCandlePrice, etCandleDescription;
    private Button btnAddCandle;
    private Uri imageUri;

    // فايربيس
    private FirebaseFirestore db;
    private StorageReference storageRef;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        // 1. اختيار الصورة من الجهاز
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == -1 && result.getData() != null) {
                        imageUri = result.getData().getData();
                        imgCandle.setImageURI(imageUri);
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_candle, container, false);

        imgCandle = view.findViewById(R.id.imgCandle);
        etCandleName = view.findViewById(R.id.etCandleName);
        etCandlePrice = view.findViewById(R.id.etCandlePrice);
        etCandleDescription = view.findViewById(R.id.etCandleDescription);
        btnAddCandle = view.findViewById(R.id.btnAddCandle);

        // عند الضغط على مكان الصورة لفتح المعرض
        imgCandle.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        // عند الضغط على زر ADD للحفظ
        btnAddCandle.setOnClickListener(v -> uploadImageAndSaveData());

        return view;
    }

    private void uploadImageAndSaveData() {
        String name = etCandleName.getText().toString().trim();
        String price = etCandlePrice.getText().toString().trim();
        String description = etCandleDescription.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || imageUri == null) {
            Toast.makeText(getContext(), "Please fill all fields and pick an image", Toast.LENGTH_SHORT).show();
            return;
        }

        btnAddCandle.setEnabled(false);

        // إنشاء مستند جديد في الفايرستور لأخذ ID تلقائي (مثل ما هو موضح بالصورة)
        DocumentReference newDocRef = db.collection("Candles").document();
        String docId = newDocRef.getId();

        // Step 1: رفع الصورة إلى Storage وتسميتها بالـ ID
        StorageReference fileRef = storageRef.child("logos/" + docId + ".jpg");

        fileRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    // Step 3: حفظ البيانات في Firestore
                    Candle candle = new Candle(docId, name, Double.parseDouble(price), description, imageUrl);

                    newDocRef.set(candle)
                            .addOnSuccessListener(unused -> {
                                // Step 4: نجاح العملية
                                Toast.makeText(getContext(), "Candle added successfully!", Toast.LENGTH_SHORT).show();
                                btnAddCandle.setEnabled(true);
                                // هنا يمكنك الانتقال لشاشة العرض لاحقاً
                            })
                            .addOnFailureListener(e -> showError(e));
                }))
                .addOnFailureListener(e -> showError(e));
    }

    private void showError(Exception e) {
        if (!isAdded()) return;
        btnAddCandle.setEnabled(true);
        Toast.makeText(getContext(), "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}