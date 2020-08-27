package dog.shebang.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dog.shebang.todo.data.TodoService
import dog.shebang.todo.data.UserService
import dog.shebang.todo.data.model.Todo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val firestore: FirebaseFirestore by lazy { Firebase.firestore }
    private val firebaseAuth: FirebaseAuth by lazy { Firebase.auth }

    private val mutableCurrentUser: MutableLiveData<FirebaseUser> = MutableLiveData()
    val currentUser: LiveData<FirebaseUser>
        get() = mutableCurrentUser

    //val todo = currentUser.value uid.switchMap { TodoService.fetchTodo(firestore, it).asLiveData() }

    @ExperimentalCoroutinesApi
    private val mutableTodo: MutableLiveData<Todo> = MutableLiveData()

    @ExperimentalCoroutinesApi
    val todo: LiveData<Todo>
        get() = mutableTodo

    fun onStart() {
        mutableCurrentUser.value = firebaseAuth.currentUser
    }

    fun onClickFAB(todo: Todo) = viewModelScope.launch {
        mutableCurrentUser.value?.also { TodoService.insertTodo(firestore, it.uid, todo) }
    }

    fun onClickSignIn(email: String, password: String) = viewModelScope.launch {
        UserService.loginUser(firebaseAuth, email, password)
    }

    fun onClickSignUp(email: String, password: String) = viewModelScope.launch {
        UserService.createUser(firebaseAuth, email, password)
    }

    @ExperimentalCoroutinesApi
    fun reload() = currentUser.value?.uid?.let { TodoService.fetchTodo(firestore, it) }
}