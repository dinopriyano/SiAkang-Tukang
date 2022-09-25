package com.siakang.tukang.core.domain.usecase

import android.net.Uri
import com.google.firebase.storage.UploadTask
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    suspend fun createUser(user: Tukang): Flow<Resource<Tukang>>
    suspend fun getUser(userId: String): Flow<Resource<Tukang?>>
    suspend fun updatePersonalInformation(userId: String, personalInformationTukang: PersonalInformationTukang): Flow<Resource<PersonalInformationTukang>>
    suspend fun updateFile(userId: String, fileTukang: FileTukang): Flow<Resource<FileTukang>>
    suspend fun updateSkill(userId: String, skillTukang: SkillTukang): Flow<Resource<SkillTukang>>
    suspend fun updateBank(userId: String, bankTukang: BankTukang): Flow<Resource<BankTukang>>
    suspend fun storeFile(path: String, uri: Uri): Flow<Resource<String>>
}