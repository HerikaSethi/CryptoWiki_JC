package com.herika.cryptowiki.screens.home_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.herika.cryptowiki.R
import com.herika.cryptowiki.screens.Screen
import com.herika.cryptowiki.screens.view_models.CoinListViewModel
import com.herika.cryptowiki.ui.theme.fonts
import java.lang.Float.min

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val lazyState = rememberLazyListState()
    val state = viewModel.state.value
    val refreshState = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.value)
    var isSearchAnimate by remember {
        mutableStateOf(false)
    }
    val searchText by viewModel.searchText.collectAsState()
    val coins by viewModel.searchState.collectAsState(initial = state.data)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(!isSearchAnimate){
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 24.sp,
                        fontFamily = fonts,
                        fontWeight = FontWeight.SemiBold

                    )
                }
                SearchCard(isSearchAnimate,
                    searchText,
                    onClick = {
                        isSearchAnimate =!isSearchAnimate
                        viewModel.clearSearch()
                    },
                    onChange = viewModel::updateSearch
                )
            }
            LazyColumn(
                state = lazyState,
                modifier = Modifier.fillMaxSize()
            ) {
                item {

                    if(!isSearchAnimate){
                        Column(
                            modifier = Modifier
                                .padding(30.dp, 20.dp, 20.dp, 40.dp)
                                .graphicsLayer {
                                    alpha =
                                        min(1f, 1 - (lazyState.firstVisibleItemScrollOffset / 300f))
                                    translationY = -lazyState.firstVisibleItemScrollOffset * 0.1f
                                }
                        ) {
                            Text(
                                text = "here,",
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                fontSize = 24.sp,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,

                                )
                            Text(
                                text = stringResource(id = R.string.app_description),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontSize = 28.sp,
                                fontFamily = fonts,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.fillMaxWidth(),
                                lineHeight = 36.sp
                            )

                        }
                    }

                }
                items(coins) { coin ->
                    CryptoListItem(coin) {
                        navController.navigate(Screen.DetailScreen.route + "/${coin.id}")
                    }
                }
            }


        }
        if(state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.tertiary,
            )
        }

    }

    SwipeRefresh(
        state = refreshState,
        onRefresh =  viewModel::refreshCoinList,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
        ) {

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if(!isSearchAnimate){
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 24.sp,
                            fontFamily = fonts,
                            fontWeight = FontWeight.SemiBold

                        )
                    }
                    SearchCard(isSearchAnimate,
                        searchText,
                        onClick = {
                            isSearchAnimate =!isSearchAnimate
                            viewModel.clearSearch()
                        },
                        onChange = viewModel::updateSearch
                    )
                }
                LazyColumn(
                    state = lazyState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {

                        if(!isSearchAnimate){
                            Column(
                                modifier = Modifier
                                    .padding(30.dp, 20.dp, 20.dp, 40.dp)
                                    .graphicsLayer {
                                        alpha =
                                            min(
                                                1f,
                                                1 - (lazyState.firstVisibleItemScrollOffset / 300f)
                                            )
                                        translationY =
                                            -lazyState.firstVisibleItemScrollOffset * 0.1f
                                    }
                            ) {
                                Text(
                                    text = "Say Hello to",
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    fontSize = 24.sp,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,

                                    )
                                Text(
                                    text = stringResource(id = R.string.app_description),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontSize = 28.sp,
                                    fontFamily = fonts,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 36.sp
                                )

                            }
                        }

                    }
                    items(coins) { coin ->
                        CryptoListItem(coin) {
                            navController.navigate(Screen.DetailScreen.route + "/${coin.id}")
                        }
                    }
                }


            }
            if(state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if(state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCard(
    isSearchAnimate : Boolean,
    searchText : String ,
    onClick : () -> Unit ,
    onChange : (value : String) -> Unit
) {

    Row(
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(if (isSearchAnimate) 25.dp else 100.dp),
                spotColor = Color.Black
            )
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(if (isSearchAnimate) 25.dp else 100.dp)
            )
            .padding(vertical = if (isSearchAnimate) 2.dp else 10.dp, horizontal = 24.dp)
            .animateContentSize(
                animationSpec = tween(100)
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(
            imageVector = if(isSearchAnimate) Icons.Rounded.ArrowBack else Icons.Rounded.Search,
            contentDescription = "search",
            modifier = Modifier
                .clickable {
                    onClick()
                }
                .size(if (isSearchAnimate) 26.dp else 24.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
        )
        Spacer(modifier = Modifier.width(6.dp))
        if(isSearchAnimate) {
            TextField(
                value = searchText,
                onValueChange = {onChange(it)},
                placeholder = { Text(
                    text = "Search...",
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    style = MaterialTheme.typography.labelMedium
                ) },
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.tertiary),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                    ),

            )
        } else {
            Text(
                text = "Search",
                color = MaterialTheme.colorScheme.surfaceVariant,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .clickable { onClick() }
                    .padding(bottom = 1.dp)
            )
        }

    }
}

@Preview
@Composable
fun PreviewCompose(){
    SearchCard(isSearchAnimate = true, "", {}){

    }
}

