package com.example.cineapp.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cineapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.cineapp.ui.theme.CineAppTheme

@Composable
fun CustomTopAppBar(
    logoImage: Painter,
    labelScreen: String,
    downloadIcon: Painter,
    searchIcon: Painter,
    onDownloadClick: () -> Unit,
    onSearchClick: () -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .width(31.dp)
                        .height(47.dp),
                    painter = logoImage, contentDescription = "Image Logo"
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = labelScreen,
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .width(31.dp)
                        .height(47.dp),
                    painter = downloadIcon, contentDescription = "Image Logo"
                )

                Spacer(modifier = Modifier.size(8.dp))

                Image(
                    modifier = Modifier
                        .width(31.dp)
                        .height(47.dp),
                    painter = searchIcon, contentDescription = "Image Logo"
                )
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
private fun CustomTopAppBarPreview() {
    CineAppTheme {
        Column(
            modifier = Modifier
                .background(Color.Black)
        ) {
            CustomTopAppBar(
                logoImage = painterResource(R.drawable.logo),
                labelScreen = "Ínicio",
                downloadIcon = painterResource(R.drawable.ic_download),
                searchIcon = painterResource(R.drawable.ic_search),
                onDownloadClick = { },
                onSearchClick = { }
            )
        }

    }

}