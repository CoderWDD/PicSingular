package com.example.picsingular.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Grey800,
    background = Grey800,
    primaryVariant = Grey200,
    secondary = Grey900,
    secondaryVariant = Grey600,
    surface = Grey800,
    error = Red600,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    background = Color.White,
    primaryVariant = Grey200,
    secondary = Grey600,
    secondaryVariant = Grey900,
    surface = White,
    error = Red600,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black,
    onError = Color.White,
)

@Composable
fun PicSingularTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}