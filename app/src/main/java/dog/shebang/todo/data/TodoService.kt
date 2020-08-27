package dog.shebang.todo.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dog.shebang.todo.data.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

object TodoService {
    fun insertTodo(firestore: FirebaseFirestore, uid: String, todo: Todo) = flow {
        val postTodoRef = firestore.usersRef().document(uid).set(todo).await()

        emit(postTodoRef)
    }.flowOn(Dispatchers.IO)

    @ExperimentalCoroutinesApi
    fun fetchTodo(firestore: FirebaseFirestore, uid: String) = flow {
        val snapshot = firestore.usersRef().document(uid).get().await()
        val todo = snapshot.toObject(Todo::class.java) ?: return@flow

        emit(todo)
    }.flowOn(Dispatchers.IO)

    private fun FirebaseFirestore.usersRef(): CollectionReference {
        return this.collection("users")
    }

}