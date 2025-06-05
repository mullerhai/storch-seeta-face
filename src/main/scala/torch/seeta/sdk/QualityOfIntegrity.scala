package torch.seeta.sdk

/**
 * 非深度学习的人脸完整度评估器，评估人脸靠近图像边缘的程度。
 */
object QualityOfIntegrity {
  enum QualityLevel :
    case LOW, //Quality level is low
    MEDIUM, //Quality level is medium
    HIGH //Quality level is high
//  object QualityLevel extends Enumeration {
//    type QualityLevel = Value
//    val LOW, //Quality level is low
//    MEDIUM, //Quality level is medium
//    HIGH = Value
//    //Quality level is high
//  }
}

class QualityOfIntegrity {
  this.construct()
  //    static {
  //        System.loadLibrary("QualityAssessor300_java");
  //    }
  var impl = 0

  def this(low: Float, high: Float)= {
    this()
    this.construct(low, high)
  }

  @native private def construct(): Unit

  /**
   * @param low  [input] threshold low
   * @param high [input] threshold high
   */
  @native private def construct(low: Float, high: Float): Unit

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

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): QualityOfIntegrity.QualityLevel = {
    val index = this.checkCore(imageData, face, landmarks, score)
    val level = QualityOfIntegrity.QualityLevel.values(index)
    level
  }
}