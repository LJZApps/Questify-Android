package de.ljz.questify.core.compose

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_NO,
  showBackground = true,
)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  showBackground = true,
)
annotation class UIModePreviews
