package com.littlebit.bitohye.ui.screens.homescreen

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.BroadcastOnHome
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.Devices
import androidx.compose.material.icons.rounded.Equalizer
import androidx.compose.material.icons.rounded.Gif
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.Headphones
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.MarkChatUnread
import androidx.compose.material.icons.rounded.Message
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Photo
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.littlebit.bitohye.R
import com.littlebit.bitohye.navigation.Screens
import com.littlebit.bitohye.ui.screens.contact.readContacts
import com.littlebit.bitohye.ui.screens.settings.ProfileInformation
import com.littlebit.bitohye.ui.theme.OhyeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchScreen(isVisibleSearchIcon: MutableState<Boolean>) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colorScheme.primary
    )
    var searchText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.shadow(10.dp, RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp))) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 43.dp)) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    singleLine = true,
                    leadingIcon = {
                        IconButton(onClick = {
                            isVisibleSearchIcon.value = true
                            keyboardController?.hide()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Search Icon",
                                tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                            )
                        }
                    },
                    placeholder = { Text(text = "Search") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = focusRequester)
                        .onFocusChanged {
                            if (it.isFocused) {
                                keyboardController?.show()
                            } else {
                                keyboardController?.hide()
                            }
                        }
                )
                val suggestionButtonsInfo = suggestionForButtons()
                Divider(thickness = 0.5.dp)
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(8.dp)
                    ) {
                        FlowRow {
                            suggestionButtonsInfo.forEach {
                                SuggestionButton(title = it.title, icon = it.icon)
                            }
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    BackHandler {
        isVisibleSearchIcon.value = true
        keyboardController?.hide()
    }
}


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun HomeScreenContent(
    isVisibleSearchIcon: MutableState<Boolean>,
    sugestionButtonsInfo: List<SuggestionInfo>,
    navController: NavHostController,
) {
    val coroutineScope = rememberCoroutineScope()
    val titles = listOf("Chats", "Status", "Calls")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        3
    }
    val fancyIndicator = @Composable { tabPositions: List<TabPosition> ->
        IndicatorHome(index = pagerState, tabPositions = tabPositions)
    }
    Column {
        if (isVisibleSearchIcon.value) {
            TabRowHorizontalPager(
                pagerState,
                fancyIndicator,
                titles,
                coroutineScope,
                navController = navController
            )
        }
        SuggestionButtonsVisibility(isVisibleSearchIcon, sugestionButtonsInfo)
    }
}

