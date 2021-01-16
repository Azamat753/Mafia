package com.lawlett.mafia.ui.home

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.lawlett.mafia.core.BaseViewModel

class HomeViewModel : BaseViewModel() {

    var isSuccess = MutableLiveData<Boolean>(false)

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun enterLobbi(lobbi : String,tokenID : String){
        db.collection(lobbi).document(tokenID).get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                var user: HashMap<String, Any> = HashMap()
                user.put("id", "tokenID")
                db.collection("lobbi1").document("tokenID").set(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        isSuccess.value = true
                    }
                }
            } else {
                var user: HashMap<String, Any> = HashMap()
                user.put("id", "tokenID")
                db.collection(lobbi).document(tokenID).set(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        isSuccess.value = true
                    }
                }
            }
        }
    }

}