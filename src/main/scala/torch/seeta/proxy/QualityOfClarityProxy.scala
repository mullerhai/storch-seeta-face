package torch.seeta.proxy

import torch.seeta.pool.{QualityOfClarityPool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfClarity, SeetaImageData, SeetaPointF, SeetaRect}

class QualityOfClarityProxy {
  pool = new QualityOfClarityPool(new SeetaConfSetting)
  private var pool: QualityOfClarityPool = null

  def this(setting: SeetaConfSetting)= {
    this()
    pool = new QualityOfClarityPool(setting)
  }

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfClarityProxy#ClarityItem = {
    val score = new Array[Float](1)
    var qualityOfClarity: QualityOfClarity = null
    var check: QualityOfClarity.QualityLevel = null
    try {
      qualityOfClarity = pool.borrowObject
      check = qualityOfClarity.check(imageData, face, landmarks, score)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfClarity != null) pool.returnObject(qualityOfClarity)
    new ClarityItem(check, score(0)) //QualityOfClarityProxy
  }

  class ClarityItem( var qualityLevel: QualityOfClarity.QualityLevel,  var score: Float) {
    def getQualityLevel: QualityOfClarity.QualityLevel = qualityLevel

    def setQualityLevel(qualityLevel: QualityOfClarity.QualityLevel): Unit = {
      this.qualityLevel = qualityLevel
    }

    def getScore: Float = score

    def setScore(score: Float): Unit = {
      this.score = score
    }
  }
}