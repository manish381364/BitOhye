package com.littlebit.bitohye.ui.screens.settings.settingschat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun SelectThemeAlert(showAlertDialog: MutableState<Boolean>, onConfirm: (Boolean) -> Unit = {}) {
    val isDark = isSystemInDarkTheme()
    var selectedTheme by remember { mutableStateOf(isDark) }
    if (showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlertDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(selectedTheme)
                    showAlertDialog.value = false
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAlertDialog.value = false }) {
                    Text(text = "Cancel")
                }
            },
            title = { Text(text = "Choose theme") },
            text = {
                Column {
                    Row(Modifier.fillMaxWidth().clickable { if(selectedTheme) selectedTheme = false }, verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedTheme.not(), onClick = {if(selectedTheme) selectedTheme = false})
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Light")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth().clickable { if(!selectedTheme) selectedTheme = true  }, verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedTheme, onClick = {if(!selectedTheme) selectedTheme = true })
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "Dark")
                    }
                }
            },
        )
    }
}

data class RowItem(
    val leadingIcon: ImageVector? = null,
    val title: String,
    val description: String? = null,
    val trailingIcon: ImageVector? = null,
    val trailingSwitch: Boolean = false,
    val onClickLeadingIcon: () -> Unit = {},
    val onClickTrailingIcon: () -> Unit = {},
    val onSwitch: (Boolean) -> Unit = {},
    val onClick: () -> Unit = {},
)

@Composable
fun DisplayComponent(
    navController: NavHostController,
    title: String,
    rowItems: List<RowItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            rowItems.forEach {
                RowItem(
                    leadingIcon = it.leadingIcon,
                    title = it.title,
                    description = it.description,
                    trailingIcon = it.trailingIcon,
                    trailingSwitch = it.trailingSwitch,
                    navController = navController,
                    onClick = it.onClick
                )
            }
        }
    }
}

@Composable
fun RowItem(
    leadingIcon: ImageVector? = null,
    onClickLeadingIcon: () -> Unit = {},
    trailingIcon: ImageVector? = null,
    onClickTrailingIcon: () -> Unit = {},
    trailingSwitch: Boolean = false,
    title: String, description: String? = null,
    navController: NavHostController,
    onClick: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable { onClick() }
        , verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
            }
        }
        Column(Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )
            }
        }
        if (trailingIcon != null) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = trailingIcon, contentDescription = "Icon")
            }
        } else if (trailingSwitch) {
            val checked = remember { mutableStateOf(false) }
            Switch(checked = checked.value, onCheckedChange = { checked.value = it })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsChatTopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                "Chats",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(0.7f)
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary.copy(0.7f)
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}
