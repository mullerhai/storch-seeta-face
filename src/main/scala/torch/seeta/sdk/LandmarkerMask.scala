package torch.seeta.sdk

/**
 * 判断人脸遮挡
 */
class LandmarkerMask {
  private[sdk] var seetaPointFS: Array[SeetaPointF] = null
  private[sdk] var masks: Array[Int] = null

  def getSeetaPointFS: Array[SeetaPointF] = seetaPointFS

  def setSeetaPointFS(seetaPointFS: Array[SeetaPointF]): Unit = {
    this.seetaPointFS = seetaPointFS
  }

  def getMasks: Array[Int] = masks

  def setMasks(masks: Array[Int]): Unit = {
    this.masks = masks
  }
}