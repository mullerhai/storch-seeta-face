package torch.seeta.proxy

import torch.seeta.pool.{FaceRecognizerPool, SeetaConfSetting}
import torch.seeta.sdk.{FaceRecognizer, SeetaImageData, SeetaPointF}

/**
 * 人脸特征提取评估器
 */
class FaceRecognizerProxy  {
  private var pool: FaceRecognizerPool = null

  def this(config: SeetaConfSetting) = {
    this()
    this.pool = new FaceRecognizerPool(config)
  }

  def extract(image: SeetaImageData, points: Array[SeetaPointF]): Array[Float] = {
    var features: Array[Float] = null
    var faceRecognizer: FaceRecognizer = null
    try {
      faceRecognizer = pool.borrowObject
      features = new Array[Float](faceRecognizer.GetExtractFeatureSize)
      faceRecognizer.Extract(image, points, features)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceRecognizer != null) pool.returnObject(faceRecognizer)
    features
  }

  def getExtractFeatureSize: Int = {
    var faceRecognizer: FaceRecognizer = null
    try {
      faceRecognizer = pool.borrowObject
      if (faceRecognizer != null) return faceRecognizer.GetExtractFeatureSize
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceRecognizer != null) pool.returnObject(faceRecognizer)
    0
  }

  def calculateSimilarity(features1: Array[Float], features2: Array[Float]): Float = {
    var score = -1f
    var faceRecognizer: FaceRecognizer = null
    try {
      faceRecognizer = pool.borrowObject
      score = faceRecognizer.CalculateSimilarity(features1, features2)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (faceRecognizer != null) pool.returnObject(faceRecognizer)
    score
  }

  def cosineSimilarity(leftVector: Array[Float], rightVector: Array[Float]): Float = {
    var dotProduct = 0f
    for (i <- 0 until leftVector.length) {
      dotProduct = leftVector(i) * rightVector(i) + dotProduct
    }
    var d1 = 0.0d
    for (value <- leftVector) {
      d1 += Math.pow(value, 2)
    }
    var d2 = 0.0d
    for (value <- rightVector) {
      d2 += Math.pow(value, 2)
    }
    var cosineSimilarity = .0
    if (d1 <= 0.0 || d2 <= 0.0) cosineSimilarity = 0.0
    else cosineSimilarity = dotProduct / (Math.sqrt(d1) * Math.sqrt(d2))
    cosineSimilarity.toFloat
  }
}