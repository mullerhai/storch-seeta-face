package torch.seeta.sdk

import java.io.FileNotFoundException

/**
 * 人脸跟踪器,人脸跟踪器会对输入的彩色图像或者灰度图像中的人脸进行跟踪，并返回所有跟踪到的人脸信息。
 */
class FaceTracker {
  //    static{
  //        System.loadLibrary("FaceTracker600_java");
  //    }
  var impl = 0

  def this(seetaModel: String, videoWidth: Int, videoHeight: Int) ={
    this()
    this.construct(seetaModel, videoWidth, videoHeight)
  }

  def this(seetaModel: Array[String], videoWidth: Int, videoHeight: Int) ={
    this()
  }

  /**
   * 后面 自己加的 可能会用到gpu
   *
   * @param model
   * @param device
   * @param id
   * @param videoWidth
   * @param videoHeight
   */
  def this(model: String, device: String, id: Int, videoWidth: Int, videoHeight: Int) = {
    this()
    this.construct(model, device, id, videoWidth, videoHeight)
  }

  /**
   * 后面 自己加的 可能会用到gpu
   *
   * @param model
   * @param device
   * @param id
   * @param videoWidth
   * @param videoHeight
   */
  //( jstring model, jstring device, jint id, jint video_width, jint video_height)
  @native private def construct(model: String, device: String, id: Int, videoWidth: Int, videoHeight: Int): Unit

  @native private def construct(seetaModel: String, videoWidth: Int, videoHeight: Int): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native def SetSingleCalculationThreads(num: Int): Unit

  @native def Track(image: SeetaImageData): Array[SeetaTrackingFaceInfo]

  @native def Track(image: SeetaImageData, frame_no: Int): Array[SeetaTrackingFaceInfo]

  @native def SetMinFaceSize(size: Int): Unit

  @native def GetMinFaceSize: Int

  @native def SetVideoStable(stable: Boolean): Unit

  @native def GetVideoStable: Boolean
}