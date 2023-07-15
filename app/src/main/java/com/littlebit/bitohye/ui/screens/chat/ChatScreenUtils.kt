package com.littlebit.bitohye.ui.screens.chat

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AttachFile
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.littlebit.bitohye.navigation.Screens
import com.littlebit.bitohye.ui.screens.homescreen.Contact
import com.littlebit.bitohye.ui.theme.OhyeTheme

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun ChattingOptions(
    modifier: Modifier = Modifier,
    showAttachmentRow: MutableState<Boolean>
) {
    val inputText = remember { mutableStateOf("") }
    OhyeTheme {
        Row(
            modifier
                .padding(1.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50),
            ) {
                TextField(
                    value = inputText.value,
                    onValueChange = { inputText.value = it },
                    maxLines = 6,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .requiredHeightIn(min = 50.dp, max = 150.dp),
                    placeholder = {
                        Text(
                            text = "Message",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    },
                    leadingIcon = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(25.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEmotions,
                                contentDescription = "Mic"
                            )

                        }
                    },
                    trailingIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    showBottomSheetDialog()
                                    showAttachmentRow.value = !showAttachmentRow.value
                                },
                                modifier = Modifier
                                    .size(25.dp)
                                    .onFocusChanged { focusState ->
                                        if (!focusState.isFocused && !showAttachmentRow.value) {
                                            showAttachmentRow.value = false
                                        }
                                    }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.AttachFile,
                                    contentDescription = "AttachFile",
                                    modifier = Modifier.rotate(120f)
                                )
                            }
                            Spacer(Modifier.width(16.dp))
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(25.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.PhotoCamera,
                                    contentDescription = "PhotoCamera"
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary,
                    ),
                    textStyle = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Normal
                    ),
                )
            }
            Spacer(Modifier.width(4.dp))
            Surface(
                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                IconButton(
                    modifier = Modifier.clip(CircleShape),
                    onClick = {

                    }
                ) {
                    if (inputText.value.isEmpty())
                        Icon(imageVector = Icons.Default.Mic, contentDescription = "Mic")
                    else
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Send"
                        )
                }
            }
        }
    }
}

fun showBottomSheetDialog() {

}


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    navHostController: NavHostController = rememberAnimatedNavController(),
    contact: Contact = Contact("John Doe", "https://picsum.photos/200/300", "12:00 PM", null),
    scrollBehavior: TopAppBarScrollBehavior
) {
    val name = contact.name
    val image = contact.profilePicture ?: "https://picsum.photos/200/300"
    val lastSeen = contact.lastSeen
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { navHostController.popBackStack() },
                colors = if (isSystemInDarkTheme()) IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary) else IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        title = {
            Row(
                Modifier.clickable {
                    navHostController.navigate(Screens.ProfileScreen.route){
                        launchSingleTop = true
                    }
                }
            ) {
                if (image.isNotEmpty()) {
                    AsyncImage(
                        model = image, contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(
                                CircleShape
                            ), contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(
                                CircleShape
                            ),
                        tint = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary.copy(
                            0.5f
                        ) else MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))

                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = lastSeen,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = if (isSystemInDarkTheme()) IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary) else IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Videocam, contentDescription = "Video Call")
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = if (isSystemInDarkTheme()) IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary) else IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone Call")
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    colors = if (isSystemInDarkTheme()) IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary) else IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Options")
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun DateStamp(modifier: Modifier = Modifier, s: String = "Today") {
    Surface(
        modifier,
        color = MaterialTheme.colorScheme.surface.copy(0.5f),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onSurface.copy(0.5f))
    ) {
        Text(
            text = s,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    text: String = "Hello",
    isSender: Boolean = true,
    isFirstMessage: Boolean = true,
    time: String = "12:00 PM",
) {
    val topEnd = if (isSender) 20.dp else {
        if (isFirstMessage) 0.dp else 20.dp
    }
    val bottomEnd = if (isSender) 20.dp else 30.dp
    val bottomStart = if (isSender) {
        if (isFirstMessage) 30.dp else 20.dp
    } else 20.dp
    val topStart = if (isSender) 0.dp else 20.dp
    val isExpanded = remember { mutableStateOf(false) }
    val isOverflowing = remember { mutableStateOf(false) }
    OhyeTheme(useDarkTheme = true) {
        Surface(
            color = if (isSender) MaterialTheme.colorScheme.inversePrimary.copy(0.5f)
            else MaterialTheme.colorScheme.primary.copy(0.5f),
            modifier = modifier
                .padding(8.dp)
                .border(
                    width = 0.6.dp, color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    shape = RoundedCornerShape(
                        topStart = topStart,
                        topEnd = topEnd,
                        bottomEnd = bottomEnd,
                        bottomStart = bottomStart
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topStart = topStart,
                        topEnd = topEnd,
                        bottomEnd = bottomEnd,
                        bottomStart = bottomStart
                    )
                )
                .widthIn(0.dp, 300.dp)
                .clickable { }
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 2.dp),
                    maxLines = if (isExpanded.value) Int.MAX_VALUE else 28,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = {
                        isOverflowing.value = it.hasVisualOverflow
                    },
                )
                if (isOverflowing.value) {
                    Text(
                        text = if (isExpanded.value) "Show Less" else "Show More",
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                            .clickable {
                                isExpanded.value = !isExpanded.value
                            },
                        color = Color.Blue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                    )
                }
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal,
                    fontSize = 8.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 16.dp, bottom = 2.dp),
                )
            }
        }
    }
}

