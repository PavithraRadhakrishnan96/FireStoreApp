package com.example.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var auth: FirebaseAuth
    protected var firestore = FirebaseFirestore.getInstance()

   /*Initializing firestore db*/
    var db = Firebase.firestore

/*To check current user is logged in. otherwise
 it shows Login screen to register or login*/
    protected val currentUser: FirebaseUser?
        get() = auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*isPersistenceEnabled - To avail the offline feature we need to setup as enable true
         otherwise it will not work in offline
         cacheSizeBytes - If we are not setting DEFAULT_CACHE_SIZE_BYTES will apply it maintain upto 100MB
          Cloud Firestore periodically attempts to clean up older, unused documents.
          You can configure a different cache size threshold or disable the clean-up process completely:*/
        val settings = firestoreSettings {
            isPersistenceEnabled = true
            cacheSizeBytes=FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
        }
        db.firestoreSettings = settings

        /*For Firebase authendication*/
        auth = FirebaseAuth.getInstance()
    }
}