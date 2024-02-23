package com.sudo_pacman.asaxiybooks.domain.impl

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sudo_pacman.asaxiybooks.data.model.AddUserData
import com.sudo_pacman.asaxiybooks.data.source.MySharedPreference
import com.sudo_pacman.asaxiybooks.domain.LoginRepository
import com.sudo_pacman.asaxiybooks.utils.Mapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor() : LoginRepository {
    private val fireStore = Firebase.firestore

    override fun loginUser(password: String, gmail: String): Flow<Result<Boolean>> = callbackFlow {
        fireStore.collection("users").whereEqualTo("password", password).whereEqualTo("gmail", gmail).limit(1).get().addOnSuccessListener {
            if (it.size() == 0) {
                trySend(Result.failure(Throwable("Bunaqa user mavjud emas !")))
            } else {
                val user = Mapper.run { it.toUserDataList()[0] }
                MySharedPreference.setUserData(user)
                trySend(Result.success(true))
                channel.close()
            }
        }.addOnFailureListener {
            trySend(Result.success(false))
            channel.close()
        }
        awaitClose()
    }

    override fun registerUser(name: String, gmail: String, password: String): Flow<Result<Unit>> = callbackFlow {

        fireStore
            .collection("users")
            .add(AddUserData(name, gmail, password)).addOnSuccessListener {
                trySend(Result.success(Unit))
            }.addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }


}