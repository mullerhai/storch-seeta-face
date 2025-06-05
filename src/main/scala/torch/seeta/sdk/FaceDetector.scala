package torch.seeta.sdk

/**
 * 人脸检测器 检测到的每个人脸位置，用矩形表示。
 *
 *
 */
object FaceDetector {
  object Property extends Enumeration {
    type Property = Value
    val PROPERTY_MIN_FACE_SIZE, PROPERTY_THRESHOLD, PROPERTY_MAX_IMAGE_WIDTH, PROPERTY_MAX_IMAGE_HEIGHT, PROPERTY_NUMBER_THREADS, PROPERTY_ARM_CPU_MODE = Value
    private var value = 0d ef this (value: Int) {
      this ()
      this.value = value
    }

    def getValue: Int = value
  }
}

@throws[Exception]
class FaceDetector(setting: SeetaModelSetting) {
  this.construct(setting)
  //    static {
  //        System.loadLibrary("SeetaFaceDetector600_java");
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

  @native def Detect(image: SeetaImageData): Array[SeetaRect]

  @native def set(property: FaceDetector.Property, value: Double): Unit

  @native def get(property: FaceDetector.Property): Double
  /*
      public native void SetComputingThreads(int num);
      public native int GetComputingThreads();


      public native void SetMinFaceSize(int size);
      public native int GetMinFaceSize();

      public native void SetThresh(float thresh);
      public native float GetThresh();

      public native void SetMaxImageWidth(int width);
      public native int GetMaxImageWidth();

      public native void SetMaxImageHeight(int height);
      public native int GetMaxImageHeight();
      */
}