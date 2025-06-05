package torch.seeta.proxy

import torch.seeta.pool.{PoseEstimatorPool, SeetaConfSetting}
import torch.seeta.sdk.{PoseEstimator, SeetaImageData, SeetaRect}

class PoseEstimatorProxy(confSetting: SeetaConfSetting) {
  pool = new PoseEstimatorPool(confSetting)
  private var pool: PoseEstimatorPool = null

  def estimate(image: SeetaImageData, face: SeetaRect): PoseEstimatorProxy#PoseItem = {
    val yaw = new Array[Float](1)
    val pitch = new Array[Float](1)
    val roll = new Array[Float](1)
    var poseEstimator: PoseEstimator = null
    try {
      poseEstimator = pool.borrowObject
      poseEstimator.Estimate(image, face, yaw, pitch, roll)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (poseEstimator != null) pool.returnObject(poseEstimator)
    new PoseItem(yaw(0), pitch(0), roll(0)) //PoseEstimatorProxy
  }

  class PoseItem( var yaw: Float,  var pitch: Float,  var roll: Float) {
    def getYaw: Float = yaw

    def setYaw(yaw: Float): Unit = {
      this.yaw = yaw
    }

    def getPitch: Float = pitch

    def setPitch(pitch: Float): Unit = {
      this.pitch = pitch
    }

    def getRoll: Float = roll

    def setRoll(roll: Float): Unit = {
      this.roll = roll
    }
  }
}