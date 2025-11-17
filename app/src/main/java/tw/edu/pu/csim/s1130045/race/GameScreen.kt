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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(gameViewModel: GameViewModel) {

    // (移除了 imageBitmaps 列表)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow) // 背景色
    ) {
        // 1. 遊戲標題 (置頂中央) - 顯示您的姓名
        Text(
            text = "資管二A 411300459 吳岱威", // 讀取 strings.xml 中的 title
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
        )

        // 2. 新增：顯示分數 (置頂左方)
        Text(
            text = "分數：${gameViewModel.score}", // 顯示分數
            color = Color.Black,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
        )

        // 3. 遊戲畫布
        Canvas(modifier = Modifier.fillMaxSize()) {

            // 4. 繪製紅色的圓
            drawCircle(
                color = Color.Red,
                radius = 50f, // 必須與 ViewModel 中的 circleRadius 相同
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )

            // (移除了繪製馬匹的 for 迴圈)
        }

        // (移除了 "第x馬獲勝" 的文字)

        // 5. 開始/結束 按鈕 (置底)
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