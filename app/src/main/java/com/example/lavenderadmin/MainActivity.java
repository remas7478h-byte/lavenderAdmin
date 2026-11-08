package com.example.lavenderadmin;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);   // تأكدي إن ID الزر عندك مطابق لملف الـ XML
        btnShow = findViewById(R.id.btnShow); // تأكدي إن ID الزر عندك مطابق لملف الـ XML

        // عند فتح التطبيق افتراضياً بنقدر نعرض شاشة الإضافة أو نخليها فاضية
        // أو إذا بدك أول ما يفتح يروح للشاشة الرئيسية

        // زر الانتقال لشاشة الإضافة (إذا كان موجود عندك مسبقاً)
        btnAdd.setOnClickListener(v -> {
            // استبدلي AddCandleFragment باسم الكلاس الخاص بشاشة الإضافة عندك إذا كان مختلفاً
            showFragment(new AddCandleFragment());
        });

        // زر الانتقال لشاشة العرض اللي عملناها هسا
        btnShow.setOnClickListener(v -> {
            showFragment(new ShowCandlesFragment());
        });
    }

    // دالة مساعدة لتبديل الفراجمنت بسلاسة
    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // تأكدي أن الـ ID للـ FrameLayout في activity_main.xml هو نفسه
        transaction.commit();
    }
}