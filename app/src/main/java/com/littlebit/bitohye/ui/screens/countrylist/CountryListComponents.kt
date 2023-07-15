package com.littlebit.bitohye.ui.screens.countrylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.littlebit.bitohye.navigation.Screens
import com.littlebit.bitohye.ui.screens.phoneauth.OTopBar
import com.littlebit.bitohye.util.Country
import com.littlebit.bitohye.util.countries
import com.littlebit.bitohye.util.countryCodeToFlagUrlMap

fun search(
    countryListVisible: MutableState<Boolean>,
    value: String,
    countries: List<Country>,
    searchCountryList: MutableState<List<Country>>
) {
    val searchList = mutableListOf<Country>()
    countries.filter { it.name.contains(value, true) || it.code.contains(value, true) }
        .forEach {
            searchList.add(it)
        }
    searchCountryList.value = searchList
    countryListVisible.value = true
}

@Preview(showBackground = true)
@Composable
fun CountryItem(
    name: String = "name",
    code: String = "+ code",
    selected: Boolean = false,
    onClick: (clickedCode: String) -> Unit = {}
) {
    var countryFlag = countryCodeToFlagUrlMap[code.removePrefix("+").trim()]
    if (code == "+1" && name == "United States")
        countryFlag = "https://flagcdn.com/240x180/us.png"
    Column(
        Modifier.padding(start = 6.dp, end = 6.dp)
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray.copy(0.4f),
            thickness = 0.6.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(code) },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically) {
                Box {
                    var isLoading by remember { mutableStateOf(true) }
                    AsyncImage(
                        model = countryFlag ?: "https://flagcdn.com/240x180/us.png",
                        contentDescription = "flag",
                        modifier = Modifier.size(35.dp),
                        onLoading = { isLoading = true },
                        onSuccess = { isLoading = false },
                        onError = { isLoading = false }
                    )
                    if (isLoading)
                        CircularProgressIndicator(Modifier.size(35.dp))
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = name,
                    style = TextStyle(
                        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
            }
            Row(modifier = Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(code)
                if (selected) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Tick Mark",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun CountryList(
    selectedCountry: MutableState<String>,
    navController: NavHostController
) {
    LazyColumn {
        items(countries) { country ->
            var selected by remember { country.selected }
            CountryItem(
                name = country.name,
                code = country.code,
                selected = selected
            ) { clickedCode ->
                selected = !selected
                countries.forEach { currentCountry ->
                    if (currentCountry.code != clickedCode && selected && currentCountry.selected.value) {
                        currentCountry.selected.value = false
                    }
                }
                if (selected) {
                    selectedCountry.value = country.name + "[]" + country.code
                    navController.navigate(Screens.PhoneAuthScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screens.CountryListScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchList(
    searchCountryList: MutableState<List<Country>>,
    inputText: MutableState<String>,
    selectedCountry: MutableState<String>,
    navController: NavHostController
) {
    if (searchCountryList.value.isEmpty()) {
        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                append("No Country Found with this ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(inputText.value)
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                append(" name")
            }
        }
        Text(text = annotatedString)
    } else
        LazyColumn {
            items(searchCountryList.value) { country ->
                var selected by remember { country.selected }
                CountryItem(
                    name = country.name,
                    code = country.code,
                    selected = selected
                ) { clickedCode ->
                    selected = !selected
                    countries.forEach { currentCountry ->
                        if (currentCountry.code != clickedCode && selected && currentCountry.selected.value) {
                            currentCountry.selected.value = false
                        }
                    }
                    if (selected) {
                        selectedCountry.value = country.name + "[]" + country.code
                        navController.navigate(Screens.PhoneAuthScreen.route) {
                            launchSingleTop = true
                            popUpTo(Screens.CountryListScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnimatedTopSearchBar(
    showSearchBar: MutableState<Boolean>,
    inputText: MutableState<String>,
    countryListVisible: MutableState<Boolean>,
    searchCountryList: MutableState<List<Country>>,
    selectedCountry: MutableState<String>,
    countryCode: MutableState<String>,
    navController: NavHostController,
    topAppBarScrollBehavior: TopAppBarScrollBehavior
) {
    val focusRequester = FocusRequester()
    AnimatedVisibility(
        showSearchBar.value,
        enter = slideInHorizontally(initialOffsetX = { it }),
        exit = slideOutHorizontally(targetOffsetX = { it })
    ) {
        TopAppBar(
            title = {
                TextField(
                    value = inputText.value,
                    onValueChange = {
                        inputText.value = it
                    },
                    placeholder = { Text("Search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .horizontalScroll(rememberScrollState()),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(onSearch = {
                        search(
                            countryListVisible,
                            inputText.value.trim(),
                            countries,
                            searchCountryList
                        )
                    }),
                    maxLines = 1,
                    shape = CircleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                    )
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    if (countryListVisible.value) {
                        countryListVisible.value = false
                    } else {
                        showSearchBar.value = !showSearchBar.value
                        countryCode.value = selectedCountry.value
                    }
                    inputText.value = ""
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "ArrowBack",
                    )
                }
            },
            scrollBehavior = topAppBarScrollBehavior
        )
        LaunchedEffect(key1 = Unit, block = { focusRequester.requestFocus() })
    }
    AnimatedVisibility(
        !showSearchBar.value,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
        OTopBar(
            title = {
                Text(
                    text = "Choose a country",
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
            },
            navigationIcon = Icons.Rounded.ArrowBack,
            onNavigation = {
                showSearchBar.value = !showSearchBar.value
                countryCode.value = selectedCountry.value
                navController.popBackStack()
            },
            actionsIcon = Icons.Rounded.Search,
            topAppBarScrollBehavior = topAppBarScrollBehavior,
            onActions = {
                showSearchBar.value = !showSearchBar.value
            }
        )
    }
}