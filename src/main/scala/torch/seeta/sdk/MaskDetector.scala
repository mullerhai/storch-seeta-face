package torch.seeta.sdk

/**
 * 口罩检测器
 */
@throws[Exception]
class MaskDetector[D](setting: SeetaModelSetting[D]) {
  this.construct(setting)
  //    static {
  //        System.loadLibrary("SeetaMaksDetector200_java");
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
   * 人脸口罩检测器
   *
   * @param imageData [input]
   * @param face      [input]
   * @param score     [output]
   * @return boolean
   */
  @native def detect(imageData: SeetaImageData, face: SeetaRect, score: Array[Float]): Boolean
}