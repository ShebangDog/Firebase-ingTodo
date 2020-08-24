package dog.shebang.todo.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

object UserService {
    fun createUser(firebaseAuth: FirebaseAuth, email: String, passWord: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                val user = firebaseAuth.currentUser

            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
            }
        }
    }

    fun loginUser(firebaseAuth: FirebaseAuth, email: String, passWord: String) {
        firebaseAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                val user = firebaseAuth.currentUser
            } else {
                Log.d(TAG, "signInWithEmail:failure", task.exception)
            }
        }
    }
}