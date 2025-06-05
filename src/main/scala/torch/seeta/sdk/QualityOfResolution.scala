package torch.seeta.sdk

/**
 * 非深度学习的人脸尺寸评估器。
 */
object QualityOfResolution {
  /**
   * 人脸尺寸评估结果枚举
   */
  enum QualityLevel:
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

/**
 * 人脸尺寸评估器构造函数。
 */
class QualityOfResolution {
  this.construct()
  var impl = 0

  /**
   * 人脸尺寸评估器构造函数。
   *
   * @param low  分级参数一
   * @param high 分级参数二
   */
  def this(low: Float, high: Float) ={
    this()
    this.construct(low, high)
  }

  @native private def construct(): Unit

  /**
   * 人脸尺寸评估器构造函数。
   *
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
   * 评估人脸尺寸
   *
   * @param imageData
   * @param face
   * @param landmarks
   * @param score [output] quality score
   * @return int
   */
  @native private def checkCore(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): Int

  /**
   * 评估人脸尺寸
   *
   * @param imageData
   * @param face
   * @param landmarks
   * @param score [output] quality score
   * @return QualityLevel
   */
  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): QualityOfResolution.QualityLevel = {
    val index = this.checkCore(imageData, face, landmarks, score)
    val level = QualityOfResolution.QualityLevel.values(index)
    level
  }
}