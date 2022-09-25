package com.siakang.tukang.core.domain.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.storage.UploadTask
import com.siakang.tukang.core.domain.model.*

interface UserRepository {
    suspend fun createUser(user: Tukang): Task<Void>
    suspend fun getUser(userId: String): Task<DocumentSnapshot>
    suspend fun updatePersonalInformation(userId: String, personalInformationTukang: PersonalInformationTukang): Task<Void>
    suspend fun updateFile(userId: String, fileTukang: FileTukang): Task<Void>
    suspend fun updateSkill(userId: String, skillTukang: SkillTukang): Task<Void>
    suspend fun updateBank(userId: String, bankTukang: BankTukang): Task<Void>
    suspend fun storeFile(path: String, uri: Uri): UploadTask
}