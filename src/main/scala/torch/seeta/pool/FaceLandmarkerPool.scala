package torch.seeta.pool

import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.PooledObjectFactory
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.impl.GenericObjectPool
import torch.seeta.sdk.FaceLandmarker

/**
 * 人脸特征点检测器  有 5点和68点
 */
class FaceLandmarkerPool(config: SeetaConfSetting) extends GenericObjectPool[FaceLandmarker](new PooledObjectFactory[FaceLandmarker]() {
  @throws[Exception]
  override def makeObject: PooledObject[FaceLandmarker] = {
    val faceLandmarker = new FaceLandmarker(config.getSeetaModelSetting)
    new DefaultPooledObject[FaceLandmarker](faceLandmarker)
  }

  @throws[Exception]
  override def destroyObject(pooledObject: PooledObject[FaceLandmarker]): Unit = {
    var instance = pooledObject.getObject
    instance = null
  }

  override def validateObject(pooledObject: PooledObject[FaceLandmarker]): Boolean = {
    val instance = pooledObject.getObject
    //这就不知道要写什么了
    instance.impl >= 0L
  }

  @throws[Exception]
  override def activateObject(pooledObject: PooledObject[FaceLandmarker]): Unit = {
  }

  @throws[Exception]
  override def passivateObject(pooledObject: PooledObject[FaceLandmarker]): Unit = {
  }
}, config) {
}