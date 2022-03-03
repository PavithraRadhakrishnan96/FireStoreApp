package com.example.firestore

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.auth.FirebaseUser




class SignInActivity : BaseActivity(), OnCompleteListener<AuthResult> {
    lateinit var login_button: Button
    lateinit var register_button: Button
    lateinit var email_edit_text: EditText
    lateinit var password_edit_text: EditText
    lateinit var roles_edit_text: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        login_button = findViewById(R.id.login_button)
        register_button = findViewById(R.id.register_button)
        email_edit_text = findViewById(R.id.email_edit_text)
        password_edit_text = findViewById(R.id.password_edit_text)
        roles_edit_text = findViewById(R.id.roles_edit_text)

        login_button.setOnClickListener {
            auth.signInWithEmailAndPassword(
                email_edit_text.text.toString(),
                password_edit_text.text.toString()
            ).addOnCompleteListener(this)
        }

        register_button.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                email_edit_text.text.toString(),
                password_edit_text.text.toString()
            ).addOnCompleteListener(this)

        }

    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            QuerySampleActivity.newInstance(this).let(::startActivity)
            finish()
        } else {
            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
        }

    }
    private fun DocumentReference.addNewUser(uid: String) {
        val userMap = HashMap<String, MutableList<String>>()
        userMap["users"] = mutableListOf(uid)
        set(userMap).addCompleteListener()
    }

    private fun DocumentReference.updateArray(uid: String) {
        update("users", FieldValue.arrayUnion(uid)).addCompleteListener()
    }

    private fun Task<Void>.addCompleteListener() {
        addOnCompleteListener {
            QuerySampleActivity.newInstance(this@SignInActivity).let(::startActivity)
            finish()
        }.addOnFailureListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(baseContext, "Error saving user", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if(currentUser !=null){
            QuerySampleActivity.newInstance(this@SignInActivity).let(::startActivity)
            finish()
        }

    }
}