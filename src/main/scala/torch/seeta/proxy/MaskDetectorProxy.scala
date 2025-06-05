package torch.seeta.proxy

import torch.seeta.pool.{MaskDetectorPool, SeetaConfSetting}
import torch.seeta.sdk.{MaskDetector, SeetaImageData, SeetaRect}

class MaskDetectorProxy  {
  private var pool: MaskDetectorPool = null

  def this(confSetting: SeetaConfSetting) ={
    this()
    pool = new MaskDetectorPool(confSetting)
  }

  def detect(imageData: SeetaImageData, face: SeetaRect): MaskDetectorProxy#MaskItem = {
    val score = new Array[Float](1)
    var detect = false
    var maskDetector: MaskDetector = null
    try {
      maskDetector = pool.borrowObject
      detect = maskDetector.detect(imageData, face, score)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (maskDetector != null) pool.returnObject(maskDetector)
    new MaskDetectorProxy#MaskItem(detect, score(0))
  }

  class MaskItem(private var mask: Boolean, private var score: Float) {
    def getScore: Float = score

    def setScore(score: Float): Unit = {
      this.score = score
    }

    def getMask: Boolean = mask

    def setMask(mask: Boolean): Unit = {
      this.mask = mask
    }
  }
}