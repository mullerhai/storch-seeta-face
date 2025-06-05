package torch.seeta.pool

import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.PooledObjectFactory
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.impl.GenericObjectPool
import torch.seeta.sdk.FaceRecognizer

/**
 * 人脸向量特征识别器
 */
class FaceRecognizerPool(config: SeetaConfSetting) extends GenericObjectPool[FaceRecognizer](new PooledObjectFactory[FaceRecognizer]() {
  @throws[Exception]
  override def makeObject = new DefaultPooledObject[FaceRecognizer](new FaceRecognizer(config.getSeetaModelSetting))

  @throws[Exception]
  override def destroyObject(pooledObject: PooledObject[FaceRecognizer]): Unit = {
    var instance = pooledObject.getObject
    instance = null
  }

  override def validateObject(pooledObject: PooledObject[FaceRecognizer]): Boolean = {
    val instance = pooledObject.getObject
    instance.impl >= 0L
  }

  @throws[Exception]
  override def activateObject(pooledObject: PooledObject[FaceRecognizer]): Unit = {
  }

  @throws[Exception]
  override def passivateObject(pooledObject: PooledObject[FaceRecognizer]): Unit = {
  }
}, config) {
}