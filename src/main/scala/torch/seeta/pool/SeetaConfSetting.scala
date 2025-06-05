package torch.seeta.pool

import org.apache.commons.pool2.impl.{GenericObjectPool, GenericObjectPoolConfig}
import torch.seeta.sdk.{SeetaDevice, SeetaModelSetting}

import java.io.FileNotFoundException

/**
 * 用于构建常用的配置
 * maxTotal 对象总数 8
 * maxIdle  最大空闲对象数 8
 * minIdle  最小空闲对象书 0
 * lifom    对象池借还是否采用 lifo true
 * fairness 对于借对象的线程阻塞恢复公平性 false
 * maxWaitMillis 借对象阻塞最大等待时间 -1
 * minEvictableIdleTimeMillis  最小驱逐空闲时间 30分钟
 * numTestsPerEvictionRun  每次驱逐数量 3
 * testOnCreate  创建后有效性测试  false
 * testOnBorrow  出借前有效性测试  false
 * testOnReturn  还回前有效性测试  false
 * testWhileIdle 空闲有效性测试  false
 * timeBetweenEvictionRunsMillis 驱逐定时器周期  false
 * blockWhenExhausted 对象池耗尽是否 block true
 */
class SeetaConfSetting extends GenericObjectPoolConfig[AnyRef] {
  /**
   * 评估器用的配置文件
   */
  private var SeetaModelSetting: SeetaModelSetting = null

  def this(SeetaModelSetting: SeetaModelSetting) ={
    this()
    this.SeetaModelSetting = SeetaModelSetting
  }

  def this(id: Int, models: Array[String], dev: SeetaDevice) ={
    this()
    this.SeetaModelSetting = new SeetaModelSetting(id, models, dev)
  }

  def this(models: Array[String])= {
    this()
    this.SeetaModelSetting = new SeetaModelSetting(0, models, SeetaDevice.SEETA_DEVICE_AUTO)
  }

  def this(model: String) ={
    this()
    this.SeetaModelSetting = new SeetaModelSetting(0, Array[String](model), SeetaDevice.SEETA_DEVICE_AUTO)
  }

  def getSeetaModelSetting: SeetaModelSetting = SeetaModelSetting

  def setSeetaModelSetting(SeetaModelSetting: SeetaModelSetting): Unit = {
    this.SeetaModelSetting = SeetaModelSetting
  }
}