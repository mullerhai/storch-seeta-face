package torch.seeta.proxy

import torch.seeta.pool.{AgePredictorPool, SeetaConfSetting}
import torch.seeta.sdk.{AgePredictor, SeetaImageData, SeetaPointF}

/**
 * 年龄评估器
 */
class AgePredictorProxy  {
  private var pool: AgePredictorPool = null

  def this(config: SeetaConfSetting) = {
    this()
    pool = new AgePredictorPool(config)
  }

  def predictAgeWithCrop(image: SeetaImageData, points: Array[SeetaPointF]): Int = {
    var agePredictor: AgePredictor = null
    val age = new Array[Int](1)
    try {
      agePredictor = pool.borrowObject
      agePredictor.PredictAgeWithCrop(image, points, age)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (agePredictor != null) pool.returnObject(agePredictor)
    age(0)
  }
}