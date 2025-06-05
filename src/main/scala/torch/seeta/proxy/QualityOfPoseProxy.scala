package torch.seeta.proxy

import torch.seeta.pool.{QualityOfPosePool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfPose, SeetaImageData, SeetaPointF, SeetaRect}

class QualityOfPoseProxy {
  pool = new QualityOfPosePool(new SeetaConfSetting)
  private var pool: QualityOfPosePool = null

  def this(confSetting: SeetaConfSetting) = {
    this()
    pool = new QualityOfPosePool(confSetting)
  }

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfPose.QualityLevel = {
    val score = new Array[Float](1)
    var qualityOfPose: QualityOfPose = null
    var check: QualityOfPose.QualityLevel = null
    try {
      qualityOfPose = pool.borrowObject
      check = qualityOfPose.check(imageData, face, landmarks, score)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfPose != null) pool.returnObject(qualityOfPose)
    check
  }
}