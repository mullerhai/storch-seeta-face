package torch.seeta.sdk.util

import torch.seeta.sdk.SeetaDevice
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util._
import java.util.logging.Logger
import java.util.stream.Collectors

/**
 * 动态加载dll
 *
 *
 */
class LoadNativeCore
object LoadNativeCore {
  private val logger = Logger.getLogger(classOf[LoadNativeCore].getName)
  /**
   * 定义dll 路径和加载顺序的文件
   */
  private val PROPERTIES_FILE_NAME = "dll.properties"
  val SEETAFACE6 = "seetaface6"

  def main(args: Array[String]): Unit = {
    LOAD_NATIVE(SeetaDevice.SEETA_DEVICE_AUTO)
    //        System.out.println(getPrefix());
    //        System.out.println(getPropertiesPath());
  }

  /**
   * 是否加载过
   */
  @volatile private var isLoaded = false

  def LOAD_NATIVE(seetaDevice: SeetaDevice): Unit = {
    if (!isLoaded) {
      val device = getDevice(seetaDevice)
      val var1 = classOf[LoadNativeCore].getResourceAsStream(getPropertiesPath)
      val properties = new Properties
      try {
        properties.load(var1)
        val baseList = new util.ArrayList[DllItem]
        val jniList = new util.ArrayList[DllItem]
        import scala.collection.JavaConversions._
        for (entry <- properties.entrySet) {
          val key = entry.getKey.asInstanceOf[String]
          val value = entry.getValue.asInstanceOf[String]
          val dllItem = new DllItem
          dllItem.setKey(key)
          if (key.contains("base")) {
            if (value.contains("tennis")) dllItem.setValue(getPrefix + "base/" + device + "/" + value)
            else dllItem.setValue(getPrefix + "base/" + value)
            baseList.add(dllItem)
          }
          else {
            dllItem.setValue(getPrefix + value)
            jniList.add(dllItem)
          }
        }
        /*
                          将文件分类
                         */
        val basePath = getSortedPath(baseList)
        val sdkPath = getSortedPath(jniList)
        val fileList = new util.ArrayList[File]
        /*
                          拷贝文件到临时目录
                         */
        import scala.collection.JavaConversions._
        for (b <- basePath) {
          fileList.add(copyDLL(b))
        }
        import scala.collection.JavaConversions._
        for (s <- sdkPath) {
          fileList.add(copyDLL(s))
        }
        // 加载 dll文件
        fileList.forEach((file: File) => {
          System.load(file.getAbsolutePath)
          logger.info(String.format("load %s finish", file.getAbsolutePath))
        })
        logger.info("............END !")
      } catch {
        case e: IOException =>
          e.printStackTrace()
      }
      isLoaded = true
    }
  }

  private def getArch = {
    var arch = System.getProperty("os.arch").toLowerCase
    if (arch.startsWith("amd64") || arch.startsWith("x86_64") || arch.startsWith("x86-64") || arch.startsWith("x64")) arch = "amd64"
    else if (arch.contains("aarch")) arch = "aarch64"
    else if (arch.contains("arm")) arch = "arm"
    arch
  }

  private def getDevice(seetaDevice: SeetaDevice) = {
    var device = "CPU"
    if ("amd64" == getArch) device = if (seetaDevice.getValue == 2) "GPU"
    else "CPU"
    device
  }

  /**
   * 获取dll配置文件路径
   *
   * @return String
   */
  private def getPropertiesPath = getPrefix + PROPERTIES_FILE_NAME

  /**
   * 返回路径文件前缀
   *
   * @return
   */
  private def getPrefix: String = {
    val arch = getArch
    //aarch64
    var os = System.getProperty("os.name")
    //Windows操作系统
    if (os != null && os.toLowerCase.startsWith("windows")) {
      //logger.info("windows系统");
      os = "/windows/"
    }
    else if (os != null && os.toLowerCase.startsWith("linux")) { //Linux操作系统
      //logger.info("linux系统");
      os = "/linux/"
    }
    else { //其它操作系统
      //安卓 乌班图等等，先不写
      return null
    }
    // "/seetaface6/windows/amd64"
    "/" + SEETAFACE6 + os + arch + "/"
  }

  /**
   * 将获得的配置进行排序 并生成路径
   *
   * @param list
   * @return List<String>
   */
  private def getSortedPath(list: util.List[DllItem]) = list.stream.sorted(Comparator.comparing((dllItem: DllItem) => {
    val i = dllItem.getKey.lastIndexOf(".") + 1
    val substring = dllItem.getKey.substring(i)
    Integer.valueOf(substring)
  })).map(DllItem.getValue).collect(Collectors.toList)

  /**
   * 复制 resource 中的dll文件到临时目录
   *
   * @param path 路径
   * @return File 文件
   * @throws IOException 异常
   */
  @throws[IOException]
  private def copyDLL(path: String) = {
    val nativeTempDir = System.getProperty("user.dir")
    val extractedLibFile = new File(nativeTempDir + File.separator + path)
    mkdirs(extractedLibFile.getParent)
    val in = classOf[LoadNativeCore].getResourceAsStream(path)
    if (in != null) writeToLocalTemp(extractedLibFile.getAbsolutePath, in)
    else throw new IOException(String.format("Could not find %s", path))
    extractedLibFile
  }

  /**
   * 将InputStream写入本地文件
   *
   * @param destination 写入本地目录
   * @param input       输入流
   * @throws IOException IOException
   */
  @throws[IOException]
  private def writeToLocalTemp(destination: String, input: InputStream): Unit = {
    var index = 0
    val bytes = new Array[Byte](1024)
    val downloadFile = new FileOutputStream(destination)
    while ((index = input.read(bytes)) != -1) {
      downloadFile.write(bytes, 0, index)
      downloadFile.flush()
    }
    input.close()
    downloadFile.close()
  }

  /**
   * 创建父级目录
   *
   * @param path
   */
  private def mkdirs(path: String): Unit = {
    //变量不需赋初始值，赋值后永远不会读取变量，在下一个变量读取之前，该值总是被另一个赋值覆盖
    var f: File = null
    try {
      f = new File(path)
      if (!f.exists) f.mkdirs
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}