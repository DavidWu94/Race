package tw.edu.pu.csim.s1130045.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class Horse(n: Int, initialY: Float) { // 接收 initialY 參數

    // 1. 將 var horseX = 0 改為 Compose 狀態
    var horseX by mutableIntStateOf(0)
    // 2. 將 var horseY = initialY.toInt() 改為 Compose 狀態
    var horseY by mutableIntStateOf(initialY.toInt())

    // 3. 將 var number = 0 改為 Compose 狀態
    var number by mutableIntStateOf(0) // 圖片索引

    fun horseRun(){
        number ++
        if (number > 3) {number = 0} // 圖片循環
        horseX += (10..30).random() // 每次移動隨機距離
    }
}