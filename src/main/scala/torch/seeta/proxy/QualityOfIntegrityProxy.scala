package torch.seeta.proxy

import torch.seeta.pool.{QualityOfIntegrityPool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfIntegrity, SeetaImageData, SeetaPointF, SeetaRect}

class QualityOfIntegrityProxy {
  pool = new QualityOfIntegrityPool(new SeetaConfSetting)
  private var pool: QualityOfIntegrityPool = null

  def this(setting: SeetaConfSetting)= {
    this()
    pool = new QualityOfIntegrityPool(setting)
  }

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfIntegrityProxy#IntegrityItem = {
    val score = new Array[Float](1)
    var qualityLevel: QualityOfIntegrity.QualityLevel = null
    var qualityOfIntegrity: QualityOfIntegrity = null
    try {
      qualityOfIntegrity = pool.borrowObject
      qualityLevel = qualityOfIntegrity.check(imageData, face, landmarks, score)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfIntegrity != null) pool.returnObject(qualityOfIntegrity)
    new QualityOfIntegrityProxy#IntegrityItem(qualityLevel, score(0))
  }

  class IntegrityItem(private var qualityLevel: QualityOfIntegrity.QualityLevel, private var score: Float) {
    def getQualityLevel: QualityOfIntegrity.QualityLevel = qualityLevel

    def setQualityLevel(qualityLevel: QualityOfIntegrity.QualityLevel): Unit = {
      this.qualityLevel = qualityLevel
    }

    def getScore: Float = score

    def setScore(score: Float): Unit = {
      this.score = score
    }
  }
}