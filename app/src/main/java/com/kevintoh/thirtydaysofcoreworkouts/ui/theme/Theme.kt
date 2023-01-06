package com.kevintoh.thirtydaysofcoreworkouts.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_on_surface,
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_on_primary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_on_secondary
)

private val LightColorPalette = lightColors(
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_on_surface,
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_on_primary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_on_secondary
)

@Composable
fun ThirtyDaysOfCoreWorkoutsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
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