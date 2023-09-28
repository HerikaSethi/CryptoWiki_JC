package com.herika.cryptowiki.screens.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.herika.cryptowiki.domain.models.CoinList
import com.herika.cryptowiki.ui.theme.Green
import com.herika.cryptowiki.ui.theme.Red
import com.herika.cryptowiki.ui.theme.fonts


@Composable
fun CryptoListItem(
    coin : CoinList,
    onItemClick : () -> Unit
){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 30.dp)
            .shadow(3.dp, RoundedCornerShape(25.dp))
            .clickable { onItemClick() }
    ){
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(25.dp))
                .padding(vertical = 35.dp, horizontal = 25.dp)

        ) {
            Text(
                text = "${coin.rank}. ${coin.name} (${coin.symbol})",
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 18.sp,
                fontFamily = fonts,
                fontWeight = FontWeight.Medium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(.7f)

            )

            Text(
                text = if(coin.isActive) "active" else "inactive",
                color = if(coin.isActive) Green else Red,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(.3f),
                textAlign = TextAlign.End

            )
        }

    }
}