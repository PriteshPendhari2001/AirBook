package com.example.airbook.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class authViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()


    sealed class AuthState {
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
        object Loading : AuthState()
        data class Erorr(val message : String) : AuthState()
    }

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        // Listen for changes in the authentication state
        checkAuthState()
        }

    private fun checkAuthState(){
        if (auth.currentUser ==  null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }


    fun SignUp(name : String,email: String,password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Erorr("Email And Password Can`t Be Empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful){
                    val user = Firebase.auth.currentUser

                    // First update the display name in Firebase Auth
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener {profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {
                                // After profile is updated, store additional data in Firestore
                                val userData = hashMapOf(
                                    "username" to name,
                                    "email" to email,
                                    "password" to password,
                                    "createdAt" to FieldValue.serverTimestamp()
                                )

                                Firebase.firestore.collection("users")
                                    .document(user.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        _authState.value = AuthState.Authenticated
                                    }
                            }
                        }

                    _authState.value = AuthState.Authenticated

                }else{
                    _authState.value =
                        AuthState.Erorr(task.exception?.message ?: "Something Went Wrong")

                }
            }
    }


    fun Login(email: String,password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Erorr("Email And Password Can`t Be Empty")
            return
        }

        _authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value =
                        AuthState.Erorr(task.exception?.message ?: "Something Went Wrong")
                }
            }
    }


    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}