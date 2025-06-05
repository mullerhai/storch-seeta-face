package torch.seeta.proxy

import torch.seeta.pool.{FaceAntiSpoofingPool, SeetaConfSetting}
import torch.seeta.sdk.{FaceAntiSpoofing, SeetaImageData, SeetaPointF, SeetaRect}

class FaceAntiSpoofingProxy  {
  private var pool: FaceAntiSpoofingPool = null

  def this(setting: SeetaConfSetting)= {
    this()
    pool = new FaceAntiSpoofingPool(setting)
  }

  def predict(image: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): FaceAntiSpoofing.Status = {
    var status: FaceAntiSpoofing.Status = null
    var faceAntiSpoofing: FaceAntiSpoofing = null
    try {
      faceAntiSpoofing = pool.borrowObject
      status = faceAntiSpoofing.Predict(image, face, landmarks)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceAntiSpoofing != null) pool.returnObject(faceAntiSpoofing)
    status
  }
}