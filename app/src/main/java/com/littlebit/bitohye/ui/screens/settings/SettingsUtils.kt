package com.littlebit.bitohye.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DonutLarge
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.outlined.Fitbit
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.littlebit.bitohye.R
import com.littlebit.bitohye.navigation.Screens

@Composable
fun ByLittleBit() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val annotatedString = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            ) {
                append("made with ")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {
                append("❤️")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            ) {
                append(" by")
            }
        }
        Text(
            text = annotatedString,
            color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
        )
        Spacer(Modifier.height(6.dp))
        Row {

            Icon(
                imageVector = Icons.Outlined.Fitbit,
                contentDescription = ""
            )
            Spacer(Modifier.width(2.dp))
            Text(
                "LittleBit",
                style = MaterialTheme.typography.titleLarge,
                fontStyle = FontStyle(R.font.lato_italic)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ProfileInformation(
    title: String,
    description: String,
    leadingIcon: ImageVector? = null,
    leadingIconBottom: ImageVector? = null,
    leadingIconBottomSize: Dp? = null,
    trailingIcon: ImageVector? = null,
    leadingImage: String? = null,
    leadingIconSize: Dp? = null,
    trailingIconSize: Dp? = null,
    leadingIconColor: Color? = null,
    trailingIconColor: Color? = null,
    leadingImageSize: Dp? = null,
    bottomDivider: Boolean = true,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineText = { Text(text = title) },
        supportingText = {
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        },
        leadingContent = {
            Box {
                if (leadingImage != null) {
                    AsyncImage(
                        model = leadingImage,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(leadingImageSize ?: 70.dp),
                        contentScale = ContentScale.Crop
                    )
                } else if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(leadingIconSize ?: 70.dp),
                        tint = leadingIconColor ?: MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                }
                if (leadingIconBottom != null) {
                    Box(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .background(
                                MaterialTheme.colorScheme.surface,
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = leadingIconBottom,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(leadingIconBottomSize ?: 12.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(0.7f)
                        )
                    }
                }
            }
        },
        trailingContent = {
            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = trailingIconColor ?: MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(trailingIconSize ?: 24.dp)
                )
            }
        },
    )
    if (bottomDivider)
        Divider(thickness = 0.4.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = "Settings", style = MaterialTheme.typography.titleMedium) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Screens.HomeScreen.route) {
                    popUpTo(Screens.SettingsScreen.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "BackButton")
            }
        }
    )
}


//val userName = FirebaseAuth.getInstance().currentUser?.displayName ?: FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
val settingsItemInformation = listOf(
    SettingsItemInformation(
        title = "User Name",
        description = "Het there! I am using Ohye App.",
        trailingIcon = Icons.Filled.QrCode2,
        leadingIcon = Icons.Filled.AccountCircle
    ),
    SettingsItemInformation(
        title = "Account",
        description = "Security notifications, change number",
        leadingIcon = Icons.Filled.Key
    ),
    SettingsItemInformation(
        title = "Privacy",
        description = "Block contacts, disappearing messages",
        leadingIcon = Icons.Filled.Lock
    ),
    SettingsItemInformation(
        title = "Avatar",
        description = "Create, edit, profile photo",
        leadingIcon = Icons.Filled.EmojiEmotions
    ),
    SettingsItemInformation(
        title = "Chats",
        description = "Theme, wallpapers, chat history",
        leadingIcon = Icons.Filled.Chat,
        onClick = {}
    ),
    SettingsItemInformation(
        title = "Notifications",
        description = "Message, group & call tones",
        leadingIcon = Icons.Filled.Notifications
    ),
    SettingsItemInformation(
        title = "Storage and data",
        description = "Network usage, auto-download",
        leadingIcon = Icons.Filled.DonutLarge
    ),
    SettingsItemInformation(
        title = "App language",
        description = "English (phone's language)",
        leadingIcon = Icons.Filled.Language
    ),
    SettingsItemInformation(
        title = "Help",
        description = "Help center, contact us, privacy policy",
        leadingIcon = Icons.Outlined.Help
    ),
    SettingsItemInformation(
        title = "Invite a friend",
        leadingIcon = Icons.Filled.Group
    ),
    SettingsItemInformation(
        title = "Sign Out",
        leadingIcon = Icons.Filled.Logout
    ),
    SettingsItemInformation(
        title = "from",
        description = "LittleBit",
    ),
)

data class SettingsItemInformation(
    val title: String,
    val description: String? = null,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null,
    val onClick: () -> Unit = {}
)
