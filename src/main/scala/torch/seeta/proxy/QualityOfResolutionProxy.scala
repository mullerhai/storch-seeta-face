package torch.seeta.proxy

import torch.seeta.pool.{QualityOfResolutionPool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfResolution, SeetaImageData, SeetaPointF, SeetaRect}

class QualityOfResolutionProxy {
  pool = new QualityOfResolutionPool(new SeetaConfSetting)
  private var pool: QualityOfResolutionPool = null

  def this(confSetting: SeetaConfSetting)= {
    this()
    pool = new QualityOfResolutionPool(confSetting)
  }

  /**
   * 评估人脸尺寸
   *
   * @param imageData
   * @param face
   * @param landmarks [output] quality score
   * @return QualityLevel
   */
  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfResolution.QualityLevel = {
    val score = new Array[Float](1)
    var qualityLevel: QualityOfResolution.QualityLevel = null
    var qualityOfResolution: QualityOfResolution = null
    try {
      qualityOfResolution = pool.borrowObject
      qualityLevel = qualityOfResolution.check(imageData, face, landmarks, score)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfResolution != null) pool.returnObject(qualityOfResolution)
    qualityLevel
  }
}