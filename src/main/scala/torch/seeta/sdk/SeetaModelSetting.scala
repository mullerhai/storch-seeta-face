package torch.seeta.sdk

import org.apache.commons.pool2.impl.{GenericObjectPool, GenericObjectPoolConfig}

import java.io.File
import java.io.FileNotFoundException

class SeetaModelSetting extends GenericObjectPoolConfig[AnyRef]{
  var device: SeetaDevice = null
  var id = 0 // when device is GPU, id means GPU id
  var model: Array[String] = null

  def this(id: Int, models: Array[String], dev: SeetaDevice)= {
    this()
    this.id = id
    this.device = dev
    this.model = new Array[String](models.length)
    for (i <- 0 until models.length) {
      //添加验证
      val file = new File(models(i))
      if (!file.exists) throw new FileNotFoundException("模型文件没有找到！")
      this.model(i) = models(i)
    }
  }

  def this(models: Array[String], dev: SeetaDevice)= {
    this()
    this.id = 0
    this.device = dev
    this.model = new Array[String](models.length)
    for (i <- 0 until models.length) {
      //添加验证
      val file = new File(models(i))
      if (!file.exists) throw new FileNotFoundException("模型文件没有找到！")
      this.model(i) = models(i)
    }
  }
}