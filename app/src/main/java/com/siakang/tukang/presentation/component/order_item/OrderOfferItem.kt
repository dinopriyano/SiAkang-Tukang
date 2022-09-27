package com.siakang.tukang.presentation.component.order_item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.siakang.tukang.R
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.core.utils.ext.toDateString
import com.siakang.tukang.presentation.theme.*

@Composable
fun OrderOfferItem(
    modifier: Modifier,
    order: Order,
    onDetailClick: (Order) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp, 17.dp, 17.dp, 5.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${order.subCategory} - ${order.category}",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                )
                Text(
                    text = order.requestDate.toDateString(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Hint
                    ),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    tint = Secondary
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    text = order.userName,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_marker),
                    contentDescription = null,
                    tint = Secondary
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    text = order.address,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 14.sp,
                        lineHeight = TextUnit.Unspecified,
                        color = Placeholder
                    ),
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                color = Border,
                thickness = 1.dp
            )
            Button(
                modifier = Modifier
                    .align(Alignment.End)
                    .defaultMinSize(
                        minWidth = 1.dp,
                        minHeight = 1.dp
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                onClick = { onDetailClick.invoke(order) },
                contentPadding = PaddingValues()
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = R.drawable.ic_dashicons_search),
                    contentDescription = null,
                    tint = Primary
                )
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp),
                    text = "Detail",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 14.sp,
                        lineHeight = TextUnit.Unspecified,
                        color = Primary
                    ),
                )
            }
        }
    }
}