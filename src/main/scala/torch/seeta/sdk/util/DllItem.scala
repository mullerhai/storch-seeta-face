package torch.seeta.sdk.util

class DllItem {
  private var key: String = null
  private var value: String = null

  def getKey: String = key

  def setKey(key: String): Unit = {
    this.key = key
  }

  def getValue: String = value

  def setValue(value: String): Unit = {
    this.value = value
  }
}