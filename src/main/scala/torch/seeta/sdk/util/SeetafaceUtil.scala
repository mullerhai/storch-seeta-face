package torch.seeta.sdk.util

import torch.seeta.sdk.{SeetaImageData, SeetaRect}
import javax.imageio.ImageIO
import javax.swing._
import java.awt._
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.awt.image.ColorModel
import java.awt.image.ComponentSampleModel
import java.awt.image.WritableRaster
import java.io.File
import java.io.IOException
import java.util

/**
 *
 *  6月18日 下午1:12:42
 */
object SeetafaceUtil {
  private val BGR_TYPE = Array(0, 1, 2)

  /**
   * 将BufferedImage转为SeetaImage
   *
   * @param bufferedImage 图片
   * @return BGR属性
   *
   *  6月18日 下午1:14:39
   */

  /**
   * 将Mat 转为SeetaImage
   *
   * @param bufferedImage 图片
   * @return BGR属性
   *
   */
  //    public static SeetaImageData mat2SeetaImageData(Mat matrix) {
  //        int cols = matrix.cols();
  //        int rows = matrix.rows();
  //        int elemSize = (int) matrix.elemSize();
  //        byte[] data = new byte[cols * rows * elemSize];
  //        matrix.data().get(data);
  //        switch (matrix.channels()) {
  //            case 1:
  //                break;
  //            case 3:
  //                byte b;
  //                for (int i = 0; i < data.length; i = i + 3) {
  //                    b = data[i];
  //                    data[i] = data[i + 2];
  //                    data[i + 2] = b;
  //                }
  //                break;
  //            default:
  //                return null;
  //        }
  //        SeetaImageData seetaImageData = new SeetaImageData(cols, rows, matrix.channels());
  //        seetaImageData.data = data;
  //
  //        return seetaImageData;
  //    }
  def toSeetaImageData(bufferedImage: BufferedImage): SeetaImageData = {
    if (bufferedImage == null) throw new NullPointerException("图片不能为空.")
    try {
      val imageData = new SeetaImageData(bufferedImage.getWidth, bufferedImage.getHeight, 3)
      imageData.data = getBgr(bufferedImage)
      imageData
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
  }

  /**
   * 图片转bgr字节数组
   *
   *
   *  7月9日 下午2:28:42
   */
  private def getBgr(image: BufferedImage) = {
    var matrixBGR: Array[Byte] = null
    if (isBgr(image)) matrixBGR = image.getData.getDataElements(0, 0, image.getWidth, image.getHeight, null).asInstanceOf[Array[Byte]]
    else {
      // ARGB格式图像数据
      val intrgb = image.getRGB(0, 0, image.getWidth, image.getHeight, null, 0, image.getWidth)
      matrixBGR = new Array[Byte](image.getWidth * image.getHeight * 3)
      val len = intrgb.length
      // ARGB转BGR格式
      for (i <- 0 until len) {
        matrixBGR(i * 3) = (intrgb(i) & 0xff).toByte
        matrixBGR(i * 3 + 1) = ((intrgb(i) >> 8) & 0xff).toByte
        matrixBGR(i * 3 + 2) = ((intrgb(i) >> 16) & 0xff).toByte
      }
    }
    matrixBGR
  }

  /**
   * 判断是否为bgr
   *
   *
   *  7月9日 下午2:29:00
   */
  private def isBgr(image: BufferedImage): Boolean = {
    if (image.getType == BufferedImage.TYPE_3BYTE_BGR && image.getData.getSampleModel.isInstanceOf[ComponentSampleModel]) {
      val sampleModel = image.getData.getSampleModel.asInstanceOf[ComponentSampleModel]
      return util.Arrays.equals(sampleModel.getBandOffsets, BGR_TYPE)
    }
    false
  }

  /**
   * 转为seetaImageData
   *
   * @param path 路径
   *
   *  6月19日 下午9:06:39
   */
  def toSeetaImageData(path: String): SeetaImageData = toSeetaImageData(new File(path))

  /**
   * 转为seetaImageData
   *
   *
   *  6月19日 下午9:06:39
   */
  def toSeetaImageData(file: File): SeetaImageData = toSeetaImageData(toBufferedImage(file))

  /**
   * @param path
   * @return
   * @throws IOException
   *
   *  7月9日 下午3:03:42
   */
  def toBufferedImage(path: String): BufferedImage = toBufferedImage(new File(path))

  /**
   * @param file
   * @return
   * @throws IOException
   *
   *  7月9日 下午3:03:42
   */
  def toBufferedImage(file: File): BufferedImage = {
    var image: BufferedImage = null
    try image = ImageIO.read(file)
    catch {
      case e: IOException =>
    }
    image
  }

  /**
   * bgr转图片
   *
   * @param data   数据
   * @param width  宽
   * @param height 高
   * @return 图片
   *
   *  7月9日 下午2:30:02
   */
  def toBufferedImage(data: Array[Byte], width: Int, height: Int): BufferedImage = {
    val `type` = BufferedImage.TYPE_3BYTE_BGR
    // bgr to rgb
    //        byte b;
    //        for (int i = 0; i < data.length; i = i + 3) {
    //            b = data[i];
    //            data[i] = data[i + 2];
    //            data[i + 2] = b;
    //        }
    val image = new BufferedImage(width, height, `type`)
    image.getRaster.setDataElements(0, 0, width, height, data)
    image
  }

  def toBufferedImage(image: SeetaImageData): BufferedImage = toBufferedImage(image.data, image.width, image.height)

  /**
   * 实现图像的等比缩放
   *
   * @param source
   * @param targetW
   * @param targetH
   * @return
   */
  def resize(source: BufferedImage, targetW: Int, targetH: Int): BufferedImage = {
    // targetW，targetH分别表示目标长和宽
    val `type` = source.getType
    var target: BufferedImage = null
    var sx = targetW.toDouble / source.getWidth
    var sy = targetH.toDouble / source.getHeight
    // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
    // 则将下面的if else语句注释即可
    if (sx < sy) {
      sx = sy
      targetW = (sx * source.getWidth).toInt
    }
    else {
      sy = sx
      targetH = (sy * source.getHeight).toInt
    }
    if (`type` == BufferedImage.TYPE_CUSTOM) { // handmade
      val cm = source.getColorModel
      val raster = cm.createCompatibleWritableRaster(targetW, targetH)
      val alphaPremultiplied = cm.isAlphaPremultiplied
      target = new BufferedImage(cm, raster, alphaPremultiplied, null)
    }
    else target = new BufferedImage(targetW, targetH, `type`)
    val g = target.createGraphics
    // smoother than exlax:
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
    g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy))
    g.dispose()
    target
  }

  def writeRect(image: BufferedImage, rect: SeetaRect): BufferedImage = {
    val bi = new BufferedImage(image.getWidth, image.getHeight, image.getType)
    val gra = bi.getGraphics
    gra.drawImage(image, 0, 0, null)
    gra.setColor(Color.RED)
    gra.drawRect(rect.x, rect.y, rect.width, rect.height)
    gra.dispose()
    bi
  }

  def show(title: String, image: BufferedImage): JFrame = {
    val frame = new JFrame
    frame.setTitle(title)
    frame.setSize(image.getWidth, image.getHeight)
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE)
    val label = new JLabel
    frame.add(label)
    frame.setVisible(true)
    val icon = new ImageIcon(image)
    label.setIcon(icon)
    frame
  }
}