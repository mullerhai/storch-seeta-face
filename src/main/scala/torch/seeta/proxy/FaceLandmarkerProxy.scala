package torch.seeta.proxy

import torch.seeta.pool.{FaceLandmarkerPool, SeetaConfSetting}
import torch.seeta.sdk.{FaceLandmarker, LandmarkerMask, SeetaImageData, SeetaPointF, SeetaRect, *}

/**
 * 人脸特征点检测器  有 5点和68点
 */
class FaceLandmarkerProxy {
  private var pool: FaceLandmarkerPool = null

  def this(config: SeetaConfSetting) ={
    this()
    pool = new FaceLandmarkerPool(config)
  }

  def mark(imageData: SeetaImageData, seetaRect: SeetaRect): Array[SeetaPointF] = {
    var faceLandmarker: FaceLandmarker = null
    var pointFS: Array[SeetaPointF] = null
    try {
      faceLandmarker = pool.borrowObject
      pointFS = new Array[SeetaPointF](faceLandmarker.number)
      faceLandmarker.mark(imageData, seetaRect, pointFS)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceLandmarker != null) pool.returnObject(faceLandmarker)
    pointFS
  }

  def isMask(imageData: SeetaImageData, seetaRect: SeetaRect): LandmarkerMask = {
    var faceLandmarker: FaceLandmarker = null
    val landmarkerMask = new LandmarkerMask
    try {
      faceLandmarker = pool.borrowObject
      val pointFS = new Array[SeetaPointF](faceLandmarker.number)
      val masks = new Array[Int](faceLandmarker.number)
      faceLandmarker.mark(imageData, seetaRect, pointFS, masks)
      landmarkerMask.setMasks(masks)
      landmarkerMask.setSeetaPointFS(pointFS)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceLandmarker != null) pool.returnObject(faceLandmarker)
    landmarkerMask
  }

  def number: Int = {
    var faceLandmarker: FaceLandmarker = null
    try {
      faceLandmarker = pool.borrowObject
      if (faceLandmarker != null) return faceLandmarker.number
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceLandmarker != null) pool.returnObject(faceLandmarker)
    0
  }
}