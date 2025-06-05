package torch.seeta.sdk

/**
 * 非深度学习的人脸清晰度评估器
 */
object QualityOfClarity {
  object QualityLevel extends Enumeration {
    type QualityLevel = Value
    val LOW, //Quality level is low
    MEDIUM, //Quality level is medium
    HIGH = Value
    //Quality level is high
  }
}

class QualityOfClarity {
  this.construct()
  //    static {
  //        System.loadLibrary("QualityAssessor300_java");
  //    }
  var impl = 0

  /**
   * low	float		分级参数一
   * high	float		分级参数二
   * 说明：分类依据为[0, low)=> LOW; [low, high)=> MEDIUM; [high, ~)=> HIGH.
   *
   * @param low
   * @param high
   */
  def this(low: Float, high: Float) = {
    this()
    this.construct(low, high)
  }

  @native private def construct(): Unit

  /**
   * @param low  [input] threshold low
   * @param high [input] threshold high
   *             [0, low)=> LOW
   *             [low, high)=> MEDIUM
   *             [high, ~)=> HIGH
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

  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): QualityOfClarity.QualityLevel = {
    val index = this.checkCore(imageData, face, landmarks, score)
    val level = QualityOfClarity.QualityLevel.values(index)
    level
  }
}