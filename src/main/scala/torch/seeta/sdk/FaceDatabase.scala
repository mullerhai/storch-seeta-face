package torch.seeta.sdk

/**
 * 应该是连接数据库用的，做小规模对比搜索
 */
object FaceDatabase {
  @native def SetLogLevel(level: Int): Int

  @native def GetCropFaceWidthV2: Int

  @native def GetCropFaceHeightV2: Int

  @native def GetCropFaceChannelsV2: Int

  @native def CropFaceV2(image: SeetaImageData, points: Array[SeetaPointF], face: SeetaImageData): Boolean
}

class FaceDatabase {
  //    static {
  //        System.loadLibrary("SeetaFaceRecognizer600_java");
  //    }
  var impl = 0

  def this(seetaModel: SeetaModelSetting) ={
    this()
    this.construct(seetaModel)
  }

  def this(seetaModel: SeetaModelSetting, extractionCoreNumber: Int, comparationCoreNumber: Int) ={
    this()
    this.construct(seetaModel, extractionCoreNumber, comparationCoreNumber)
  }

  @native private def construct(setting: SeetaModelSetting): Unit

  @native private def construct(setting: SeetaModelSetting, extractionCoreNumber: Int, comparationCoreNumber: Int): Unit

  @native def dispose(): Unit

  @throws[Throwable]
  override protected def finalize(): Unit = {
    super.finalize()
    this.dispose()
  }

  @native def Compare(image1: SeetaImageData, points1: Array[SeetaPointF], image2: SeetaImageData, points2: Array[SeetaPointF]): Float

  @native def CompareByCroppedFace(croppedFaceImage1: SeetaImageData, croppedFaceImage2: SeetaImageData): Float

  @native def Register(image: SeetaImageData, points: Array[SeetaPointF]): Long

  @native def RegisterByCroppedFace(croppedFaceImage: SeetaImageData): Long

  @native def Delete(index: Long): Int

  @native def Clear(): Unit

  @native def Count: Long

  @native def Query(image: SeetaImageData, points: Array[SeetaPointF]): Long

  @native def Query(image: SeetaImageData, points: Array[SeetaPointF], similarity: Array[Float]): Long

  @native def QueryByCroppedFace(croppedFaceImage: SeetaImageData): Long

  @native def QueryByCroppedFace(croppedFaceImage: SeetaImageData, similarity: Array[Float]): Long

  @native def QueryTop(image: SeetaImageData, points: Array[SeetaPointF], N: Long, index: Array[Long], similarity: Array[Float]): Long

  @native def QueryTopByCroppedFace(croppedFaceImage: SeetaImageData, N: Long, index: Array[Long], similarity: Array[Float]): Long

  @native def QueryAbove(image: SeetaImageData, points: Array[SeetaPointF], threshold: Float, N: Long, index: Array[Long], similarity: Array[Float]): Long

  @native def QueryAboveByCroppedFace(croppedFaceImage: SeetaImageData, threshold: Float, N: Long, index: Array[Long], similarity: Array[Float]): Long

  @native def RegisterParallel(image: SeetaImageData, points: Array[SeetaPointF], index: Array[Long]): Unit

  @native def RegisterByCroppedFaceParallel(croppedFaceImage: SeetaImageData, index: Array[Long]): Unit

  @native def Join(): Unit

  @native def Save(path: String): Boolean

  @native def Load(path: String): Boolean
}