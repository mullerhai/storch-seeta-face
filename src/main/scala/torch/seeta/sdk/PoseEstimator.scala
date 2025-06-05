package torch.seeta.sdk

/**
 * 姿态估计
 */
class PoseEstimator {
  var impl = 0

  /**
   * 后面自己添加的
   *
   * @param seting
   */
  def this(seting: SeetaModelSetting) ={
    this()
    this.construct(seting)
  }

  def this(seetaModel: String) ={
    this()
    this.construct(seetaModel)
  }

  def this(model: String, device: String, id: Int)= {
    this()
    this.construct(model, device, id)
  }

  /**
   * 后面自己添加的
   *
   * @param seting
   */
  @native private def construct(seting: SeetaModelSetting): Unit

  @native private def construct(seetaModel: String): Unit

  @native private def construct(model: String, device: String, id: Int): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native def Estimate(image: SeetaImageData, face: SeetaRect, yaw: Array[Float], pitch: Array[Float], roll: Array[Float]): Unit
}