package torch.seeta.proxy

import torch.seeta.pool.{QualityOfBrightnessPool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfBrightness, SeetaImageData, SeetaPointF, SeetaRect}

class QualityOfBrightnessProxy {
  pool = new QualityOfBrightnessPool(new SeetaConfSetting)
  private var pool: QualityOfBrightnessPool = null

  def this(confSetting: SeetaConfSetting) ={
    this()
    pool = new QualityOfBrightnessPool(confSetting)
  }

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfBrightnessProxy#BrightnessItem = {
    val score = new Array[Float](1)
    var check: QualityOfBrightness.QualityLevel = null
    var qualityOfBrightness: QualityOfBrightness = null
    try {
      qualityOfBrightness = pool.borrowObject
      check = qualityOfBrightness.check(imageData, face, landmarks, score)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfBrightness != null) pool.returnObject(qualityOfBrightness)
    new BrightnessItem(check, score(0)) //QualityOfBrightnessProxy
  }

  class BrightnessItem( var check: QualityOfBrightness.QualityLevel,  var score: Float) {
    def getCheck: QualityOfBrightness.QualityLevel = check

    def setCheck(check: QualityOfBrightness.QualityLevel): Unit = {
      this.check = check
    }

    def getScore: Float = score

    def setScore(score: Float): Unit = {
      this.score = score
    }
  }
}