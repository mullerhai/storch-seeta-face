package torch.seeta.sdk

/**
 * 活体识别
 *
 *
 */
object FaceAntiSpoofing {
  object Status extends Enumeration {
    type Status = Value
    val REAL, //< 真实人脸
    SPOOF, //< 攻击人脸（假人脸）
    FUZZY, //< 无法判断（人脸成像质量不好）
    DETECTING = Value
    //< 正在检测
  }
}

class FaceAntiSpoofing {
  //	static{
  //		System.loadLibrary("FaceAntiSpoofing600_java");
  //	}
  var impl = 0

  def this(setting: SeetaModelSetting) {
    this()
    this.construct(setting)
  }

  def this(model: String, device: String, id: Int) {
    this()
    this.construct(model, device, id)
  }

  def this(model1: String, model2: String, device: String, id: Int) {
    this()
    this.construct(model1, model2, device, id)
  }

  @native private def construct(setting: SeetaModelSetting): Unit

  @native private def construct(model: String, device: String, id: Int): Unit

  @native private def construct(model1: String, model2: String, device: String, id: Int): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native private def PredictCore(image: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): Int

  @native private def PredictVideoCore(image: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): Int

  def Predict(image: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): FaceAntiSpoofing.Status = {
    val status_num = this.PredictCore(image, face, landmarks)
    FaceAntiSpoofing.Status.values(status_num)
  }

  def PredictVideo(image: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): FaceAntiSpoofing.Status = {
    val status_num = this.PredictVideoCore(image, face, landmarks)
    FaceAntiSpoofing.Status.values(status_num)
  }

  @native def ResetVideo(): Unit

  @native def GetPreFrameScore(clarity: Array[Float], reality: Array[Float]): Unit

  @native def SetVideoFrameCount(number: Int): Unit

  @native def GetVideoFrameCount: Int

  @native def SetThreshold(clarity: Float, reality: Float): Unit

  @native def GetThreshold(clarity: Array[Float], reality: Array[Float]): Unit
}