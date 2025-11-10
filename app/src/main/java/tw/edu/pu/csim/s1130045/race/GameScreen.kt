package tw.edu.pu.csim.s1130045.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

// 1. 修正函式簽名：
//    將 (message: String) 改為 (gameViewModel: GameViewModel)
@Composable
fun GameScreen(gameViewModel: GameViewModel) {

    // 2. 將資源載入移到 Composable 函式的頂層
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    // 3. Box 作為背景和容器
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // 4. Canvas 放在 Box 內部，並負責所有繪圖和手勢
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // 5. .pointerInput 只負責手勢偵測
                detectDragGestures { change, dragAmount ->
                    change.consume() // 告訴系統已經處理了這個事件
                    gameViewModel.moveCircle(dragAmount.x, dragAmount.y)
                }
            }
        ) {
            // 6. 這裡是 Canvas 的 onDraw 範圍
            //    所有 "draw" 相關的程式碼都必須放在這裡

            // 繪製圓形
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(100f, 100f)
            )

            // 7. 將繪製馬匹的迴圈移到這裡
            for (i in 0..2) {
                val horse = gameViewModel.horses[i]

                // 安全性檢查：確保 horse.number 不會超出 imageBitmaps 的索引
                val imageIndex = horse.number.coerceIn(0, imageBitmaps.lastIndex)

                drawImage(
                    image = imageBitmaps[imageIndex],
                    dstOffset = IntOffset(
                        horse.horseX,
                        horse.horseY
                    ),
                    dstSize = IntSize(200, 200)
                )
            }
        }
    }
}