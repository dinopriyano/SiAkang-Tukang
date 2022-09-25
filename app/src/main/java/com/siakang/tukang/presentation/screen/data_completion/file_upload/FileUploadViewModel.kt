package com.siakang.tukang.presentation.screen.data_completion.file_upload

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.FileTukang
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.presentation.screen.data_completion.personal_information.PersonalInformationInputEvent
import com.siakang.tukang.utils.ext.findFailure
import com.siakang.tukang.utils.validator.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class FileUploadViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel() {

    var photoProfile = MutableStateFlow<Uri?>(null)
        private set
    var idCard = MutableStateFlow<Uri?>(null)
        private set
    var skck = MutableStateFlow<Uri?>(null)
        private set

    private val inputEvents = Channel<FileUploadEvent>(Channel.CONFLATED)
    private val _storeProcess = Channel<Resource<FileTukang>>(Channel.CONFLATED)
    val storeProcess: Flow<Resource<FileTukang>> get() = _storeProcess.receiveAsFlow()

    val areInputsValid = combine(photoProfile, idCard, skck) { photoProfile, idCard, skck ->
        photoProfile != null && idCard != null && skck != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    private val storePhotoProfileResponse = Channel<Resource<String>>()
    private val storeIdCardResponse = Channel<Resource<String>>()
    private val storeSkckResponse = Channel<Resource<String>>()

    init {
        observeUserInputEvents()
        observeStoreData()
    }

    private fun observeUserInputEvents() {
        viewModelScope.launch(Dispatchers.Default) {
            inputEvents.receiveAsFlow()
                .collect { event ->
                    when (event) {
                        is FileUploadEvent.PhotoProfile -> {
                            photoProfile.value = event.uri
                        }
                        is FileUploadEvent.IdCard -> {
                            idCard.value = event.uri
                        }
                        is FileUploadEvent.SKCK -> {
                            skck.value = event.uri
                        }
                    }
                }
        }
    }

    private fun observeStoreData() {
        viewModelScope.launch {
            combine(storePhotoProfileResponse.receiveAsFlow(), storeIdCardResponse.receiveAsFlow(), storeSkckResponse.receiveAsFlow()) { photoProfile, idCard, skck ->
                if(photoProfile is Resource.Success && idCard is Resource.Success && skck is Resource.Success) {
                    Resource.Success(Triple(photoProfile.value, idCard.value, skck.value))
                }
                else if(photoProfile is Resource.Failure || idCard is Resource.Failure || skck is Resource.Failure) {
                    listOf(photoProfile, idCard, skck).findFailure()
                }
                else if(photoProfile is Resource.Loading || idCard is Resource.Loading || skck is Resource.Loading) {
                    Resource.Loading
                }
                else {
                   Resource.Empty
                }
            }.collect {
                if(it is Resource.Success) {
                    updateTukangFile(it.value.first, it.value.second, it.value.third)
                }
                else {
                    _storeProcess.trySend(it as Resource<FileTukang>)
                }
            }
        }
    }

    fun setPhotoProfile(uri: Uri) {
        inputEvents.trySend(FileUploadEvent.PhotoProfile(uri))
    }

    fun setIdCard(uri: Uri) {
        inputEvents.trySend(FileUploadEvent.IdCard(uri))
    }

    fun setSkck(uri: Uri) {
        inputEvents.trySend(FileUploadEvent.SKCK(uri))
    }

    private fun updateTukangFile(photo: String, idCard: String, skck: String) {
        viewModelScope.launch {
            userUseCase.updateFile(
                dataStoreUseCase.getUid().first(),
                FileTukang(
                    photo,
                    idCard,
                    skck
                )
            ).collect { result ->
                _storeProcess.trySend(result)
            }
        }
    }

    fun storePhotoProfile() {
        viewModelScope.launch {
            storePhotoProfileResponse.trySend(Resource.Loading)
            userUseCase.storeFile("users/photo_profile/${dataStoreUseCase.getUid().first()}.jpg", photoProfile.value ?: Uri.EMPTY).collect { result ->
                storePhotoProfileResponse.trySend(result)
            }
        }
    }

    fun storeIdCard() {
        viewModelScope.launch {
            storeIdCardResponse.trySend(Resource.Loading)
            userUseCase.storeFile("users/id_card/${dataStoreUseCase.getUid().first()}.jpg", idCard.value ?: Uri.EMPTY).collect { result ->
                storeIdCardResponse.trySend(result)
            }
        }
    }

    fun storeSkck() {
        viewModelScope.launch {
            storeSkckResponse.trySend(Resource.Loading)
            userUseCase.storeFile("users/skck/${dataStoreUseCase.getUid().first()}.jpg", skck.value ?: Uri.EMPTY).collect { result ->
                storeSkckResponse.trySend(result)
            }
        }
    }

}