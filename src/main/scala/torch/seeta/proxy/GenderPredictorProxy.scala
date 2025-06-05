package torch.seeta.proxy

import torch.seeta.pool.{GenderPredictorPool, SeetaConfSetting}
import torch.seeta.sdk.{GenderPredictor, SeetaImageData, SeetaPointF}

class GenderPredictorProxy  {
  private var pool: GenderPredictorPool = null

  def this(confSetting: SeetaConfSetting)= {
    this()
    pool = new GenderPredictorPool(confSetting)
  }

  def predictGenderWithCrop(image: SeetaImageData, points: Array[SeetaPointF]): GenderPredictorProxy#GenderItem = {
    val gender = new Array[GenderPredictor.GENDER](1)
    var genderPredictor: GenderPredictor = null
    var result = false
    try {
      genderPredictor = pool.borrowObject
      result = genderPredictor.PredictGenderWithCrop(image, points, gender)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (genderPredictor != null) pool.returnObject(genderPredictor)
    new GenderPredictorProxy#GenderItem(gender(0), result)
  }

  class GenderItem(private var gender: GenderPredictor.GENDER, private var result: Boolean) {
    def getGender: GenderPredictor.GENDER = gender

    def setGender(gender: GenderPredictor.GENDER): Unit = {
      this.gender = gender
    }

    def isResult: Boolean = result

    def setResult(result: Boolean): Unit = {
      this.result = result
    }
  }
}