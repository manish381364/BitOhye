package com.littlebit.bitohye.ui.screens.profile

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.littlebit.bitohye.ui.theme.OhyeTheme


@Composable
@Preview(
    showBackground = true
)
fun ProfileScreenPreview() {
    OhyeTheme(
        useDarkTheme = true
    ) {
        ProfileScreen()
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navHostController: NavHostController = rememberAnimatedNavController()
) {
    val profileImageSize = remember { mutableStateOf(0.dp) }
    val scrollState = rememberScrollState()
    Surface {
        Scaffold(
            topBar = {
                Column(Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(50.dp))
                    ProfileTopBar2(
                        profileImageSize,
                        scrollState = scrollState,
                        navHostController = navHostController
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)) {
                ProfileContent(profileImageSize, scrollState = scrollState)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
fun ProfileTopBar(
    profileImageSize: MutableState<Dp> = mutableStateOf(200.dp),
    profileURL: String = dummyImage,
    name: String = "Dumb Man"
) {
    var showMenu by remember { mutableStateOf(false) }
    Box(contentAlignment = Alignment.TopEnd) {
        TopAppBar(
            title = {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = profileURL,
                        contentDescription = "User Name",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(animateDpAsState(targetValue = profileImageSize.value).value)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = name, style = MaterialTheme.typography.titleMedium)
                }
            },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = { showMenu = true }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "Options")
                }

            },
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedVisibility(
            visible = showMenu,
        ) {
            ProfileMenu(showMenu = showMenu, { showMenu = false })
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview(showBackground = true)
fun ProfileTopBar2(
    profileImageSize: MutableState<Dp> = mutableStateOf(50.dp),
    profileURL: String = dummyImage,
    name: String = "Dumb Man",
    scrollState: ScrollState = rememberScrollState(),
    navHostController: NavHostController = rememberAnimatedNavController()
) {
    var showMenu by remember { mutableStateOf(false) }
    val animateSpace = animateDpAsState(targetValue = if (scrollState.value <= 0) 90.dp else 0.dp)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (scrollState.value > 0) Arrangement.Center else Arrangement.Start
    ) {
        IconButton(onClick = {
            navHostController.popBackStack()
        }) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.padding(start = animateSpace.value))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = profileURL,
                contentDescription = "User Name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(animateDpAsState(targetValue = profileImageSize.value).value)
                    .clip(CircleShape)
            )
            AnimatedVisibility(
                visible = scrollState.value > 0,
                enter = slideInHorizontally { fullSize -> fullSize } + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { showMenu = true }) {
            Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "Options")
        }
        AnimatedVisibility(
            visible = showMenu,
        ) {
            ProfileMenu(showMenu = showMenu, { showMenu = false })
        }
    }
}


@Composable
fun ProfileMenu(
    showMenu: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { onDismissRequest.invoke() },
        modifier = modifier,
    ) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                text = { Text(text = item.title) },
                onClick = { item.onClick() },
                leadingIcon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ProfileContent(
    profileImageSize: MutableState<Dp> = mutableStateOf(100.dp),
    name: String = "Dumb Man",
    scrollState: ScrollState = rememberScrollState()
) {
    profileImageSize.value = if (scrollState.value > 0) 50.dp else 160.dp
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserBasicInformation(name = name)
        MediaLinksDocs()
        ButtonsBlock()
    }
}


@Composable
fun ButtonsBlock() {
    val checked = remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Notifications")
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Text(text = "Mute notifications")
            }
            Switch(
                checked = checked.value,
                onCheckedChange = { checked.value = it },
                modifier = Modifier.padding(end = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))


        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.Audiotrack, contentDescription = "Audio")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "Custom notifications")
        }


        Spacer(modifier = Modifier.height(4.dp))


        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.Image, contentDescription = "Media Visibility")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = "Media Visibility")
        }


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .height(8.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(0.2f))
        )

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = "Encryption")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column {
                Text(text = "Encryption")
                Text(
                    text = "Messages and calls are end-to-end encrypted. Tap to verify.",
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Filled.AvTimer, contentDescription = "Disappear")
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column {
                Text(text = "Disappearing Messages")
                Text(text = "Off", overflow = TextOverflow.Ellipsis)
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .height(8.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(0.2f))
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "No groups in common",
                    fontWeight = FontWeight.ExtraLight,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.4f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(shape = CircleShape, color = Color.Green.copy(0.6f)) {
                        Icon(
                            imageVector = Icons.Filled.Group,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Create group with User",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }





        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "Other phones",
                    fontWeight = FontWeight.ExtraLight,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.4f)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "+9198847789393", style = MaterialTheme.typography.titleLarge)
                    Row {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.Chat, contentDescription = "Chat")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone")
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Videocam,
                                contentDescription = "Video Call"
                            )
                        }
                    }
                }
                Text(
                    text = "Phone",
                    fontWeight = FontWeight.ExtraLight,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.4f)
                )
            }
        }



        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .height(8.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(0.2f))
        )



        Row(
            Modifier.padding(start = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Block,
                contentDescription = null,
                tint = Color.Red.copy(0.5f)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Block User",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Red.copy(0.5f)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            Modifier.padding(start = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ThumbDown,
                contentDescription = null,
                tint = Color.Red.copy(0.5f)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(
                text = "Report User",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Red.copy(0.5f)
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}


@Composable
fun MediaLinksDocs() {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Media,links, and docs")
            Text(text = "200 >")
        }
        LazyRow(Modifier.padding(6.dp)) {
            items(10) {
                AsyncImage(
                    model = "", contentDescription = "Image", modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .size(100.dp)
                        .padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun UserBasicInformation(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = name, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "1234567890",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Last seen today at 2:57 pm",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraLight,
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Call,
                    contentDescription = "Phone",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(28.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Videocam,
                    contentDescription = "Video Call",
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(modifier = Modifier.width(28.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .height(2.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(0.2f))
        )
        Row(modifier = Modifier.align(Alignment.Start)) {
            Spacer(Modifier.width(18.dp))
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Hello There I'm Using Ohye",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "20 May",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.ExtraLight,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .height(2.dp)
                .background(MaterialTheme.colorScheme.inverseSurface.copy(0.2f))
        )
    }
}


