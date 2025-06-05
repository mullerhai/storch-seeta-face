package torch.seeta.sdk

class SeetaImageData(var width: Int, var height: Int, var channels: Int) {
  this.data = new Array[Byte](width * height * channels)
  var data: Array[Byte] = null

  def this(width: Int, height: Int) ={
    this(width, height, 1)
  }
}