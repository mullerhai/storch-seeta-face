package torch.seeta.proxy

import torch.seeta.pool.{FaceDetectorPool, SeetaConfSetting}
import torch.seeta.sdk.{FaceDetector, SeetaImageData, SeetaRect}

/**
 * 人脸位置评估器
 */
class FaceDetectorProxy  {
  private var faceDetectorPool: FaceDetectorPool = null

  def this(config: SeetaConfSetting)= {
    this()
    faceDetectorPool = new FaceDetectorPool(config)
  }

  @throws[Exception]
  def detect(image: SeetaImageData): Array[SeetaRect] = {
    var faceDetector: FaceDetector = null
    var detect: Array[SeetaRect] = null
    try {
      faceDetector = faceDetectorPool.borrowObject
      detect = faceDetector.Detect(image)
    } finally if (faceDetector != null) faceDetectorPool.returnObject(faceDetector)
    detect
  }
  //    public void setProperty(FaceDetector.Property property, double value) throws Exception {
  //
  //        FaceDetector faceDetector = null;
  //        SeetaRect[] detect;
  //        try {
  //            faceDetector = faceDetectorPool.borrowObject();
  //
  //        }finally {
  //            if (faceDetector != null) {
  //                faceDetectorPool.returnObject(faceDetector);
  //            }
  //        }
  //
  //    }
}