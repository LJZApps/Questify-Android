package de.ljz.questify.ui.features.profile.edit_profile

import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.util.NavBarConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    viewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.updateProfilePicture(uri.toString())
    }

    LaunchedEffect(Unit) {
        NavBarConfig.transparentNavBar = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.edit_profile_screen_title)) },
                actions = {
                    TextButton(
                        onClick = {
                            if (uiState.pickedProfilePicture && uiState.profilePictureUrl != "null") {
                                val profilePicture = uiState.profilePictureUrl.let {
                                    viewModel.saveImageToInternalStorage(
                                        context = context,
                                        uri = Uri.parse(it)
                                    )
                                }
                                viewModel.saveProfile(profilePicture?: "")
                            } else if (uiState.profilePictureUrl.isNotEmpty() && uiState.profilePictureUrl != "null") {
                                viewModel.saveProfile(uiState.profilePictureUrl)
                            } else {
                                viewModel.saveProfile("")
                            }

                            navController.navigateUp()
                        }
                    ) {
                        Text(stringResource(R.string.save))
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
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
                OutlinedTextField(
                    value = uiState.displayName,
                    onValueChange = viewModel::updateDisplayName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text(stringResource(R.string.text_field_display_name)) },
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                    )
                )

                // About me
                OutlinedTextField(
                    value = uiState.aboutMe,
                    onValueChange = viewModel::updateAboutMe,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text(stringResource(R.string.text_field_about_me)) },
                    shape = RoundedCornerShape(10.dp),
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