@Composable
fun SuggestionButtonsVisibility(
    isVisibleSearchIcon: MutableState<Boolean>,
    sugestionButtonsInfo: List<SuggestionInfo>
) {
    if (!isVisibleSearchIcon.value) {
        AnimatedContent(
            targetState = !isVisibleSearchIcon.value,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInVertically { height -> height } + fadeIn()).togetherWith(
                        slideOutVertically { height -> -height } + fadeOut())
                } else {
                    (slideInVertically { height -> -height } + fadeIn()).togetherWith(
                        slideOutVertically { height -> height } + fadeOut())
                }.using(
                    SizeTransform(clip = false)
                )
            }
        ) {
            it.not()
            SuggestionButtons(sugestionButtonsInfo)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun TabRowHorizontalPager(
    pagerState: PagerState,
    fancyIndicator: @Composable (List<TabPosition>) -> Unit,
    titles: List<String>,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        indicator = fancyIndicator
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index, animationSpec = tween(easing = EaseInOutCirc))
                    }
                },
                text = {
                    Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = true,
        reverseLayout = false,
        contentPadding = PaddingValues(0.dp),
        beyondBoundsPageCount = 0,
        pageSize = PageSize.Fill,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
        key = null,
        pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
            Orientation.Horizontal
        )
    ) { tabIndex ->
        when (tabIndex) {
            0 -> ChatsTabContent(navController = navController)
            1 -> StatusTabContent(navController = navController)
            2 -> CallsTabContent(navController = navController)
        }
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    actionsButton: ImageVector? = null,
    onClickAction: () -> Unit,
    actionVisible: Boolean = false,
    navigationButton: ImageVector? = null,
    onClickNavigation: () -> Unit = {},
    navigationVisible: Boolean = false,
    onValueChange: (String) -> Unit = {},
) {
    Row(Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            value = "",
            onValueChange = onValueChange,
            placeholder = { Text(text = "Search") },
            leadingIcon = {
                if (navigationVisible) {
                    IconButton(onClick = { onClickAction() }) {
                        Icon(
                            imageVector = actionsButton ?: Icons.Rounded.Search,
                            contentDescription = "Search Button",
                            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    }
                }
            },
            trailingIcon = {
                if (actionVisible) {
                    IconButton(onClick = { onClickAction() }) {
                        Icon(
                            imageVector = actionsButton ?: Icons.Rounded.Mic,
                            contentDescription = "Mic Button",
                            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                focusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                errorLabelColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                placeholderColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                textColor = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            )
        )
    }
}

@Composable
fun CallsTabContent(navController: NavHostController) {
    OhyeTheme {
        LazyColumn {
            itemsIndexed(contacts) { index, contact ->
                when (index) {
                    0 -> {
                        CallsHeader()
                    }

                    else -> {
                        ContactItem(contact = contact, isHomeScreen = true)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallsHeader() {
    val title = "Create call link"
    val description = "Share a link for your, Ohye call"
    val leadingIcon = Icons.Outlined.Link
    val leadingIconSize = 25.dp
    Column {
        ListItem(
            modifier = Modifier.clickable { },
            headlineText = { Text(text = title) },
            supportingText = {
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                )
            },
            leadingContent = {
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(0.7f), CircleShape)
                        .size(50.dp)
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .size(leadingIconSize)
                            .rotate(135f),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
        )
        Divider()
        Text(
            text = "Recent",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
        )
    }

}

@Composable
fun StatusTabContent(navController: NavHostController) {

    OhyeTheme {
        LazyColumn {
            itemsIndexed(contacts) { index, contact ->
                when (index) {
                    0 -> {
                        StatusHeader()
                        Text(
                            text = "Recent updates",
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
                        )
                    }

                    else -> {
                        ContactItem(contact = contact)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, isHomeScreen: Boolean = false) {
    val auth = FirebaseAuth.getInstance()
    if (isHomeScreen) {
        if (contact.profilePicture.isNullOrEmpty()) {
            ProfileInformation(
                title = contact.name,
                description = contact.lastMessage,
                leadingIcon = Icons.Rounded.AccountCircle,
                leadingIconSize = 45.dp,
                bottomDivider = false
            )
        } else {
            ProfileInformation(
                title = contact.name,
                description = contact.lastMessage,
                leadingImageSize = 45.dp,
                leadingImage = contact.profilePicture,
                bottomDivider = false
            )
        }
    } else {
        if (contact.profilePicture.isNullOrEmpty()) {
            ProfileInformation(
                title = contact.name,
                description = contact.status,
                leadingIcon = Icons.Rounded.AccountCircle,
                leadingIconSize = 45.dp,
                bottomDivider = false
            )
        } else {
            ProfileInformation(
                title = contact.name,
                description = contact.status,
                leadingImageSize = 45.dp,
                leadingImage = contact.profilePicture,
                bottomDivider = false
            )
        }
    }
}

@Composable
fun StatusHeader() {
    val auth = FirebaseAuth.getInstance()
    val image = auth.currentUser?.photoUrl.toString()
    ProfileInformation(
        title = "My Status",
        description = "Tap to add status update",
        leadingImage = image,
        leadingIconBottom = Icons.Rounded.AddCircle,
        leadingImageSize = 45.dp,
        leadingIconBottomSize = 25.dp
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun SuggestionButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Surface(
        Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.inverseSurface.copy(0.1f),
    ) {
        Row(
            Modifier
                .padding(paddingValues = PaddingValues(8.dp)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(0.7f)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    isDropdownExpanded: MutableState<Boolean>,
    isVisibleSearchIcon: MutableState<Boolean>,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    val name: String = stringResource(id = R.string.app_name)
    val searchQuery = rememberSaveable { mutableStateOf("") }
    Surface {
        TopAppBar(
            title = {
                HomeTitle(name)
            },
            actions = {
                DropMenu(isDropdownExpanded, navController, homeMenuItems)
                ActionIfSearchVisible(isVisibleSearchIcon, isDropdownExpanded, navController)
            },
            navigationIcon = {
                NavigationIfSearchVisible(isVisibleSearchIcon, navController)
            },
            colors = homeTopBarColors(isVisibleSearchIcon),
            scrollBehavior = scrollBehavior,
            modifier = modifier
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun homeTopBarColors(isVisibleSearchIcon: MutableState<Boolean>) =
    TopAppBarDefaults.mediumTopAppBarColors(
        containerColor =  MaterialTheme.colorScheme.primary,
        navigationIconContentColor = if (isVisibleSearchIcon.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        titleContentColor = if (isVisibleSearchIcon.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = if (isVisibleSearchIcon.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    )

@Composable
fun NavigationIfSearchVisible(
    isVisibleSearchIcon: MutableState<Boolean>,
    navController: NavHostController
) {
    if (!isVisibleSearchIcon.value) {
        IconButton(onClick = {
            isVisibleSearchIcon.value = !isVisibleSearchIcon.value
            navController.popBackStack()
        }) {
            Icon(
                imageVector = Icons.Outlined.ArrowBack,
                contentDescription = "Back Button",
                tint = MaterialTheme.colorScheme.onSurface.copy(0.5f),
            )
        }
    }
}

@Composable
fun HomeTitle(
    name: String,
) {
    TitleSearchVisible(name)
}


fun checkAndRequestCameraPermission(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
        // Use location because permissions are already granted
    } else {
        // Request permissions
        launcher.launch(permissions)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ActionIfSearchVisible(
    isVisibleSearchIcon: MutableState<Boolean>,
    isDropdownExpanded: MutableState<Boolean>,
    navController: NavHostController
) {
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
        )
    )
    val context = LocalContext.current
    if (isVisibleSearchIcon.value) {
        IconButton(onClick = {
            if (permissionState.allPermissionsGranted) {
                Toast.makeText(context, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                permissionState.launchMultiplePermissionRequest()
            }
            navController.navigate(Screens.CameraScreen.route) {
                launchSingleTop = true
            }
        }) {
            Icon(
                imageVector = Icons.Outlined.CameraAlt,
                contentDescription = "Camera",
                modifier = Modifier.size(25.dp)
            )
        }
        Spacer(Modifier.width(2.dp))
        IconButton(onClick = {
            isVisibleSearchIcon.value = !isVisibleSearchIcon.value
        }) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(25.dp)
            )
        }
        Spacer(Modifier.width(2.dp))
        IconButton(onClick = { isDropdownExpanded.value = !isDropdownExpanded.value }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "Drop Down Menu Icon",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FloatingButton(isVisibleSearchIcon: MutableState<Boolean>, navController: NavHostController) {
    val titleVisible = rememberSaveable {
        mutableStateOf(true)
    }
    val isVisiblePermission = remember {
        mutableStateOf(false)
    }
    AskContactPermission(isVisiblePermission, navController)
    val permissionState =
        rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    if (isVisibleSearchIcon.value) {
        val transition = remember {
            MutableTransitionState(titleVisible.value)
        }
        Surface(
            color = MaterialTheme.colorScheme.primary.copy(0.7f),
            shape = CircleShape,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            tonalElevation = 8.dp,
            modifier = Modifier
                .clickable {
                    titleVisible.value = !titleVisible.value
                    when (permissionState.status) {
                        PermissionStatus.Granted -> {
                            isVisiblePermission.value = false
                            navController.navigate(Screens.SelectContactScreen.route) {
                                launchSingleTop = true
                            }
                        }

                        is PermissionStatus.Denied -> {
                            isVisiblePermission.value = true
                        }
                    }
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Message,
                    contentDescription = "Message",
                )
                if (titleVisible.value) {
                    AnimatedVisibility(
                        visibleState = transition,
                        enter = slideInHorizontally(
                            animationSpec = tween(
                                3000,
                                easing = EaseInOutCirc
                            )
                        ),
                        exit = slideOutHorizontally(
                            animationSpec = tween(
                                3000,
                                easing = EaseOut
                            )
                        )
                    ) {
                        Text(
                            text = "Message",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SuggestionButtons(suggestionButtonsInfo: List<SuggestionInfo>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Divider(thickness = 0.5.dp)
        FlowRow(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(paddingValues = PaddingValues(8.dp))
        ) {
            suggestionButtonsInfo.forEach {
                SuggestionButton(title = it.title, icon = it.icon)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChatsTabContent(navController: NavHostController) {
    val context = LocalContext.current
    val permissionStatus =
        rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    when (permissionStatus.status) {
        PermissionStatus.Granted -> {
            val contacts = readContacts(context)
            val contactsOnOhye = getContactsOnOhye(contacts)
            if (contacts.isNotEmpty()) {
                ChatsContent(contacts, navController = navController)
            } else {
                EmptyContactsContent()
            }
        }

        is PermissionStatus.Denied -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        imageVector = Icons.Rounded.Contacts,
                        contentDescription = "Contacts",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(Modifier.height(40.dp))
                Text(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    text = "To help you message friends and family on Ohye, allow Ohye access to your contacts. Tap Settings > Permissions, and turn Contacts on.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(20.dp))
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    context.startActivity(intent)
                }) {
                    Text(text = "Settings")
                }
            }
        }
    }
}

fun getContactsOnOhye(contacts: List<Contact>): List<Contact> {
    val database = FirebaseDatabase.getInstance()
    val contactsOnOhye = mutableListOf<Contact>()
    database.getReference("users").addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (contact in contacts) {
                if (contact.phoneNumber?.let { snapshot.hasChild(it) } == true
                    || contact.email?.let { snapshot.hasChild(it) } == true
                ) {
                    contactsOnOhye.add(contact)
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("ChatsTabContent", "Error: ${error.message}")
        }
    })
    return contactsOnOhye
}


@Composable
@Preview(showBackground = true)
fun EmptyContactsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Invite your friends", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "None of your contacts are using Ohye. Use the button below to invite them to Ohye.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Invite a friend", style = MaterialTheme.typography.labelLarge)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Chat with your friends who use Ohye on iphone or Android",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.8f)
        )
    }
}

@Composable
fun ChatsContent(contacts: List<Contact>, navController: NavHostController) {
    LazyColumn {
        items(contacts) { contact ->
            ChatRowItem(contact, onClick = {
                navController.navigate(Screens.ChatScreen.route) {
                    launchSingleTop = true
                }
            })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ChatRowItem(
    contact: Contact = Contact("John Doe", "Hello", "12:00 PM", null, userId = "1"),
    onClick: () -> Unit = {},
    profileIconOnClick: () -> Unit = {}
) {
    val image = contact.profilePicture
    val name = contact.name.ifEmpty { "Name" }
    val lastMessage = contact.lastMessage.ifEmpty { "Hello how are you?" }
    val lastMessageTime = contact.lastMessageTime.ifEmpty { "12:00 PM" }
    val imageSize = 60.dp
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(imageSize),
            onClick = { profileIconOnClick() }
        ) {
            if (!image.isNullOrEmpty())
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier.size(imageSize),
                )
            else {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(imageSize),
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.3f)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = lastMessageTime,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lastMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AskContactPermission(isVisible: MutableState<Boolean>, navController: NavHostController) {
    val context = LocalContext.current
    val permissionState =
        rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    var contactPermissionGranted by remember { mutableStateOf(permissionState.status == PermissionStatus.Granted) }
    if (isVisible.value) {
        AlertDialog(
            onDismissRequest = {
                isVisible.value = false
            },
            title = {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    text = "To help you message friends and family on Ohye, allow Ohye access to your contacts. Tap Settings > Permissions, and turn Contacts on."
                )
            },
            confirmButton = {
                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    contactPermissionGranted = isGranted
                    isVisible.value = false
                    navController.navigate(Screens.SelectContactScreen.route) {
                        launchSingleTop = true
                    }
                }
                TextButton(onClick = {
                    if (!contactPermissionGranted) {
                        launcher.launch(android.Manifest.permission.READ_CONTACTS)
                    }
                }) {
                    Text(text = "Continue", style = MaterialTheme.typography.labelLarge)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Contacts,
                    contentDescription = "Contacts",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                )
            },
            dismissButton = {
                TextButton(onClick = {
                    isVisible.value = false
                }) {
                    Text(text = "Not now", style = MaterialTheme.typography.labelLarge)
                }
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
fun TitleSearchNotVisible(searchQuery: MutableState<String>) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboardController?.show()
                }
            },
        value = searchQuery.value,
        onValueChange = { enteredValue -> searchQuery.value = enteredValue },
        placeholder = {
            Text(
                text = "Search..",
                style = MaterialTheme.typography.labelLarge
            )
        },
        maxLines = 1,
        textStyle = MaterialTheme.typography.labelLarge,
        trailingIcon = {
            if (searchQuery.value.isNotEmpty()) {
                IconButton(onClick = { searchQuery.value = "" }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Clear Text Button",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
fun TitleSearchVisible(name: String) {
    Text(
        text = name,
        style = TextStyle(
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
        )
    )
}

@Composable
fun DropMenu(
    isDropdownExpanded: MutableState<Boolean> = mutableStateOf(true),
    navController: NavHostController,
    menuItems: List<MenuItem> = homeMenuItems
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = isDropdownExpanded.value,
        onDismissRequest = { isDropdownExpanded.value = false },
        properties = PopupProperties(),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 0.dp, end = 5.dp)
    ) {
        menuItems.forEach {
            DropdownMenuItem(
                modifier = Modifier.padding(top = 2.dp, start = 8.dp, bottom = 2.dp, end = 16.dp),
                onClick = {
                    Toast.makeText(context, "${it.title} clicked!", Toast.LENGTH_SHORT).show()
                    isDropdownExpanded.value = !isDropdownExpanded.value
                    if (it.title == "Settings") {
                        navController.navigate(Screens.SettingsScreen.route) {
                            launchSingleTop = true
                        }
                    } else if (it.title == "New group") {
                        navController.navigate(Screens.NewGroupScreen.route) {
                            launchSingleTop = true
                        }
                    }
                },
                enabled = it.enabled,
                leadingIcon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title,
                    )
                },
                text = {
                    Text(
                        text = it.title,
                        style = TextStyle(
                            fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        )
                    )
                },
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onSurface,
                    leadingIconColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        }
    }

}

@Composable
fun suggestionForButtons(): List<SuggestionInfo> {
    return listOf(
        SuggestionInfo(
            title = "Unread",
            icon = Icons.Rounded.MarkChatUnread
        ),
        SuggestionInfo(
            title = "Photos",
            icon = Icons.Rounded.Photo,
        ),
        SuggestionInfo(
            title = "Videos",
            icon = Icons.Rounded.Videocam
        ),
        SuggestionInfo(
            title = "Links",
            icon = Icons.Rounded.Link
        ),
        SuggestionInfo(
            title = "Gifs",
            icon = Icons.Rounded.Gif
        ),
        SuggestionInfo(
            title = "Audio",
            icon = Icons.Rounded.Headphones
        ),
        SuggestionInfo(
            title = "Documents",
            icon = Icons.Rounded.Article
        ),
        SuggestionInfo(
            title = "Polls",
            icon = Icons.Rounded.Equalizer
        ),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IndicatorHome(
    index: PagerState,
    tabPositions: List<TabPosition>,
    tabColors: List<Color> = listOf()
) {
    val selectedTabIndex = index.currentPage
    val colors = if(isSystemInDarkTheme()) tabColors.ifEmpty { listOf(MaterialTheme.colorScheme.onPrimary) } else tabColors.ifEmpty { listOf(MaterialTheme.colorScheme.surface) }
    val transition = updateTransition(selectedTabIndex, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    val indicatorColor by transition.animateColor(label = "") {
        colors[it % colors.size]
    }

    FancyIndicator(
        indicatorColor,
        modifier = Modifier

            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorStart, y = 0.dp)
            .width(indicatorEnd - indicatorStart)
    )
}

@Composable
fun FancyIndicator(indicatorColor: Color, modifier: Modifier) {
    TabRowDefaults.Indicator(modifier, color = indicatorColor, height = 4.dp)
}

// Data

val homeMenuItems = listOf(
    MenuItem(
        title = "New group",
        icon = Icons.Rounded.Group,
        onClick = { /*TODO*/ },
        enabled = true,
        textColor = Color.Gray,
        fontWeight = FontWeight.Normal,
        fontSize = TextUnit.Unspecified,
        iconTint = Color.Gray
    ),
    MenuItem(
        title = "New broadcast",
        icon = Icons.Rounded.BroadcastOnHome,
        onClick = { /*TODO*/ },
        enabled = true,
        textColor = Color.Gray,
        fontWeight = FontWeight.Normal,
        fontSize = TextUnit.Unspecified,
        iconTint = Color.Gray
    ),
    MenuItem(
        title = "Linked devices",
        icon = Icons.Rounded.Devices,
        onClick = { /*TODO*/ },
        enabled = true,
        textColor = Color.Gray,
        fontWeight = FontWeight.Normal,
        fontSize = TextUnit.Unspecified,
        iconTint = Color.Gray
    ),
    MenuItem(
        title = "Starred messages",
        icon = Icons.Rounded.Message,
        onClick = { /*TODO*/ },
        enabled = true,
        textColor = Color.Gray,
        fontWeight = FontWeight.Normal,
        fontSize = TextUnit.Unspecified,
        iconTint = Color.Gray
    ),
    MenuItem(
        title = "Settings",
        icon = Icons.Rounded.Settings,
        onClick = {

        },
        enabled = true,
        textColor = Color.Gray,
        fontWeight = FontWeight.Normal,
        fontSize = TextUnit.Unspecified,
        iconTint = Color.Gray
    ),
)

val contacts = listOf(
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
    Contact(
        name = "John",
        lastMessage = "Hello",
        lastMessageTime = "12:00",
        profilePicture = null
    ),
)


// Data Classes
data class Contact(
    val name: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val profilePicture: String?,
    val number: String? = null,
    val status: String = "Hey there I am using Ohye!",
    val lastSeen: String = "Last seen today at 12:00",
    val isOnline: Boolean = false,
    val userId: String = "",
    val phoneNumber: String? = null,
    val email: String? = null,
)

data class SuggestionInfo(val title: String, val icon: ImageVector)
data class MenuItem(
    val title: String = "Title",
    val icon: ImageVector = Icons.Rounded.Devices,
    val onClick: () -> Unit = {},
    val enabled: Boolean = true,
    val textColor: Color = Color.Gray,
    val fontWeight: FontWeight = FontWeight.Normal,
    val fontSize: TextUnit = 16.sp,
    val iconTint: Color = Color.Gray
)