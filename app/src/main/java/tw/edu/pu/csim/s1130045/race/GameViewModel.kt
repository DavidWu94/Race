package tw.edu.pu.csim.s1130045.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    var screenWidthPx by mutableFloatStateOf(0f)
        private set

    var screenHeightPx by mutableFloatStateOf(0f)
        private set

    // 1. 新增：分數變數
    var score by mutableIntStateOf(0)
        private set

    // 2. 新增：圓形相關變數
    var circleX by mutableFloatStateOf(0f)
    var circleY by mutableFloatStateOf(0f)
    private var circleSpeedX by mutableFloatStateOf(0f)
    private var circleSpeedY by mutableFloatStateOf(0f)
    private val circleRadius = 50f // 圓的半徑

    // 3. 新增：遊戲區域邊界 (避開標題和按鈕)
    private var topBoundary = 0f
    private var bottomBoundary = 0f

    // 設定螢幕寬度與高度
    fun setGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        // 設定遊戲邊界 (上方 150f 留給標題和分數，下方 150f 留給按鈕)
        topBoundary = 150f
        bottomBoundary = screenHeightPx - 150f

        resetGame() // 設置遊戲大小時，重設遊戲
    }

    var gameRunning by mutableStateOf(false)

    // 將遊戲重設到初始狀態
    fun resetGame() {
        gameRunning = false
        score = 0 // 分數歸零

        // 4. 設定圓的初始位置和速度
        circleX = 150f
        circleY = 200f
        circleSpeedX = 15f
        circleSpeedY = 15f
    }


    fun startGame() {
        // 只有在遊戲未運行時才開始
        if (!gameRunning) {
            gameRunning = true
            score = 0 // 每次開始都重設分數

            viewModelScope.launch {
                while (gameRunning) {
                    delay(20) // 讓動畫更流暢 (20ms)

                    // 5. 更新圓的位置
                    circleX += circleSpeedX
                    circleY += circleSpeedY

                    // 6. 碰撞邏輯
                    // 碰到右邊邊界 (要求：分數+1)
                    if (circleX >= screenWidthPx - circleRadius) {
                        circleX = screenWidthPx - circleRadius
                        circleSpeedX *= -1 // 反彈
                        score++ // 分數+1
                    }
                    // 碰到左邊邊界
                    if (circleX <= circleRadius) {
                        circleX = circleRadius
                        circleSpeedX *= -1 // 反彈
                    }
                    // 碰到下方邊界
                    if (circleY >= bottomBoundary - circleRadius) {
                        circleY = bottomBoundary - circleRadius
                        circleSpeedY *= -1 // 反彈
                    }
                    // 碰到上方邊界
                    if (circleY <= topBoundary + circleRadius) {
                        circleY = topBoundary + circleRadius
                        circleSpeedY *= -1 // 反彈
                    }
                }
            }
        }
    }

    // "結束" 按鈕的功能：停止並重設
    fun stopGame() {
        gameRunning = false // 停止 coroutine 迴圈
        resetGame() // 呼叫重設函式
    }
}