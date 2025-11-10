package tw.edu.pu.csim.s1130045.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.text.compareTo


class GameViewModel: ViewModel() {
    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        //設定紅圓的圓心
        circleX = 100f
        circleY = screenHeightPx - 100f

        for (i in 0..2){
            horses.add(Horse(i))
        }
    }


    var gameRunning by mutableStateOf(false)

    //val horse = Horse()

    val horses = mutableListOf<Horse>()




    fun StartGame() {
        //回到初使位置
        circleX = 100f
        circleY = androidx.wear.compose.materialcore.screenHeightPx - 100f

        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)
                circleX += 10

                if (circleX >= screenWidthPx - 100){
                    circleX = 100f
                }
                horse.HorseRun()
                if (horse.horseX >= screenWidthPx - 200){
                    horse.horseX = 0
                }

                for (i in 0..2){
                    horses[i].HorseRun()
                    if (horses[i].horseX >= screenWidthPx - 200){
                        horses[i].horseX = 0
                    }
                }


            }
        }

    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }


}
