package com.siakang.tukang.presentation.screen.data_completion.skill

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Category
import com.siakang.tukang.core.domain.usecase.CategoryUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : BaseViewModel() {

    var categoryResponse by mutableStateOf(emptyList<Category>())
    var checkedSkill by mutableStateOf(emptyList<Category>())

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

}