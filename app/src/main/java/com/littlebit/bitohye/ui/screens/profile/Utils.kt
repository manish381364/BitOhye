package com.littlebit.bitohye.ui.screens.profile

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class ProfileMenuItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)
val menuItems = listOf<ProfileMenuItem>(
    ProfileMenuItem(
        title = "Share",
        icon = Icons.Rounded.Share
    ) { /*TODO*/ },
    ProfileMenuItem(
        title = "Edit",
        icon = Icons.Rounded.Edit
    ) { /*TODO*/ },
    ProfileMenuItem(
        title = "View in address book",
        icon = Icons.Rounded.Contacts
    ) { /*TODO*/ },
    ProfileMenuItem(
        title = "Label chat",
        icon = Icons.Rounded.Label
    ) { /*TODO*/ },
    ProfileMenuItem(
        title = "Verify security code",
        icon = Icons.Rounded.Security
    ) { /*TODO*/ },
)
val dummyImage = "https://i.pinimg.com/originals/25/78/61/25786134576ce0344893b33a051160b1.jpg"