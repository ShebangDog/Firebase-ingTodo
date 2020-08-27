package dog.shebang.todo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dog.shebang.todo.data.UserService
import dog.shebang.todo.data.model.Todo
import dog.shebang.todo.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onStart() {
        super.onStart()

//        viewModel.currentUser.observe(this) {
//            Log.d(TAG, "sign in ${it.email}")
//        }

        viewModel.onStart()
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {

//            viewModel.uid.observe(this@MainActivity) {
//                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT)
//            }

//            viewModel.todo.observe(this@MainActivity) {
//                todoContents.text = it.contents
//            }

            viewModel.reload()?.asLiveData()?.observe(this@MainActivity) {
                todoContents.text = it.contents
            }

            floatingActionButton.setOnClickListener {
                viewModel.onClickFAB(
                    Todo(emailEditText.text.toString(), passwordEditText.text.toString())
                )
            }

            signUpButton.setOnClickListener {
                viewModel.onClickSignUp(emailEditText.text.toString(), passwordEditText.text.toString())
            }

            signInButton.setOnClickListener {
                viewModel.onClickSignIn(emailEditText.text.toString(), passwordEditText.text.toString())
            }



        }
    }

}