@Composable
fun ChatBody() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        for (i in 0..10) {
            ChatBubble(
                isSender = true, modifier = Modifier
                    .align(Alignment.Start)
            )
            DateStamp(Modifier.align(Alignment.CenterHorizontally))
            ChatBubble(
                isSender = false,
                modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth(0.8f),
                text = "Hello World how are the things going on? and when are you coming to my place? lets have a party there. See more new features of the compose and explore it further Hello World how are the things going on? and when are you coming to my place? lets have a party there. See more new features of the compose and explore it further",
            )
            ChatBubble(
                isSender = false,
                modifier = Modifier
                    .align(Alignment.End),
                text = "Hello",
            )
        }
    }
}

data class AttachmentButton(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val contentDescription: String? = null,
    val background: Brush,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview(showBackground = true)
fun AttachmentsListButtons(
    buttons: List<AttachmentButton> = AttachmentButtons,
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        FlowRow {
            buttons.forEach { button ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    IconButton(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(button.background),
                        onClick = { button.onClick() },
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurface),
                    ) {
                        Icon(
                            imageVector = button.icon,
                            contentDescription = button.contentDescription
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = button.contentDescription!!,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

val AttachmentButtons: List<AttachmentButton> = listOf(
    AttachmentButton(
        Icons.Filled.InsertDriveFile,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(50, 70, 168, 100),
                Color(50, 70, 168)
            )
        ),
        contentDescription = "File"
    ),
    AttachmentButton(
        Icons.Filled.PhotoCamera,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(224, 29, 101, 100),
                Color(224, 29, 101)
            )
        ),
        contentDescription = "Gallery"
    ),
    AttachmentButton(
        Icons.Filled.Bolt,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(255, 159, 28, 100),
                Color(255, 159, 28)
            )
        ),
        contentDescription = "Quick Reply"
    ),
    AttachmentButton(
        Icons.Filled.Place,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(0, 200, 83, 100),
                Color(0, 200, 83)
            )
        ),
        contentDescription = "Location"
    ),
    AttachmentButton(
        Icons.Filled.Headset,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(224, 88, 29, 100),
                Color(224, 88, 29)
            )
        ),
        contentDescription = "Audio"
    ),
    AttachmentButton(
        Icons.Filled.Store,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(119, 0, 255, 100),
                Color(119, 0, 255, 255)
            )
        ),
        contentDescription = "Catalogue"
    ),
    AttachmentButton(
        Icons.Filled.Person,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(29, 110, 224, 100),
                Color(29, 110, 224)
            )
        ),
        contentDescription = "Contact"
    ),
    AttachmentButton(
        Icons.Filled.Equalizer,
        onClick = { },
        background = Brush.verticalGradient(
            listOf(
                Color(76, 175, 80, 100),
                Color(76, 175, 80)
            )
        ),
        contentDescription = "Poll"
    ),
)




