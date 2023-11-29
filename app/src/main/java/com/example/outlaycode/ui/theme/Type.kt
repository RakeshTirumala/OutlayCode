package com.example.outlaycode.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.outlaycode.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
val opensans = FontFamily(
    Font(R.font.opensans_regular, FontWeight.Normal),
    Font(R.font.opensans_medium, FontWeight.Medium),
    Font(R.font.opensans_bold, FontWeight.Bold),
    Font(R.font.opensans_semibold, FontWeight.SemiBold)
)
val customStyle: TextStyle
    @Composable
    get() {
        return  TextStyle(
            fontFamily = opensans,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )
    }
val customStyleNormal:TextStyle
    @Composable
    get(){
        return TextStyle(
            fontFamily = opensans,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp
        )
    }

val customStyleForDialog:TextStyle
    @Composable
    get(){
        return TextStyle(
            fontFamily = opensans,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }

val customStyleSmall:TextStyle
    @Composable
    get(){
        return TextStyle(
            fontFamily = opensans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
val customStyleMedium:TextStyle
    @Composable
    get(){
        return TextStyle(
            fontFamily = opensans,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp
        )
    }
