package torch.seeta.sdk

/**
 * 性别估计器
 */
object GenderPredictor {
  enum Property:
    case PROPERTY_NUMBER_THREADS, //Number of threads
    PROPERTY_ARM_CPU_MODE //ARM CPU mode
   def getValue: Int = match this {
      case PROPERTY_NUMBER_THREADS => 0
      case PROPERTY_ARM_CPU_MODE => 1
    }
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

  enum GENDER:
    case MALE, FEMALE
//  object GENDER extends Enumeration {
//    type GENDER = Value
//    val MALE, FEMALE = Value
//  }
}

class GenderPredictor(setting: SeetaModelSetting) {
  this.construct(setting)
  //    static{
  //        System.loadLibrary("SeetaGenderPredictor600_java");
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

  @native private def PredictGenderCore(face: SeetaImageData, gender_index: Array[Int]): Boolean

  @native private def PredictGenderWithCropCore(image: SeetaImageData, points: Array[SeetaPointF], gender_index: Array[Int]): Boolean

  def PredictGender(face: SeetaImageData, gender: Array[GenderPredictor.GENDER]): Boolean = {
    val gender_index = new Array[Int](1)
    val result = PredictGenderCore(face, gender_index)
    if (gender.length > 0) gender(0) = GenderPredictor.GENDER.values(gender_index(0))
    result
  }

  def PredictGenderWithCrop(image: SeetaImageData, points: Array[SeetaPointF], gender: Array[GenderPredictor.GENDER]): Boolean = {
    val gender_index = new Array[Int](1)
    val result = PredictGenderWithCropCore(image, points, gender_index)
    if (gender.length > 0) gender(0) = GenderPredictor.GENDER.values(gender_index(0))
    result
  }

  @native def set(property: GenderPredictor.Property, value: Double): Unit

  @native def get(property: GenderPredictor.Property): Double
}