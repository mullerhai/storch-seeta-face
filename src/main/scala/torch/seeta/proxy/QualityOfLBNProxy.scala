package torch.seeta.proxy

import torch.seeta.pool.{QualityOfLBNPool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfLBN, SeetaImageData, SeetaPointF}

class QualityOfLBNProxy  {
  private var pool: QualityOfLBNPool = null

  def this(setting: SeetaConfSetting) ={
    this()
    pool = new QualityOfLBNPool(setting)
  }

  def detect(imageData: SeetaImageData, points: Array[SeetaPointF]): QualityOfLBNProxy#LBNClass = {
    val light = new Array[Int](1)
    val blur = new Array[Int](1)
    val noise = new Array[Int](1)
    var qualityOfLBN: QualityOfLBN = null
    try {
      qualityOfLBN = pool.borrowObject
      qualityOfLBN.Detect(imageData, points, light, blur, noise)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfLBN != null) pool.returnObject(qualityOfLBN)
    new QualityOfLBNProxy#LBNClass(light, blur, noise)
  }

  class LBNClass(light: Array[Int], blur: Array[Int], noise: Array[Int]) {
    this.lightstate = QualityOfLBN.LIGHTSTATE.values(light(0))
    this.blurstate = QualityOfLBN.BLURSTATE.values(blur(0))
    this.noisestate = QualityOfLBN.NOISESTATE.values(noise(0))
    private var lightstate: QualityOfLBN.LIGHTSTATE = null
    private var blurstate: QualityOfLBN.BLURSTATE = null
    private var noisestate: QualityOfLBN.NOISESTATE = null

    def getLightstate: QualityOfLBN.LIGHTSTATE = lightstate

    def setLightstate(lightstate: QualityOfLBN.LIGHTSTATE): Unit = {
      this.lightstate = lightstate
    }

    def getBlurstate: QualityOfLBN.BLURSTATE = blurstate

    def setBlurstate(blurstate: QualityOfLBN.BLURSTATE): Unit = {
      this.blurstate = blurstate
    }

    def getNoisestate: QualityOfLBN.NOISESTATE = noisestate

    def setNoisestate(noisestate: QualityOfLBN.NOISESTATE): Unit = {
      this.noisestate = noisestate
    }
  }
}