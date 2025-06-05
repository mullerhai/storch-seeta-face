package torch.seeta.pool

import org.apache.commons.pool2.{PooledObject, PooledObjectFactory}
import org.apache.commons.pool2.impl.{DefaultPooledObject, GenericObjectPool, GenericObjectPoolConfig}
import torch.seeta.sdk.{EyeStateDetector, FaceDetector}

import scala.collection.mutable

class FaceDetectorPool(config: SeetaConfSetting) extends GenericObjectPool[FaceDetector](
  new PooledObjectFactory[FaceDetector] {
    /**
     * borrowObject 方法的主要流程是首先看里面的 idleReferences 是否为空，如果不为空，则从里面取一个对象出来并返回，否则通过 factory 来创建一个 object。
     * @return 包装好的 FaceDetector 对象
     */
    override def makeObject(): PooledObject[FaceDetector] = {
      val detector = new FaceDetector(config.getSeetaModelSetting)
      new DefaultPooledObject[FaceDetector](detector)
    }

    /**
     * 销毁对象
     * @param pooledObject 要销毁的包装对象
     */
    override def destroyObject(pooledObject: PooledObject[FaceDetector]): Unit = {
      // 这里原 Java 代码只是将引用置为 null，在 Scala 中可能不需要，可根据实际情况添加释放资源的逻辑
    }

    /**
     * 验证对象是否可用
     * @param pooledObject 要验证的包装对象
     * @return 如果对象可用返回 true，否则返回 false
     */
    override def validateObject(pooledObject: PooledObject[FaceDetector]): Boolean = {
      val obj = pooledObject.getObject
      // 假设 FaceDetector 有 impl 字段，若实际不存在需调整
      val impl = obj.impl
      impl >= 0
    }

    /**
     * 激活一个对象，使其可用
     * @param pooledObject 要激活的包装对象
     */
    override def activateObject(pooledObject: PooledObject[FaceDetector]): Unit = {
      val obj = pooledObject.getObject
      // 假设 FaceDetector 有 impl 字段，若实际不存在需调整
      val impl = obj.impl
      // 可根据实际需求添加激活逻辑
    }

    /**
     * 钝化一个对象，也可以理解为反初始化
     * @param pooledObject 要钝化的包装对象
     */
    override def passivateObject(pooledObject: PooledObject[FaceDetector]): Unit = {
      // 无需操作
    }
  }, config.asInstanceOf[GenericObjectPoolConfig[FaceDetector]]
)
