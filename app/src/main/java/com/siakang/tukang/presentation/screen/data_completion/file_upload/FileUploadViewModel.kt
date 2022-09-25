package com.siakang.tukang.presentation.screen.data_completion.file_upload

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.presentation.base.BaseViewModel
import com.siakang.tukang.domain.usecase.InputWrapper
import com.siakang.tukang.presentation.screen.data_completion.personal_information.PersonalInformationInputEvent
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

) : BaseViewModel() {

    var photoProfile = MutableStateFlow<Uri?>(null)
        private set
    var idCard = MutableStateFlow<Uri?>(null)
        private set
    var skck = MutableStateFlow<Uri?>(null)
        private set

    private val inputEvents = Channel<FileUploadEvent>(Channel.CONFLATED)

    val areInputsValid = combine(photoProfile, idCard, skck) { photoProfile, idCard, skck ->
        photoProfile != null && idCard != null && skck != null
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    init {
        observeUserInputEvents()
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

    fun setPhotoProfile(uri: Uri) {
        inputEvents.trySend(FileUploadEvent.PhotoProfile(uri))
    }

    fun setIdCard(uri: Uri) {
        inputEvents.trySend(FileUploadEvent.IdCard(uri))
    }

    fun setSkck(uri: Uri) {
        inputEvents.trySend(FileUploadEvent.SKCK(uri))
    }

}