package de.ljz.questify.feature.quests.presentation.sheets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.NewLabel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.ljz.questify.R
import de.ljz.questify.core.presentation.components.expressive.menu.ExpressiveMenuItem
import de.ljz.questify.core.presentation.components.expressive.settings.ExpressiveSettingsSection
import de.ljz.questify.feature.quests.data.models.QuestCategoryEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SelectCategoryBottomSheet(
    categories: List<QuestCategoryEntity>,
    onCategorySelect: (QuestCategoryEntity) -> Unit,
    onCreateCategory: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var searchText by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    val searchBarState = rememberSearchBarState()

    val filteredLists = categories
        .filter {
            it.text.contains(
                searchText,
                ignoreCase = true
            )
        }

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = {

                },
                expanded = false,
                onExpandedChange = {},
                placeholder = {
                    Text(
                        text = stringResource(R.string.select_category_bottom_sheet_placeholder)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )
        }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                SearchBar(
                    state = searchBarState,
                    inputField = inputField,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                ExpressiveSettingsSection(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    if (searchText.trim().isEmpty() && filteredLists.count() == 0) {
                        ExpressiveMenuItem(
                            title = stringResource(R.string.select_category_bottom_sheet_empty_list_hint),
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null
                                )
                            }
                        )
                    } else {
                        if (filteredLists.count() > 0) {
                            filteredLists
                                .forEach { list ->
                                    ExpressiveMenuItem(
                                        title = list.text,
                                        onClick = {
                                            onCategorySelect(list)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Outlined.Label,
                                                contentDescription = null
                                            )
                                        }
                                    )
                                }
                        } else {
                            ExpressiveMenuItem(
                                title = stringResource(
                                    R.string.select_category_bottom_sheet_create_list,
                                    searchText
                                ),
                                onClick = {
                                    onCreateCategory(searchText)
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Outlined.NewLabel,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}