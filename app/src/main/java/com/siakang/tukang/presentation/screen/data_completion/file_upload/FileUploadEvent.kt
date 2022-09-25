package com.siakang.tukang.presentation.screen.data_completion.file_upload

import android.net.Uri

sealed class FileUploadEvent {
    class PhotoProfile(val uri: Uri): FileUploadEvent()
    class IdCard(val uri: Uri): FileUploadEvent()
    class SKCK(val uri: Uri): FileUploadEvent()
}