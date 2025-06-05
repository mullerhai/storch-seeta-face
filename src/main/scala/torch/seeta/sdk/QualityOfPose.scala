package torch.seeta.sdk

/**
 * 非深度学习的人脸姿态评估器
 */
object QualityOfPose {
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

class QualityOfPose {
  this.construct()
  //    static {
  //        System.loadLibrary("QualityAssessor300_java");
  //    }
  var impl = 0

  @native private def construct(): Unit

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

  /**
   * @param imageData [input]image data
   * @param face      [input] face location
   * @param landmarks [input] face landmarks
   * @param score     [output] quality score
   * @return QualityLevel quality level sorted into "LOW" , "Medium" , "HIGH"
   */
  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF], score: Array[Float]): QualityOfPose.QualityLevel = {
    val index = this.checkCore(imageData, face, landmarks, score)
    val level = QualityOfPose.QualityLevel.values(index)
    level
  }
}