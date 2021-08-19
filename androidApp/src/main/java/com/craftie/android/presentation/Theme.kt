import android.graphics.Color.rgb
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.craftie.android.presentation.*

private val DarkColorPalette = darkColors(
    primary = primaryColor,
    primaryVariant = primaryVariantColor,
    secondary = secondaryColor
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = primaryVariantColor,
    secondary = secondaryColor,
    background = backgroundColor,
    surface = surfaceColor,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

val yellow = Color(rgb(242, 201, 76))
val gray = Color(rgb(130, 130, 130))
val lightGray = Color(rgb(242, 242, 242))
val darkGray = Color(rgb(79, 79, 79))
val orange = Color(rgb(242, 153, 74))
val lightRed = Color(rgb(255, 227, 222))

@Composable
fun CraftieTheme(
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
        typography = typography,
        shapes = shapes,
        content = content
    )
}
