package com.siakang.tukang.presentation.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.siakang.tukang.presentation.screen.dashboard.home.TabItem
import com.siakang.tukang.presentation.theme.Border
import com.siakang.tukang.presentation.theme.Placeholder
import com.siakang.tukang.presentation.theme.Primary
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Tabs(modifier: Modifier, tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    // OR ScrollableTabRow()
    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Primary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions)
                    .height(2.5.dp)
                    .background(Border)
                    .clip(RoundedCornerShape(10.dp)),
                height = 2.5.dp,
                color = Primary
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                selectedContentColor = Primary,
                unselectedContentColor = Placeholder,
                icon = {

                },
                text = {
                    Text(
                        text = tab.label,
                        style = if (pagerState.currentPage == index) MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp,
                            color = Primary
                        ) else MaterialTheme.typography.labelSmall.copy(
                            fontSize = 14.sp,
                            color = Placeholder
                        )
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}