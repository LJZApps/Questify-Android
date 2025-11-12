package de.ljz.questify.feature.profile.presentation.screens.edit_profile

import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.text_fields.AppOutlinedTextField
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    EditProfileScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is EditProfileUiEvent.NavigateUp -> onNavigateUp()

                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileScreen(
    uiState: EditProfileUiState,
    onUiEvent: (EditProfileUiEvent) -> Unit
) {
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onUiEvent.invoke(EditProfileUiEvent.UpdateProfilePicture(uri.toString()))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit_profile_screen_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (uiState.pickedProfilePicture && uiState.profilePictureUrl != "null") {
                                val profilePicture: String? = uiState.profilePictureUrl.let {
                                    val inputStream: InputStream? =
                                        context.contentResolver.openInputStream(it.toUri())
                                    // Der use-Block gibt den Pfad oder null zurück, was dann vom let-Block zurückgegeben wird.
                                    inputStream?.use { input ->
                                        val fileName = "profile_${UUID.randomUUID()}.jpg"
                                        val file = File(
                                            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                                            fileName
                                        )

                                        FileOutputStream(file).use { output ->
                                            input.copyTo(output)
                                        }

                                        file.absolutePath
                                    }
                                }
                                onUiEvent.invoke(
                                    EditProfileUiEvent.SaveProfile(
                                        profilePicture ?: ""
                                    )
                                )
                            } else if (uiState.profilePictureUrl.isNotEmpty() && uiState.profilePictureUrl != "null") {
                                onUiEvent.invoke(EditProfileUiEvent.SaveProfile(uiState.profilePictureUrl))
                            } else {
                                onUiEvent.invoke(EditProfileUiEvent.SaveProfile(""))
                            }

                            onUiEvent.invoke(EditProfileUiEvent.NavigateUp)
                        },
                    ) {
                        Text(stringResource(R.string.save))
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(EditProfileUiEvent.NavigateUp)
                        },
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.profilePictureUrl.isNotEmpty() && uiState.profilePictureUrl != "null") {
                        AsyncImage(
                            model = uiState.profilePictureUrl,
                            contentDescription = "Profilbild",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profilbild",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                // Display name
                AppOutlinedTextField(
                    value = uiState.displayName,
                    onValueChange = {
                        onUiEvent.invoke(EditProfileUiEvent.UpdateDisplayName(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text(stringResource(R.string.text_field_display_name)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                    )
                )

                // About me
                AppOutlinedTextField(
                    value = uiState.aboutMe,
                    onValueChange = {
                        onUiEvent.invoke(EditProfileUiEvent.UpdateAboutMe(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text(stringResource(R.string.text_field_about_me)) },
                    minLines = 2,
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                    )
                )
            }
        }
    )
}