package torch.seeta.sdk

/**
 * 深度学习的人脸姿态评估器。
 */
object QualityOfPoseEx {
  object QualityLevel extends Enumeration {
    type QualityLevel = Value
    val LOW, //Quality level is low
    MEDIUM, //Quality level is medium
    HIGH = Value
    //Quality level is high
  }

  enum Property:
    case YAW_LOW_THRESHOLD, YAW_HIGH_THRESHOLD, PITCH_LOW_THRESHOLD, PITCH_HIGH_THRESHOLD, ROLL_LOW_THRESHOLD, ROLL_HIGH_THRESHOLD
    def getValue: Int = this match {
      case YAW_LOW_THRESHOLD => 0
      case YAW_HIGH_THRESHOLD => 1
      case PITCH_LOW_THRESHOLD => 2
      case PITCH_HIGH_THRESHOLD => 3
      case ROLL_LOW_THRESHOLD => 4
      case ROLL_HIGH_THRESHOLD => 5
    }
//  object Property extends Enumeration {
//    type Property = Value
//    val YAW_LOW_THRESHOLD, YAW_HIGH_THRESHOLD, PITCH_LOW_THRESHOLD, PITCH_HIGH_THRESHOLD, ROLL_LOW_THRESHOLD, ROLL_HIGH_THRESHOLD = Value
//    private var value = 0d ef this (value: Int) {
//      this ()
//      this.value = value
//    }
//
//    def getValue: Int = value
//  }
}

@throws[Exception]
class QualityOfPoseEx(setting: SeetaModelSetting){

/**
 * 人脸姿态评估器构造函数。
 *
 * @param setting setting
 * @throws Exception
 */
  this.construct(setting)
  //    static {
  //        System.loadLibrary("QualityAssessor300_java");
  //    }
  var impl = 0

  /**
   * 人脸姿态评估器构造函数
   *
   * @param setting
   * @throws Exception
   */
  @native
  @throws[Exception]
  private def construct(setting: SeetaModelSetting): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  /**
   * 检测人脸姿态
   *
   * @param imageData [input]image data
   * @param face      [input] face location
   * @param landmarks [input] face landmarks
   * @param score     [output] quality score
   * @return QualityLevel quality level sorted into "LOW" , "Medium" , "HIGH"
   */
  @native private def checkCore(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): Int

  /**
   * 检测人脸姿态
   *
   * @param imageData
   * @param face
   * @param landmarks
   * @param score
   * @return
   */
  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): QualityOfPoseEx.QualityLevel = {
    val index = this.checkCore(imageData, face, landmarks, score)
    val level = QualityOfPoseEx.QualityLevel.values(index)
    level
  }

  /**
   * 检测人脸姿态
   *
   * @param imageData [input]image data
   * @param face      [input] face location
   * @param landmarks [input] face landmarks
   * @param yaw       [output] face location in yaw  偏航中的面部位置
   * @param pitch     [output] face location in pitch 俯仰中的面部位置
   * @param roll      [oputput] face location in roll  面卷中的位置
   */
  @native private def checkCore(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], yaw: Array[Float], pitch: Array[Float], roll: Array[Float]): Unit

  /**
   * 检测人脸姿态
   *
   * @param imageData
   * @param face
   * @param landmarks
   * @param yaw   偏航中的面部位置
   * @param pitch 俯仰中的面部位置
   * @param roll  面卷中的位置
   */
  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], yaw: Array[Float], pitch: Array[Float], roll: Array[Float]): Unit = {
    this.checkCore(imageData, face, landmarks, yaw, pitch, roll)
  }

  @native def set(property: QualityOfPoseEx.Property, value: Double): Unit

  @native def get(property: QualityOfPoseEx.Property): Double
}