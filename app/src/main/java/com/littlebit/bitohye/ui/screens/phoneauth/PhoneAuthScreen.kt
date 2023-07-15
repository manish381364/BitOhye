package com.littlebit.bitohye.ui.screens.phoneauth

import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.littlebit.bitohye.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PhoneAuthContent(
    country: String = "Country[]Code",
    navController: NavHostController = rememberNavController()
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var smsCode by rememberSaveable { mutableStateOf("") }
    var code by remember { mutableStateOf(country.split("[]")[1].removePrefix("+")) }
    val context = LocalContext.current
    val verificationID = rememberSaveable { mutableStateOf("") }
    val message = rememberSaveable { mutableStateOf("") }
    val countryName = remember { mutableStateOf(country.split("[]")[0]) }
    val codeSent = rememberSaveable { mutableStateOf(false) }
    val countryListButton = rememberSaveable { mutableStateOf(true) }
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    val codeFieldRequester = FocusRequester()
    val phoneNumberFieldRequester = FocusRequester()
    val verifyCodeFieldRequester = FocusRequester()
    val verifyingSms = rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ohye will need to verify your phone number.")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = countryListButton.value,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {
                        navController.navigate(Screens.CountryListScreen.route) {
                            launchSingleTop = true
                        }
                    }
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(countryName.value)
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "ArrowDropDown",
                )
            }
            Divider(Modifier.fillMaxWidth(0.5f), color = Color(0xFF128C7E), thickness = 0.6.dp)
            // get current country code
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "ArrowDropDown",
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextField(
                    value = code,
                    onValueChange = { code = it },
                    modifier = Modifier
                        .width(90.dp)
                        .focusRequester(codeFieldRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        setCountryName(
                            code,
                            countryName
                        )
                        phoneNumberFieldRequester.requestFocus()
                    }),
                    maxLines = 1,
                    colors = TextFieldDefaults
                        .textFieldColors(
                            cursorColor = Color(0xFF128C7E),
                            focusedIndicatorColor = Color(0xFF128C7E),
                            unfocusedIndicatorColor = Color(0xFF128C7E),
                            disabledIndicatorColor = Color(0xFF128C7E),
                            containerColor = Color.Transparent
                        )
                )
                Spacer(Modifier.width(10.dp))
                TextField(
                    value = phoneNumber, onValueChange = { phoneNumber = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyBoardController?.hide()
                    }),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color(0xFF128C7E),
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFF128C7E),
                        unfocusedIndicatorColor = Color(0xFF128C7E),
                        disabledIndicatorColor = Color(0xFF128C7E),
                    ),
                    modifier = Modifier.focusRequester(phoneNumberFieldRequester),
                )
                LaunchedEffect(key1 = Unit, block = {codeFieldRequester.requestFocus()})
            }
            AnimatedVisibility(visible = codeSent.value) {
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = smsCode, onValueChange = { smsCode = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = { keyBoardController?.hide() }),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color(0xFF128C7E),
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFF128C7E),
                        unfocusedIndicatorColor = Color(0xFF128C7E),
                        disabledIndicatorColor = Color(0xFF128C7E),
                    ),
                    placeholder = { Text("Enter Verification Code") },
                    modifier = Modifier.fillMaxWidth(0.7f).focusRequester(verifyCodeFieldRequester)
                )
                LaunchedEffect(key1 = Unit, block = {verifyCodeFieldRequester.requestFocus()})
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text("Carrier charges may apply")
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(onClick = {
                    if (phoneNumber.isEmpty()) {
                        Toast.makeText(context, "Please enter phone number..", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val number = "+${code}${phoneNumber}"
                        // on below line calling method to generate verification code.
                        sendVerificationCode(number, mAuth, context as Activity, callbacks)
                        Toast.makeText(context, "Verification Code Sent..", Toast.LENGTH_SHORT)
                            .show()
                        codeSent.value = true
                    }
                },
                    enabled = !codeSent.value,
                ) {
                    Text(text = "Next")
                }
                AnimatedVisibility(visible = codeSent.value) {
                    Button(modifier = Modifier.padding(start = 100.dp), enabled = !verifyingSms.value, onClick = {
                        if (smsCode.isEmpty()) {
                            Toast.makeText(
                                context,
                                "Please enter verification code..",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            // on below line we are calling a method to verify our code.
                            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                                verificationID.value, smsCode
                            )
                            // on below line signing within credentials.
                            verifyingSms.value = true
                            signInWithPhoneAuthCredential(
                                credential,
                                mAuth,
                                context as Activity,
                                context,
                                message,
                                navController,
                                verifyingSms
                            )
                        }
                    }) {
                        Text(text = "Verify")
                    }
                    if(verifyingSms.value){
                        CircularProgressIndicator(modifier = Modifier.padding(start = 10.dp))
                    }
                }
            }
        }
    }
    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            // on below line updating message
            // and displaying toast message
            message.value = "Verification successful"
            Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            // on below line displaying error as toast message.
            message.value = "Fail to verify user : \n" + p0.message
            Toast.makeText(context, "Verification failed..", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
            // this method is called when code is send
            super.onCodeSent(verificationId, p1)
            verificationID.value = verificationId
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PhoneAuthScreen(
    navController: NavHostController = rememberNavController(),
    country: MutableState<String> = mutableStateOf("Country[]Code")
) {
    Scaffold(
        topBar = {
            PhoneAuthTopBar()
        }
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            PhoneAuthContent(country.value, navController)
        }
    }
}










