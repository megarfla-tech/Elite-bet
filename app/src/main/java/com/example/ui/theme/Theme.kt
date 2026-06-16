package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SportGreen,
    secondary = CyberTeal,
    tertiary = WarningOrange,
    background = DarkBg,
    surface = CardGray,
    onPrimary = Color(0xFF06150D),
    onSecondary = Color(0xFF002025),
    onBackground = LightText,
    onSurface = LightText,
    outline = DarkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = SportGreen,
    secondary = CyberTeal,
    tertiary = WarningOrange,
    background = LightBg,
    surface = LightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF0F172A),
    outline = LightBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force Dark theme by default for elite trading aesthetic
    dynamicColor: Boolean = false, // Disable dynamic colors to preserve our beautiful branded look
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
