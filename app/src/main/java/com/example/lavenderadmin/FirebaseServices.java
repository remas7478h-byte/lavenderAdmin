package com.example.lavenderadmin;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class FirebaseServices
{
        private static FirebaseServices instance;

        private final FirebaseAuth auth;
        private final FirebaseFirestore db;
        private final FirebaseStorage storage;

        // الـ Constructor الخاص من نوع private
        private FirebaseServices() {
            auth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            storage = FirebaseStorage.getInstance();
        }

        // الدالة العامة لجلب النسخة (Lazy Singleton)
        public static synchronized FirebaseServices getInstance() {
            if (instance == null) {
                instance = new FirebaseServices();
            }
            return instance;
        }

        // دوال للوصول إلى الخدمات
        public FirebaseAuth getAuth() {
            return auth;
        }

        public FirebaseFirestore getDb() {
            return db;
        }

        public FirebaseStorage getStorage() {
            return storage;
        }
    }


