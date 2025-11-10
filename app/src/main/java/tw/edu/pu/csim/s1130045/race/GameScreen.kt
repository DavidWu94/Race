package tw.edu.pu.csim.s1130045.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.wear.compose.materialcore.screenHeightPx

@Composable
fun GameScreen(message: String) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Text(text = message)
    }

    val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

    Canvas (modifier = Modifier.fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume() // 告訴系統已經處理了這個事件
                gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
            }
            for(i in 0..2){
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].number],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].horseX,
                        gameViewModel.horses[i].horseY),
                    dstSize = IntSize(200, 200)
                )
            }


        }

    ) {
        // 繪製圓形
        drawCircle(
            color = Color.Red,
            radius = 100f,
            center = Offset(100f, 100f)
        )
    }
    //載入圖片
    //val imageBitmap = ImageBitmap.imageResource(R.drawable.horse0)

    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )


}


