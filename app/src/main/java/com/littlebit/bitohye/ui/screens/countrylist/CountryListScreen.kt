package com.littlebit.bitohye.ui.screens.countrylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.littlebit.bitohye.util.Country
import com.littlebit.bitohye.util.countries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun CountryListScreen(
    navController: NavHostController = rememberNavController(),
    countryCode: MutableState<String> = mutableStateOf("+1")
) {
    val selectedCountry by remember { mutableStateOf(countryCode) }
    val showSearchBar = remember { mutableStateOf(false) }
    val countryListVisible = remember { mutableStateOf(false) }
    val inputText = remember { mutableStateOf("") }
    val searchCountryList = remember { mutableStateOf(emptyList<Country>()) }
    countries.forEach {
        if (it.code == countryCode.value) {
            it.selected.value = true
        }
    }
    val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = rememberTopAppBarState(), snapAnimationSpec = spring(stiffness = Spring.StiffnessLow))
    Scaffold(
        topBar = {
            AnimatedTopSearchBar(
                showSearchBar,
                inputText,
                countryListVisible,
                searchCountryList,
                selectedCountry,
                countryCode,
                navController,
                topAppBarScrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) {
        Box(Modifier.padding(it), contentAlignment = Alignment.Center) {
            AnimatedVisibility(visible = countryListVisible.value) {
                SearchList(searchCountryList, inputText, selectedCountry, navController)
            }
            AnimatedVisibility(visible = !countryListVisible.value) {
                CountryList(selectedCountry, navController)
            }
        }
    }
}





