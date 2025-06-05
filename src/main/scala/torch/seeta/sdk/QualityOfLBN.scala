package torch.seeta.sdk

/**
 * 深度学习的人脸清晰度评估器
 * 这个识别器不怎么好
 */
object QualityOfLBN {
  object LIGHTSTATE extends Enumeration {
    type LIGHTSTATE = Value
    val BRIGHT, DARK = Value
  }

  object BLURSTATE extends Enumeration {
    type BLURSTATE = Value
    val CLEAR, BLUR = Value
  }

  object NOISESTATE extends Enumeration {
    type NOISESTATE = Value
    val HAVENOISE, NONOISE = Value
  }

  object Property extends Enumeration {
    type Property = Value
    val PROPERTY_NUMBER_THREADS, PROPERTY_ARM_CPU_MODE, PROPERTY_LIGHT_THRESH, PROPERTY_BLUR_THRESH, PROPERTY_NOISE_THRESH = Value
    private var value = 0d ef this (value: Int) {
      this ()
      this.value = value
    }

    def getValue: Int = value
  }
}

class QualityOfLBN @throws[Exception]
(setting: SeetaModelSetting) {
  this.construct(setting)
  //    static {
  //        System.loadLibrary("QualityAssessor300_java");
  //    }
  var impl = 0

  @native
  @throws[Exception]
  private def construct(setting: SeetaModelSetting): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  /**
   * @param imageData [input] image data
   * @param points    [input] face points
   * @param light     [output] 0 means light is good, 1 is not [not recommend]      0表示光线好，1表示不好[不推荐]
   * @param blur      [output] o means face is clear, 1 means face is blur          o 表示脸部清晰，1 表示脸部模糊
   * @param noise     [output] 0 means having noise ,1 not [not recommend]          0 表示有噪音，1 表示没有 [不推荐]
   */
  @native def Detect(imageData: SeetaImageData, points: Array[SeetaPointF], light: Array[Int], blur: Array[Int], noise: Array[Int]): Unit

  @native def set(property: QualityOfLBN.Property, value: Double): Unit

  @native def get(property: QualityOfLBN.Property): Double
}