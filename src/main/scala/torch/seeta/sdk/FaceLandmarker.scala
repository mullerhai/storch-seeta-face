package torch.seeta.sdk

/**
 * 人脸特征点检测器
 */
class FaceLandmarker[D] {
  //    static{
  //        System.loadLibrary("SeetaFaceLandmarker600_java");
  //    }
  var impl = 0

  def this(setting: SeetaModelSetting[D])= {
    this()
    this.construct(setting)
  }

  def this(model: String, device: String, id: Int) ={
    this()
    this.construct(model, device, id)
  }

  @native private def construct(seeting: SeetaModelSetting[D]): Unit

  @native private def construct(model: String, device: String, id: Int): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native def number: Int

  @native def mark(imageData: SeetaImageData, seetaRect: SeetaRect, pointFS: Array[SeetaPointF]): Unit

  @native def mark(imageData: SeetaImageData, seetaRect: SeetaRect, pointFS: Array[SeetaPointF], masks: Array[Int]): Unit
}