package torch.seeta.sdk

class SeetaPointF {
  var x = .0
  var y = .0

  def getX: Double = x

  def setX(x: Double): Unit = {
    this.x = x
  }

  def getY: Double = y

  def setY(y: Double): Unit = {
    this.y = y
  }

  override def toString: String = "SeetaPointF{" + "x=" + x + ", y=" + y + '}'
}