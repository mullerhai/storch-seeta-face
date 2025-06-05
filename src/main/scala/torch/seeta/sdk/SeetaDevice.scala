package torch.seeta.sdk

enum SeetaDevice:
  case SEETA_DEVICE_AUTO, SEETA_DEVICE_CPU, SEETA_DEVICE_GPU
  def getValue: Int = this match
    case SEETA_DEVICE_AUTO => 0
    case SEETA_DEVICE_CPU => 1
    case SEETA_DEVICE_GPU => 2

//object SeetaDevice extends Enumeration {
//  type SeetaDevice = Value
//  val SEETA_DEVICE_AUTO, SEETA_DEVICE_CPU, SEETA_DEVICE_GPU = Value
//  private var value = 0d ef this (value: Int) {
//    this ()
//    this.value = value
//  }
//
//  def getValue: Int = value
//}