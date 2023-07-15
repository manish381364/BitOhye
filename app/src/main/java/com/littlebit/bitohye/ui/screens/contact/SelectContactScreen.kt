package com.littlebit.bitohye.ui.screens.contact

import android.content.Intent
import android.provider.ContactsContract
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.QrCode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.littlebit.bitohye.ui.screens.homescreen.Contact
import com.littlebit.bitohye.ui.screens.homescreen.ContactItem

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun SelectContactScreen(navController: NavHostController = rememberAnimatedNavController()) {
    val context = LocalContext.current
    val contactList = remember {
        mutableStateListOf<Contact>()
    }
    contactList.addAll(readContacts(LocalContext.current))
    Scaffold(
        topBar = { ContactTopBar(navController) },
    ) {
        val state = LazyListState(firstVisibleItemScrollOffset = 1)
        LazyColumn(Modifier.padding(it), state) {
            item {
                Spacer(Modifier.height(12.5.dp))
                ContactTopRow(
                    title = "New Group",
                    leadingIcon = Icons.Rounded.Group
                ) {

                }
                ContactTopRow(
                    title = "New Contact",
                    leadingIcon = Icons.Rounded.PersonAdd,
                    trailingIcon = Icons.Rounded.QrCode
                ) {
                    val intent = Intent(Intent.ACTION_INSERT)
                    intent.type = ContactsContract.Contacts.CONTENT_TYPE
                    startActivity(context, intent, null)
                }
                Text(
                    text = "Contacts on Ohye",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                    modifier = Modifier.padding(start = 16.dp, top = 25.dp, bottom = 25.dp)
                )
            }
            items(contactList) { contact ->
                ContactItem(contact)
            }
        }
    }

}


