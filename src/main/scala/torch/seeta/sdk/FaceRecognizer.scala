package torch.seeta.sdk

//import torch.seeta.SeetaModelSetting;

/**
 * 人脸识别器
 */
object FaceRecognizer {
  
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

class FaceRecognizer {
  //    static {
  //        System.loadLibrary("SeetaFaceRecognizer600_java");
  //    }
  var impl = 0

  def this(setting: SeetaModelSetting) ={
    this()
    this.construct(setting)
  }

  def this(model: String, device: String, id: Int) ={
    this()
    this.construct(model, device, id)
  }

  @native private def construct(setting: SeetaModelSetting): Unit

  @native private def construct(model: String, device: String, id: Int): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native def GetCropFaceWidthV2: Int

  //public static native int SetLogLevel(int level);
  //public static native void SetSingleCalculationThreads(int num);
  @native def GetCropFaceHeightV2: Int

  @native def GetCropFaceChannelsV2: Int

  @native def GetExtractFeatureSize: Int

  @native def CropFaceV2(image: SeetaImageData, points: Array[SeetaPointF], face: SeetaImageData): Boolean

  @native def ExtractCroppedFace(face: SeetaImageData, features: Array[Float]): Boolean

  @native def Extract(image: SeetaImageData, points: Array[SeetaPointF], features: Array[Float]): Boolean

  @native def CalculateSimilarity(features1: Array[Float], features2: Array[Float]): Float

  @native def set(property: FaceRecognizer.Property, value: Double): Unit

  @native def get(property: FaceRecognizer.Property): Double
}