package com.siakang.tukang.presentation.screen.data_completion.skill

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Category
import com.siakang.tukang.core.domain.model.FileTukang
import com.siakang.tukang.core.domain.model.SkillTukang
import com.siakang.tukang.core.domain.usecase.CategoryUseCase
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : BaseViewModel() {

    var categoryResponse by mutableStateOf(emptyList<Category>())
    var checkedSkill by mutableStateOf(emptyList<Category>())

    private val _storeSkillResponse = Channel<Resource<SkillTukang>>(Channel.CONFLATED)
    val storeSkillResponse: Flow<Resource<SkillTukang>> get() = _storeSkillResponse.receiveAsFlow()

    fun getCategory() {
        viewModelScope.launch {
            categoryUseCase.getMenu(100).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        categoryResponse = (result.value)
                    }
                    is Resource.Failure -> Unit
                    else -> Unit
                }
            }
        }
    }

    fun checkSkill(category: Category) {
        viewModelScope.launch {
            val newCategory = checkedSkill.toMutableList()
            newCategory.add(category)
            checkedSkill = newCategory
        }
    }

    fun uncheckSkill(category: Category) {
        viewModelScope.launch {
            val newCategory = checkedSkill.toMutableList()
            newCategory.remove(category)
            checkedSkill = newCategory
        }
    }

    fun storeSkill() {
        viewModelScope.launch {
            _storeSkillResponse.trySend(Resource.Loading)
            userUseCase.updateSkill(
                dataStoreUseCase.getUid().first(),
                SkillTukang(
                    checkedSkill.map { it.id }
                )
            ).collect { result->
                _storeSkillResponse.trySend(result)
            }
        }
    }

}