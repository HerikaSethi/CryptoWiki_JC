package `in`.hypernation.cryptowiki.screens.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hypernation.cryptowiki.data.repository.CryptoRepository
import `in`.hypernation.cryptowiki.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val cryptoRepository: CryptoRepository
) : ViewModel() {

    private val _state = mutableStateOf(CoinListState())
    val state: State<CoinListState> = _state

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _searchCoinState = MutableStateFlow(state.value.data)
    val searchState = searchText
        .debounce(1000L)
        .onEach { state.value.isLoading = true }
        .combine(_searchCoinState) { text, coins ->
            if(text.isBlank()){
                coins
            } else {
                coins.filter {
                    it.searchCombinations(text)
                }
            }
        }
        .onEach { state.value.isLoading = false }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchCoinState.value

        )


    init {
        getCoinList()
    }
    fun updateSearch(text : String) {
        _searchText.value = text
    }
    fun clearSearch(){
        _searchText.value = ""
    }

    private fun getCoinList(){
        viewModelScope.launch {
            when(val result = cryptoRepository.getCoinList()){
                is Resource.Success -> {
                    _state.value = CoinListState(data = result.data ?: emptyList(), isLoading = false)
                    _searchCoinState.value = state.value.data
                }
                is Resource.Error -> {
                    _state.value = CoinListState(error = result.message ?: "Something went wrong", isLoading = false)
                }

                else -> {
                    _state.value = CoinListState(isLoading = true)
                }
            }

        }

    }

}