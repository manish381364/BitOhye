package com.littlebit.bitohye.ui.screens.setprofile

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.PermMedia
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.littlebit.bitohye.navigation.Screens
import com.littlebit.bitohye.ui.screens.homescreen.DropMenu
import com.littlebit.bitohye.ui.screens.homescreen.MenuItem
import com.littlebit.bitohye.ui.theme.OhyeTheme
import com.littlebit.bitohye.util.User


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun SetProfileScreen(navController: NavHostController = rememberAnimatedNavController()) {
    val context = LocalContext.current
    val showDropDown = remember { mutableStateOf(false) }
    val inputText = remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val photoUrl = auth.currentUser?.photoUrl
    val storageRef = FirebaseStorage.getInstance().reference
    val showNumberField = auth.currentUser?.phoneNumber == null
    val focusRequester = FocusRequester()
    val showPermissionRequest = remember { mutableStateOf(true) }
    val permissionState =
        rememberMultiplePermissionsState(permissions = listOf( Manifest.permission.READ_CONTACTS, Manifest.permission.READ_EXTERNAL_STORAGE))
    OhyeTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Profile info",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary.copy(0.8f)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            showDropDown.value = true
                        }) {
                            Icon(Icons.Rounded.MoreVert, contentDescription = "Help")
                        }
                        DropMenu(
                            navController = navController,
                            isDropdownExpanded = showDropDown,
                            menuItems = listOf(
                                MenuItem(
                                    title = "Help",
                                    onClick = { },
                                    icon = Icons.Rounded.HelpOutline
                                )
                            )
                        )
                    }
                )
            },
        ) {
            Column(
                Modifier.padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Please provide your name and optional profile photo",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                if (photoUrl != null) {
                    val loading = remember { mutableStateOf(true) }
                    if (loading.value) CircularProgressIndicator(modifier = Modifier.size(25.dp))
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = "ProfilePicture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        onLoading = { loading.value = true },
                        onSuccess = { loading.value = false },
                        onError = { loading.value = false }
                    )
                } else {
                    Box(
                        Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable { }
                            .background(MaterialTheme.colorScheme.onSurface.copy(0.1f))
                            .padding(paddingValues = PaddingValues(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Rounded.AddAPhoto, contentDescription = "Camera",
                            modifier = Modifier
                                .size(45.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                TextField(
                    textStyle = MaterialTheme.typography.titleMedium,
                    value = inputText.value,
                    singleLine = true,
                    onValueChange = { inputValue -> inputText.value = inputValue },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.EmojiEmotions, contentDescription = "Edit",
                            tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Type your name here",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .height(50.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .horizontalScroll(rememberScrollState()),
                    keyboardOptions = KeyboardOptions(imeAction = if (showNumberField) ImeAction.Next else ImeAction.Done),
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = inputText.value.isNotEmpty(),
                    onClick = {
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(inputText.value)
                            .build()
                        currentUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = User(
                                        name = inputText.value,
                                        phoneNumber = auth.currentUser?.phoneNumber,
                                        uid = auth.currentUser?.uid,
                                        email = auth.currentUser?.email,
                                        profilePicUrl = auth.currentUser?.photoUrl.toString(),
                                    )
                                    database.reference.child("users").child(auth.currentUser?.uid!!)
                                        .setValue(user).addOnCompleteListener { dataTask ->
                                            if (dataTask.isSuccessful) {
                                                navController.navigate(Screens.HomeScreen.route) {
                                                    launchSingleTop = true
                                                    popUpTo(Screens.SetProfileScreen.route) {
                                                        inclusive = true
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Error: ${dataTask.exception?.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Error: ${task.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Next")
                }
            }
        }

        ReadContactsAlert(showPermissionRequest = showPermissionRequest, permissionState)
    }

    LaunchedEffect(key1 = Unit, block = { focusRequester.requestFocus() })
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReadContactsAlert(
    showPermissionRequest: MutableState<Boolean> = mutableStateOf(true),
    permissionState: MultiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )
) {
    if(!permissionState.allPermissionsGranted && showPermissionRequest.value){
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(text = "Contacts and media")
            },
            text = {
                Text(text = "We need to read your contacts to find your friends")
            },
            confirmButton = {
                Button(onClick = {
                    permissionState.launchMultiplePermissionRequest()
                    showPermissionRequest.value = false
                }) {
                    Text(text = "Allow")
                }
            },
            dismissButton = {
                Button(onClick = { showPermissionRequest.value = false }) {
                    Text(text = "Not now")
                }
            },
            icon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Contacts,
                        contentDescription = "Camera",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Camera",
                        modifier = Modifier.size(25.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Rounded.PermMedia,
                        contentDescription = "Camera",
                        modifier = Modifier.size(35.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                    )
                }
            },
            tonalElevation = 2.dp
        )
    }

}
