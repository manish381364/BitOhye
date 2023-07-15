package com.littlebit.bitohye.ui.screens.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreview(navController: NavHostController) {
    val ambientContext = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(ambientContext) }
    val preview = remember { Preview.Builder().build() }
    val cameraSelector = remember { CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build() }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
        val previewView = PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, preview)
            preview.setSurfaceProvider(previewView.surfaceProvider)
        }, ContextCompat.getMainExecutor(context))

        previewView
    })
}





