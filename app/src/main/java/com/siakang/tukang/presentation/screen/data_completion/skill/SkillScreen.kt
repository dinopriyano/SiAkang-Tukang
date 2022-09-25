package com.siakang.tukang.presentation.screen.data_completion.skill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.R
import com.siakang.tukang.presentation.component.check_box.LabelledCheckbox
import com.siakang.tukang.presentation.component.grid.gridItems
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar

@ExperimentalMaterial3Api
@Composable
fun SkillScreen(
    navController: NavHostController,
    viewModel: SkillViewModel = hiltViewModel()
) {

    viewModel.getCategory()
    val categories = viewModel.categoryResponse
    val checkedSkill = viewModel.checkedSkill
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(
                title = stringResource(R.string.skill_list),
                navController = navController
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                TitleDescription(
                    title = stringResource(id = R.string.choose_list),
                    desc = "Mohon untuk memilih keahlian sesuai keahlian yang kamu miliki.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 40.dp, 30.dp, 0.dp),
                    titleStyle = MaterialTheme.typography.labelMedium,
                    descStyle = MaterialTheme.typography.bodySmall,
                    spacerHeight = 8.dp
                )
            }
            gridItems(
                data = categories,
                columnCount = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) { item ->
                LabelledCheckbox(
                    modifier = Modifier,
                    label = "Tukang ${item.name}",
                    isChecked = checkedSkill.contains(item),
                    onCheckedChange = { isChecked ->
                        if(isChecked) {
                            viewModel.checkSkill(item)
                        }
                        else {
                            viewModel.uncheckSkill(item)
                        }
                    }
                )
            }
            item {
                LoadingButton(
                    text = stringResource(id = R.string.next),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 40.dp, 30.dp, 30.dp)
                        .height(50.dp),
                    onClick = {
                        navController.navigate("bank_info")
                    },
                    enabled = checkedSkill.isNotEmpty(),
                    loading = false
                )
            }
        }
    }
}