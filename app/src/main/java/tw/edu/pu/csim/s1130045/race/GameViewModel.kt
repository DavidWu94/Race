package tw.edu.pu.csim.s1130045.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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

    var circleX by mutableFloatStateOf(0f)
        private set
    var circleY by mutableFloatStateOf(0f)
        private set

    // 設定螢幕寬度與高度
    fun setGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        //設定紅圓的圓心
        circleX = 100f
        circleY = screenHeightPx - 100f

        horses.clear()
        for (i in 0..2){
            horses.add(Horse(i))
        }
    }


    var gameRunning by mutableStateOf(false)

    val horses = mutableListOf<Horse>()

    fun startGame() {
        if (!gameRunning) {
            gameRunning = true
            //回到初使位置
            circleX = 100f
            circleY = screenHeightPx - 100f

            for (horse in horses) {
                horse.horseX = 0
            }

            viewModelScope.launch {
                while (gameRunning) { // 每0.1秒循環
                    delay(100)
                    circleX += 10

                    if (circleX >= screenWidthPx - 100) {
                        circleX = 100f
                    }

                    for (horse in horses) {
                        horse.horseRun()
                        if (horse.horseX >= screenWidthPx - 200) {
                            horse.horseX = 0
                        }
                    }
                }
            }
        }
    }

    fun stopGame() {
        gameRunning = false
    }

    fun moveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }


}