package torch.seeta.pool

import org.apache.commons.pool2.PooledObject
import org.apache.commons.pool2.PooledObjectFactory
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.impl.GenericObjectPool
import torch.seeta.sdk.GenderPredictor

class GenderPredictorPool(config: SeetaConfSetting)  extends GenericObjectPool[GenderPredictor](new PooledObjectFactory[GenderPredictor]() {


  /**
 * this.factoryType = null;
 * this.maxIdle = 8;
 * this.minIdle = 0;
 * this.allObjects = new ConcurrentHashMap();
 * this.createCount = new AtomicLong(0L);
 * this.makeObjectCount = 0L;
 * this.makeObjectCountLock = new Object();
 * this.abandonedConfig = null;
 */
 /**
     * borrowObject方法的主要流程是首先看里面的idleReferences是否为空，如果不为空，则从里面取一个对象出来并返回，否则通过factory来创建一个object。
     *
     * @return
     * @throws Exception
     */
    @throws[Exception]
    override def makeObject: PooledObject[?] = {
      val detector = new GenderPredictor(config.getSeetaModelSetting)
      new DefaultPooledObject[?](detector)
    }

    @throws[Exception]
    override def destroyObject(pooledObject: PooledObject[GenderPredictor]): Unit = {
      var instance = pooledObject.getObject
      instance = null
    }

    /**
     * 验证对象是否可用
     *
     * @param pooledObject
     * @return
     */
    override def validateObject(pooledObject: PooledObject[GenderPredictor]): Boolean = {
      val instance = pooledObject.getObject
      val impl = instance.impl
      impl >= 0
    }

    /**
     * 激活一个对象，使其可用用
     *
     * @param pooledObject
     * @throws Exception
     */
    @throws[Exception]
    override def activateObject(pooledObject: PooledObject[GenderPredictor]): Unit = {
      val instance = pooledObject.getObject
      val impl = instance.impl
    }

    /**
     * 钝化一个对象,也可以理解为反初始化
     *
     * @param pooledObject
     * @throws Exception
     */
    @throws[Exception]
    override def passivateObject(pooledObject: PooledObject[?]): Unit = {

      //nothing
    }
  }, config) {
}