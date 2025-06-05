package torch.seeta.proxy

import torch.seeta.pool.{EyeStateDetectorPool, SeetaConfSetting}
import torch.seeta.sdk.{EyeStateDetector, SeetaImageData, SeetaPointF}

class EyeStateDetectorProxy  {
  private var pool: EyeStateDetectorPool = null

  def this(confSetting: SeetaConfSetting)= {
    this()
    pool = new EyeStateDetectorPool(confSetting)
  }

  def detect(imageData: SeetaImageData, points: Array[SeetaPointF]): Array[EyeStateDetector.EYE_STATE] = {
    var eyeStateDetector: EyeStateDetector = null
    var states: Array[EyeStateDetector.EYE_STATE] = null
    try {
      eyeStateDetector = pool.borrowObject
      states = eyeStateDetector.detect(imageData, points)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (eyeStateDetector != null) pool.returnObject(eyeStateDetector)
    states
  }
}