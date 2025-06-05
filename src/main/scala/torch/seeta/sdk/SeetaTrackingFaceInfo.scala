package torch.seeta.sdk

class SeetaTrackingFaceInfo {
  var x = 0
  var y = 0
  var width = 0
  var height = 0
  var score = .0
  var frame_no = 0
  var PID = 0

  override def toString: String = "SeetaTrackingFaceInfo{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", score=" + score + ", frame_no=" + frame_no + ", PID=" + PID + '}'
}