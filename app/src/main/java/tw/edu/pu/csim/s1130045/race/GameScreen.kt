package tw.edu.pu.csim.s1130045.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(gameViewModel: GameViewModel) {

    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // 1. 遊戲標題 (置頂)
        Text(
            text = "賽馬遊戲(作者：資管二A 411300459 吳岱威) ", // 讀取 strings.xml 中的 title
            color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )


        // 2. 賽馬畫布
        Canvas(modifier = Modifier.fillMaxSize()) {
            // (已刪除 drawCircle)

            // 繪製馬匹
            for (i in 0..2) {
                val horse = gameViewModel.horses.getOrNull(i) // 使用 getOrNull 防止越界
                horse?.let {
                    val imageIndex = it.number.coerceIn(0, imageBitmaps.lastIndex)
                    drawImage(
                        image = imageBitmaps[imageIndex],
                        dstOffset = IntOffset(
                            it.horseX,
                            it.horseY
                        ),
                        dstSize = IntSize(200, 200) // 馬匹圖片大小
                    )
                }
            }
        }

        // 3. 顯示勝利文字 (置中)
        if (gameViewModel.winningHorseIndex != -1) {
            Text(
                text = "第${gameViewModel.winningHorseIndex}馬獲勝！",
                color = Color.Black,
                fontSize = 40.sp,
                modifier = Modifier.align(Alignment.Center) // 讓文字在畫面中央
            )
        }

        // 4. 開始/結束 按鈕 (置底)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Button(onClick = { gameViewModel.startGame() }) {
                Text(text = "開始")
            }
            Spacer(modifier = Modifier.width(16.dp)) // 按鈕間距
            Button(onClick = { gameViewModel.stopGame() }) {
                Text(text = "結束")
            }
        }
    }
}