package de.ljz.questify.ui.features.settings.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.ljz.questify.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsFeedbackScreen(
    viewModel: SettingsFeedbackViewModel = hiltViewModel(),
    mainNavController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.settings_help_screen_provide_feedback_title)) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            mainNavController.navigateUp()
                        },
                        shapes = IconButtonDefaults.shapes()
                    ) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    viewModel.sendFeedback(
                        onSuccess = {
                            mainNavController.navigateUp()
                        },
                        onFailure = { message->

                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(12.dp)
                    .imePadding(),
                enabled = uiState.value.messageTitle.isNotEmpty(),
                shapes = ButtonDefaults.shapes()
            ) {
                Text(text = stringResource(R.string.settings_help_screen_send_feedback))
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.value.messageTitle,
                    onValueChange = { viewModel.updateMessageTitle(it) },
                    label = { Text(stringResource(R.string.text_field_quest_title) + " *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                    )
                )

                OutlinedTextField(
                    value = uiState.value.messageDescription,
                    onValueChange = { viewModel.updateMessageDescription(it) },
                    label = { Text(stringResource(R.string.text_field_quest_note)) },
                    modifier = Modifier.fillMaxWidth(),
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