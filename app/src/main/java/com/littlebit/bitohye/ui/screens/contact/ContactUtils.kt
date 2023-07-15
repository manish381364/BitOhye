package com.littlebit.bitohye.ui.screens.contact

import android.content.Context
import android.provider.ContactsContract
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Dialpad
import androidx.compose.material.icons.rounded.Help
import androidx.compose.material.icons.rounded.Keyboard
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.littlebit.bitohye.ui.screens.homescreen.Contact
import com.littlebit.bitohye.ui.screens.homescreen.DropMenu
import com.littlebit.bitohye.ui.screens.homescreen.MenuItem
fun readContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val projection = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
        ContactsContract.CommonDataKinds.Phone.NUMBER
    )
    val selection = null
    val selectionArgs = null
    val sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC"
    val contentResolver = context.contentResolver
    val cursor = contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )
    cursor?.use {
        val idColumn = it.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
        val nameColumn =
            it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        val phoneColumn =
            it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)

        while (it.moveToNext()) {
            it.getLong(idColumn)
            val name = it.getString(nameColumn)
            val phone = it.getString(phoneColumn)
            val contact = Contact(name, "", "", "", phone)
            contacts.add(contact)
        }
    }
    return contacts
}



@Composable
fun ContactTopRow(
    title: String,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.5.dp, bottom = 12.5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(45.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(0.7f), CircleShape)
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "icon",
                    Modifier
                        .size(25.dp)
                        .align(Alignment.Center),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(text = title)
        }
        if (trailingIcon != null) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = trailingIcon, contentDescription = "icon")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ContactTopBar(navController: NavHostController) {
    val searchQuery = remember { mutableStateOf("") }
    val searchIconVisible = remember { mutableStateOf(true) }
    val isDropdownExpanded = remember { mutableStateOf(false) }
    val keyboardType = remember { mutableStateOf(KeyboardType.Text) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val icon = remember { mutableStateOf(Icons.Rounded.Dialpad) }
    TopAppBar(
        title = {
            if (searchIconVisible.value) {
                Column {
                    Text(
                        text = "Select contact",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily.Default
                        )
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "12 contacts",
                        color = MaterialTheme.colorScheme.onPrimary.copy(0.5f),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            } else {
                TextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    placeholder = {
                        Text(
                            text = "Type a nem or number...",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        placeholderColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                    ),
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Normal
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType.value,
                        imeAction = ImeAction.Search
                    ),
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            }
                        }
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                if (!searchIconVisible.value) {
                    searchIconVisible.value = !searchIconVisible.value
                    keyboardType.value = KeyboardType.Text
                    icon.value = Icons.Rounded.Dialpad

                } else navController.popBackStack()
            }
            ) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "icon",
                )
            }
        },
        actions = {
            if (searchIconVisible.value) {
                IconButton(onClick = { searchIconVisible.value = !searchIconVisible.value }) {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "icon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = {
                    isDropdownExpanded.value = !isDropdownExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Menu",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                DropMenu(isDropdownExpanded, navController, contactMenuItems)
            } else {
                IconButton(onClick = {
                    if (icon.value == Icons.Rounded.Dialpad) {
                        icon.value = Icons.Rounded.Keyboard
                        keyboardType.value = KeyboardType.Number
                    } else {
                        icon.value = Icons.Rounded.Dialpad
                        keyboardType.value = KeyboardType.Text
                    }
                }) {
                    Icon(
                        icon.value,
                        contentDescription = "icon",
                    )
                }
            }
        },
        modifier = Modifier.shadow(4.dp),
        colors = if (searchIconVisible.value) TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ) else TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
        ),
    )
}

val contactMenuItems = listOf(
    MenuItem(
        title = "Invite a contact",
        icon = Icons.Rounded.Person,
        onClick = {},
    ),
    MenuItem(
        title = "Contacts",
        icon = Icons.Rounded.Contacts,
        onClick = {},
    ),
    MenuItem(
        title = "Refresh",
        icon = Icons.Rounded.Refresh,
        onClick = {},
    ),
    MenuItem(
        title = "Help",
        icon = Icons.Rounded.Help,
        onClick = {},
    )
)