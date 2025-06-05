package torch.seeta.sdk

/**
 * 眼睛状态估计器
 *
 *
 */
object EyeStateDetector {
  enum EYE_STATE:
    case EYE_CLOSE, EYE_OPEN, EYE_RANDOM, EYE_UNKNOWN

  //    public enum EYE_STATE {
  //        EYE_CLOSE,
  //        EYE_OPEN,
  //        EYE_RANDOM,
  //        EYE_UNKNOWN
  //    }
//  final class EYE_STATE {}
}

@throws[Exception]
class EyeStateDetector[D](setting: SeetaModelSetting[D]) {
  this.construct(setting)
  //    static{
  //        System.loadLibrary("SeetaEyeStateDetector200_java");
  //    }
  var impl = 0

  @native
  @throws[Exception]
  private def construct(setting: SeetaModelSetting[D]): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  /**
   * 眼睛状态
   *
   * @param imageData
   * @param points
   * @return EYE_STATE[]
   */
  //    public void Detect(SeetaImageData imageData, SeetaPointF[] points, EYE_STATE[] eyeStatus) {
  //        if (eyeStatus.length < 2) return;
  //
  //        int[] eyeStateIndexs = new int[2];
  //        DetectCore(imageData, points, eyeStateIndexs);
  //        eyeStatus[0] = EYE_STATE.values()[eyeStateIndexs[0]];
  //        eyeStatus[1] = EYE_STATE.values()[eyeStateIndexs[1]];
  //    }
  def detect(imageData: SeetaImageData, points: Array[SeetaPointF]): Array[EyeStateDetector.EYE_STATE] = {
    val eyeStatus = new Array[EyeStateDetector.EYE_STATE](2)
    val eyeStateIndexs = new Array[Int](2)
    DetectCore(imageData, points, eyeStateIndexs)
    eyeStatus(0) = EyeStateDetector.EYE_STATE.values(eyeStateIndexs(0))
    eyeStatus(1) = EyeStateDetector.EYE_STATE.values(eyeStateIndexs(1))
    eyeStatus
  }

  @native private def DetectCore(imageData: SeetaImageData, points: Array[SeetaPointF], eyeStateIndexs: Array[Int]): Unit
}