package torch.seeta.proxy

import torch.seeta.pool.{QualityOfPoseExPool, SeetaConfSetting}
import torch.seeta.sdk.{QualityOfPoseEx, SeetaImageData, SeetaPointF, SeetaRect}

class QualityOfPoseExProxy  {
  private var pool: QualityOfPoseExPool = null

  def this(setting: SeetaConfSetting)= {
    this()
    pool = new QualityOfPoseExPool(setting)
  }

  /**
   * 检测人脸姿态
   *
   * @param imageData
   * @param face
   * @param landmarks
   * @return
   */
  def check(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfPoseEx.QualityLevel = {
    var qualityLevel: QualityOfPoseEx.QualityLevel = null
    var qualityOfPoseEx: QualityOfPoseEx = null
    val scors = new Array[Float](1)
    try {
      qualityOfPoseEx = pool.borrowObject
      qualityLevel = qualityOfPoseEx.check(imageData, face, landmarks, scors)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfPoseEx != null) pool.returnObject(qualityOfPoseEx)
    qualityLevel
  }

  /**
   * 检测人脸姿态
   *
   * @param imageData [input]image data
   * @param face      [input] face location
   * @param landmarks [input] face landmarks
   * @return yaw       [output] face location in yaw  偏航中的面部位置
   * @return pitch     [output] face location in pitch 俯仰中的面部位置
   * @return roll      [oputput] face location in roll  面卷中的位置
   */
  def checkCore(imageData: SeetaImageData, face: SeetaRect, landmarks: Array[SeetaPointF]): QualityOfPoseExProxy#PoseExItem = {
    val yaw = new Array[Float](1)
    val pitch = new Array[Float](1)
    val roll = new Array[Float](1)
    var qualityOfPoseEx: QualityOfPoseEx = null
    try {
      qualityOfPoseEx = pool.borrowObject
      qualityOfPoseEx.check(imageData, face, landmarks, yaw, pitch, roll)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (qualityOfPoseEx != null) pool.returnObject(qualityOfPoseEx)
    new PoseExItem(yaw(0), pitch(0), roll(0)) //QualityOfPoseExProxy
  }

  class PoseExItem( var yaw: Float,  var pitch: Float,  var roll: Float) {
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