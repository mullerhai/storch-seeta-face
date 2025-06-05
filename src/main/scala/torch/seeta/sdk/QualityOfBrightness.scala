package torch.seeta.sdk

/**
 * QualityOfBrightness
 * 人脸亮度评估器构造函数。
 */
object QualityOfBrightness {
  enum QualityLevel:
    case LOW, MEDIUM, HIGH
//  object QualityLevel extends Enumeration {
//    type QualityLevel = Value
//    val LOW, //Quality level is low
//    MEDIUM, //Quality level is medium
//    HIGH = Value
//    //Quality level is high
//  }
}

class QualityOfBrightness {
  this.construct()
  var impl = 0

  def this(v0: Float, v1: Float, v2: Float, v3: Float)= {
    this()
    this.construct(v0, v1, v2, v3)
  }

  @native private def construct(): Unit

  /**
   * @param v0 [input] param 0
   * @param v1 [input] param 1
   * @param v2 [input] param2
   * @param v3 [input] param 3
   *           [0, v0) and [v3, ~) => LOW
   *           [v0, v1) and [v2, v3) => MEDIUM
   *           [v1, v2) => HIGH
   */
  @native private def construct(v0: Float, v1: Float, v2: Float, v3: Float): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  /**
   * @param imageData [input]image data
   * @param face      [input] face location
   * @param landmarks [input] face landmarks
   * @param score     [output] quality score
   * @return QualityLevel quality level sorted into "LOW" , "Medium" , "HIGH"
   */
  @native private def checkCore(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): Int

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): QualityOfBrightness.QualityLevel = {
    val index = this.checkCore(imageData, face, landmarks, score)
    val level = QualityOfBrightness.QualityLevel.values(index)
    level
  }
}