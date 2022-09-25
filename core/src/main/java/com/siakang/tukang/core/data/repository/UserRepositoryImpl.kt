package com.siakang.tukang.core.data.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.siakang.tukang.core.domain.model.*
import com.siakang.tukang.core.domain.repository.UserRepository
import com.siakang.tukang.core.utils.ext.asMap

class UserRepositoryImpl(
    private val userRef: CollectionReference,
    private val storageReference: StorageReference
): UserRepository {
    override suspend fun createUser(user: Tukang): Task<Void> {
        return userRef.document(user.id).set(user)
    }

    override suspend fun getUser(userId: String): Task<DocumentSnapshot> {
        return userRef.document(userId).get()
    }

    override suspend fun updatePersonalInformation(
        userId: String,
        personalInformationTukang: PersonalInformationTukang
    ): Task<Void> {
        return userRef.document(userId).update(personalInformationTukang.asMap())
    }

    override suspend fun updateFile(userId: String, fileTukang: FileTukang): Task<Void> {
        return userRef.document(userId).update(fileTukang.asMap())
    }

    override suspend fun updateSkill(userId: String, skillTukang: SkillTukang): Task<Void> {
        return userRef.document(userId).update(skillTukang.asMap())
    }

    override suspend fun updateBank(userId: String, bankTukang: BankTukang): Task<Void> {
        return userRef.document(userId).update(bankTukang.asMap())
    }

    override suspend fun storeFile(path: String, uri: Uri): UploadTask {
        return storageReference.child(path).putFile(uri)
    }

}