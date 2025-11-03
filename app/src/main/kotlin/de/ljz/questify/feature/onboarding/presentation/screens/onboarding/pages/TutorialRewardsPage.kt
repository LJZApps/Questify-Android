package de.ljz.questify.feature.onboarding.presentation.screens.onboarding.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.ljz.questify.R
import de.ljz.questify.feature.onboarding.presentation.components.TutorialRewardComponent

@Composable
fun TutorialRewardsPage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .padding(top = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Belohnungen verdienen, aufsteigen!",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Schließe Quests ab, um Punkte und Erfahrung (XP) zu sammeln. Sammle XP, um aufzusteigen und neue Funktionen oder Belohnungen freizuschalten!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            lineHeight = 28.sp,
            fontSize = 18.sp
        )

        TutorialRewardComponent(
            modifier = Modifier.fillMaxWidth(),
            title = "Punkte",
            description = "Für jede abgeschlossene Quests",
            icon = painterResource(R.drawable.coins)
        )

        TutorialRewardComponent(
            modifier = Modifier.fillMaxWidth(),
            title = "Erfahrung (XP)",
            description = "Bringt dich dem nächsten Level näher",
            icon = painterResource(R.drawable.ic_sparkles)
        )

        TutorialRewardComponent(
            modifier = Modifier.fillMaxWidth(),
            title = "Dein Rang",
            description = "Schalte exklusive Belohnungen frei",
            icon = painterResource(R.drawable.ic_trending_up)
        )
    }
}