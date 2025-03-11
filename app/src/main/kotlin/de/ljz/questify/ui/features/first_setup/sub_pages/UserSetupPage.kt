package de.ljz.questify.ui.features.first_setup.sub_pages

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import de.ljz.questify.R
import de.ljz.questify.util.trimToNull

@Composable
fun UserSetupPage(
    displayName: String,
    aboutMe: String,
    imageUri: Uri?,
    onDisplayNameChange: (String) -> Unit,
    onAboutMeChange: (String) -> Unit,
    requestImagePicker: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var animationVisible by remember { mutableStateOf(false) }

    // Animation starten
    androidx.compose.runtime.LaunchedEffect(Unit) {
        animationVisible = true
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Dekorative Hintergrundformen
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(200.dp, (-100).dp)
                .blur(70.dp)
                .background(
                    MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                    CircleShape
                )
        )

        Box(
            modifier = Modifier
                .size(250.dp)
                .offset((-120).dp, 500.dp)
                .blur(60.dp)
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                    CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            AnimatedVisibility(
                visible = animationVisible,
                enter = fadeIn(tween(800)) + slideInVertically(
                    initialOffsetY = { -50 },
                    animationSpec = tween(800)
                )
            ) {
                Text(
                    text = stringResource(R.string.user_setup_page_title),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            AnimatedVisibility(
                visible = animationVisible,
                enter = fadeIn(tween(1000)) + slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(1000)
                )
            ) {
                ProfilePictureSelector(
                    imageUri = imageUri,
                    requestImagePicker = requestImagePicker
                )
            }

            AnimatedVisibility(
                visible = animationVisible,
                enter = fadeIn(tween(1200)) + slideInVertically(
                    initialOffsetY = { 100 },
                    animationSpec = tween(1200)
                )
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Name Input
                        OutlinedTextField(
                            value = displayName,
                            onValueChange = { onDisplayNameChange(it) },
                            label = { Text(stringResource(R.string.text_field_display_name)) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            /*colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),*/
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words,
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )

                        // Bio Input
                        OutlinedTextField(
                            value = aboutMe,
                            onValueChange = { onAboutMeChange(it) },
                            label = { Text(stringResource(R.string.text_field_about_me)) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            shape = RoundedCornerShape(16.dp),
                            /*colors = TextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                cursorColor = MaterialTheme.colorScheme.primary
                            ),*/
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfilePictureSelector(
    imageUri: Uri?,
    requestImagePicker: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(180.dp)
            .shadow(20.dp, CircleShape)
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                )
            )
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                    )
                ),
                shape = CircleShape
            )
            .clickable { requestImagePicker() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri.toString().trimToNull() != null && imageUri.toString() != "null") {
            AsyncImage(
                model = imageUri,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Camera-Icon im Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Change profile picture",
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )
            }
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color.White.copy(alpha = 0.9f)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = (-16).dp)
            ) {
                Button(
                    onClick = { requestImagePicker() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = "Foto",
                        modifier = Modifier.padding(start = 8.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}