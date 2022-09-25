package com.siakang.tukang.core.data.interactors

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.*
import com.siakang.tukang.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class UserInteractors(
    private val userRepository: UserRepository
): UserUseCase {
    override suspend fun createUser(user: Tukang): Flow<Resource<Tukang>> {
        return callbackFlow {
            userRepository.createUser(user).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(user)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUser(userId: String): Flow<Resource<Tukang?>> {
        return callbackFlow<Resource<Tukang?>> {
            userRepository.getUser(userId).addOnCompleteListener { task: Task<DocumentSnapshot> ->
                trySend(
                    if(task.isSuccessful) {
                        val user = task.result.toObject(Tukang::class.java)
                        if(user != null) {
                            Resource.Success(user)
                        }
                        else {
                            Resource.Failure("Ups, user tidak ada!")
                        }
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updatePersonalInformation(
        userId: String,
        personalInformationTukang: PersonalInformationTukang
    ): Flow<Resource<PersonalInformationTukang>> {
        return callbackFlow<Resource<PersonalInformationTukang>> {
            userRepository.updatePersonalInformation(userId, personalInformationTukang).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(personalInformationTukang)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateFile(
        userId: String,
        fileTukang: FileTukang
    ): Flow<Resource<FileTukang>> {
        return callbackFlow<Resource<FileTukang>> {
            userRepository.updateFile(userId, fileTukang).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(fileTukang)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateSkill(
        userId: String,
        skillTukang: SkillTukang
    ): Flow<Resource<SkillTukang>> {
        return callbackFlow<Resource<SkillTukang>> {
            userRepository.updateSkill(userId, skillTukang).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(skillTukang)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateBank(
        userId: String,
        bankTukang: BankTukang
    ): Flow<Resource<BankTukang>> {
        return callbackFlow<Resource<BankTukang>> {
            userRepository.updateBank(userId, bankTukang).addOnCompleteListener { task ->
                trySend(
                    if(task.isSuccessful) {
                        Resource.Success(bankTukang)
                    }
                    else {
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    }
                )
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun storeFile(path: String, uri: Uri): Flow<Resource<String>> {
        return callbackFlow {
            userRepository.storeFile(path, uri).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    task.result.storage.downloadUrl.addOnCompleteListener { taskUri ->
                        trySend(
                            if(taskUri.isSuccessful) {
                                Resource.Success(taskUri.result.toString())
                            }
                            else {
                                Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                            }
                        )
                    }
                }
                else {
                    trySend(
                        Resource.Failure(task.exception?.localizedMessage ?: "Ups, something error!")
                    )
                }
            }
            awaitClose()
        }.flowOn(Dispatchers.IO)
    }


}