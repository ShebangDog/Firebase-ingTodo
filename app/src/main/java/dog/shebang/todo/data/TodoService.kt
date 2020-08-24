package dog.shebang.todo.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dog.shebang.todo.data.model.Todo

object TodoService {
    fun insertTodo(firestore: FirebaseFirestore, uid: String, todo: Todo) {
        firestore.usersRef().document(uid).set(todo)
    }

    fun fetchTodo(firestore: FirebaseFirestore, uid: String) {
        firestore.usersRef().document(uid).get().addOnSuccessListener { document ->
            Log.d(TAG, "todo ${document.data}")
        }
    }

    private fun FirebaseFirestore.usersRef(): CollectionReference {
        return this.collection("users")
    }

}