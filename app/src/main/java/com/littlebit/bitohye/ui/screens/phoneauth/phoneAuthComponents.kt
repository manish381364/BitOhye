package com.littlebit.bitohye.ui.screens.phoneauth

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.littlebit.bitohye.R
import com.littlebit.bitohye.navigation.Screens
import com.littlebit.bitohye.util.countryCodesToNames
import java.util.concurrent.TimeUnit

@Composable
fun PhoneAuthTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter your phone number",
            style = TextStyle(
                color = Color(0xFF128C7E),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

fun setCountryName(
    code: String,
    countryName: MutableState<String>,
) {
    if(countryCodesToNames["+$code"] != null) {
        Log.d("COUNTRY TO CODE", "setCountryName: ${countryCodesToNames["+$code"]}  +$code")
        countryName.value = countryCodesToNames["+$code"]!!
    }
}

fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    mAuth: FirebaseAuth,
    activity: Activity,
    context: Context,
    message: MutableState<String>,
    navController: NavHostController,
    verifyingSms: MutableState<Boolean>
) {
    val database = FirebaseDatabase.getInstance()
    val auth = FirebaseAuth.getInstance()
    mAuth.signInWithCredential(credential)
        .addOnCompleteListener(activity) { task ->
            // displaying toast message when
            // verification is successful
            if (task.isSuccessful) {
                message.value = "Verification successful"
                Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
                val user = database.reference.child("users").child(auth.currentUser?.uid!!).toString()
                val usersRef = database.reference.child("users")
                val currentUserUid = auth.currentUser?.uid!!
                usersRef.child(currentUserUid).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // UID exists in the database, navigate to the next screen
                            navController.navigate(Screens.HomeScreen.route) {
                                popUpTo(Screens.PhoneAuthScreen.route) {
                                    inclusive = true
                                }
                            }

                        } else {
                            // UID does not exist in the database, navigate to the profile setup screen
                            navController.navigate(Screens.SetProfileScreen.route) {
                                popUpTo(Screens.PhoneAuthScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle error
                        Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                // Sign in failed, display a message
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code
                    // entered was invalid
                    Toast.makeText(
                        context,
                        "Verification failed.." + (task.exception as FirebaseAuthInvalidCredentialsException).message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
}

fun sendVerificationCode(
    number: String,
    mAuth: FirebaseAuth,
    activity: Activity,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    val options = PhoneAuthOptions.newBuilder(mAuth)
        .setPhoneNumber(number) // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(activity) // Activity (for callback binding)
        .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: ImageVector? = null,
    actionsIcon: ImageVector? = null,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    onNavigation: () -> Unit = { },
    onActions: () -> Unit = { },
) {

    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = {
            if (navigationIcon != null)
                IconButton(onClick = { onNavigation() }) {
                    Icon(navigationIcon, "navigationIcon")
                }
        },
        actions = {
            if (actionsIcon != null)
                IconButton(onClick = { onActions() }) {
                    Icon(actionsIcon, "actionsIcon")
                }
        },
        scrollBehavior = topAppBarScrollBehavior
    )
}

fun makeToast(context: Context, s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun SignInButtons(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                // Signed in successfully, handle the user's details
                Log.d("SUCCESS", "SocialMediaButtons: ${account?.displayName}")
                val firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signInWithCredential(
                    GoogleAuthProvider.getCredential(account?.idToken, null)
                ).addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        Log.d("FIRE SUCCESS", "SocialMediaButtons: ${signInTask.result}")
                        val auth = FirebaseAuth.getInstance()
                        val database = FirebaseDatabase.getInstance()
                        val usersRef = database.reference.child("users")
                        val currentUserUid = auth.currentUser?.uid!!
                        usersRef.child(currentUserUid).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // UID exists in the database, navigate to the next screen
                                     navController.navigate(Screens.HomeScreen.route) {
                                         popUpTo(Screens.PhoneAuthScreen.route) {
                                             inclusive = true
                                         }
                                     }

                                } else {
                                    // UID does not exist in the database, navigate to the profile setup screen
                                    navController.navigate(Screens.SetProfileScreen.route) {
                                        popUpTo(Screens.PhoneAuthScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                                // Handle error
                                Toast.makeText(context, databaseError.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else {
                        Log.d("FIRE ERROR", "SocialMediaButtons: ${signInTask.exception}")
                    }
                }
            } catch (e: ApiException) {
                // Handle sign-in failure
                makeToast(context, "Login Failed")
                Log.d("ERROR", "SocialMediaButtons: ${e.message}")
            }
        }
    }
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
        SignInIconButton(
            text = "Sign in with Google",
            icon = R.drawable.ic_google,
            onClick = {
                Log.d("GoogleButton", "GoogleButton Clicked: Google")
                val signInIntent = googleSignInClient.signInIntent
                googleSignInLauncher.launch(signInIntent)
            }
        )
        SignInIconButton(
            text = "Sign in with Facebook",
            icon = R.drawable.ic_facebook,
            onClick = {
                makeToast(context, "Feature coming soon")
            }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SignInIconButton(text: String = "Description", icon: Int = R.drawable.ic_google, onClick: () -> Unit = {}) {
    IconButton(
        onClick,
        Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface),
    ) {
        AsyncImage(
            model = icon, contentDescription = text,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.surface)
                .clip(CircleShape)
        )
    }
}
