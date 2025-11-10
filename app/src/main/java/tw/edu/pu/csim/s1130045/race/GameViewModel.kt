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

    // 儲存勝利馬匹的索引 (1, 2, 3)，-1 表示沒有馬獲勝
    var winningHorseIndex by mutableIntStateOf(-1)
        private set

    // 設定螢幕寬度與高度
    fun setGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
        resetGame() // 設置遊戲大小時，重設遊戲
    }

    var gameRunning by mutableStateOf(false)
    val horses = mutableListOf<Horse>()

    // 將遊戲重設到初始狀態
    fun resetGame() {
        gameRunning = false
        winningHorseIndex = -1 // 重設勝利狀態

        horses.clear()
        // 根據 screenHeightPx 調整馬匹的 Y 座標
        val horseTrackHeight = (screenHeightPx - 100f) / 3 // 將畫面高度分成3等份 (為標題和按鈕留空間)
        val startY = 100f // 馬匹起始Y座標 (避開頂部標題)

        for (i in 0..2){
            // 傳入Y座標
            horses.add(Horse(i, startY + i * horseTrackHeight))
        }
    }


    fun startGame() {
        // 只有在遊戲未運行時才開始
        if (!gameRunning) {
            gameRunning = true
            winningHorseIndex = -1 // 新一輪遊戲，重設獲勝者

            // 馬匹回到初始位置
            for (horse in horses) {
                horse.horseX = 0
            }

            viewModelScope.launch {
                var winnerFound = false
                while (gameRunning && !winnerFound) { // 每0.1秒循環
                    delay(100)

                    for (i in horses.indices) { // 使用索引來判斷哪匹馬獲勝
                        val horse = horses[i]
                        horse.horseRun()
                        // 馬匹抵達終點線判斷 (留200f給馬匹圖片寬度)
                        if (horse.horseX >= screenWidthPx - 200) {
                            winningHorseIndex = i + 1 // 記錄獲勝馬匹，從1開始
                            winnerFound = true
                            gameRunning = false // 停止遊戲
                            break // 有馬獲勝，跳出迴圈
                        }
                    }
                }
            }
        }
    }

    // "結束" 按鈕的功能：停止並重設
    fun stopGame() {
        gameRunning = false // 停止 coroutine 迴圈

        // 馬匹回到初始位置
        for (horse in horses) {
            horse.horseX = 0
        }
        // 重設獲勝者
        winningHorseIndex = -1
    }
}