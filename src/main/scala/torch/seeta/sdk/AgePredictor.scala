package torch.seeta.sdk

/**
 * 年龄估计器
 *
 *
 *
 */
object AgePredictor {
  enum Property:
    case PROPERTY_NUMBER_THREADS, PROPERTY_ARM_CPU_MODE
    def getValue: Int = this match {
      case PROPERTY_NUMBER_THREADS => 0
      case PROPERTY_ARM_CPU_MODE => 1
    }
//  object Property extends Enumeration {
//    type Property = Value
//    val PROPERTY_NUMBER_THREADS, PROPERTY_ARM_CPU_MODE = Value
//    private var value = 0d ef this (value: Int) {
//      this ()
//      this.value = value
//    }
//
//    def getValue: Int = value
//  }
}

class AgePredictor(setting: SeetaModelSetting) {
  this.construct(setting)
  //    static{
  //        System.loadLibrary("SeetaAgePredictor600_java");
  //    }
  var impl = 0

  @native private def construct(setting: SeetaModelSetting): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native def GetCropFaceWidth: Int

  @native def GetCropFaceHeight: Int

  @native def GetCropFaceChannels: Int

  @native def CropFace(image: SeetaImageData, points: Array[SeetaPointF], face: SeetaImageData): Boolean

  @native def PredictAge(face: SeetaImageData, age: Array[Int]): Boolean

  @native def PredictAgeWithCrop(image: SeetaImageData, points: Array[SeetaPointF], age: Array[Int]): Boolean

  /**
   * 获取照片的年龄评估
   *
   * @param image  SeetaImageData
   * @param points points
   * @return 将接口重写，使其符合java代码正常写法
   */
  def predictAgeWithCrop(image: SeetaImageData, points: Array[SeetaPointF]): Integer = {
    val ages = new Array[Int](1)
    this.PredictAgeWithCrop(image, points, ages)
    if (ages != null && ages.length >= 1) return ages(0)
    null
  }

  @native def set(property: AgePredictor.Property, value: Double): Unit

  @native def get(property: AgePredictor.Property): Double
}