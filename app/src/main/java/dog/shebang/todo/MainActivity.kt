package dog.shebang.todo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dog.shebang.todo.data.TodoService
import dog.shebang.todo.data.UserService
import dog.shebang.todo.data.model.Todo
import dog.shebang.todo.databinding.ActivityMainBinding
import android.content.ContentValues.TAG

class MainActivity : AppCompatActivity() {
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }

    private lateinit var binding: ActivityMainBinding

    private var currentUser: FirebaseUser? = null

    override fun onStart() {
        super.onStart()

        currentUser = firebaseAuth.currentUser ?: return
        Log.d(TAG, "sign in ${currentUser?.email}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {
            floatingActionButton.setOnClickListener {
                currentUser?.uid?.also {
                    TodoService.insertTodo(firestore, it, Todo("lost princess", "first todo"))
                    TodoService.fetchTodo(firestore, it)
                }
            }

            signUpButton.setOnClickListener {
                UserService.createUser(
                    firebaseAuth,
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }

            signInButton.setOnClickListener {
                UserService.loginUser(
                    firebaseAuth,
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
    }

}