package com.idyria.osi.vui.core


/**
    This class is used to notifiy that a function has not been implemented, and should not be used
*/
class NotImplementedException( message : String) extends RuntimeException(message) {

  def this() = this("The current method was not implemented for the selected UI layer")
  
}
object NotImplementedException {

    def apply(msg: String) = new NotImplementedException(msg)
}
