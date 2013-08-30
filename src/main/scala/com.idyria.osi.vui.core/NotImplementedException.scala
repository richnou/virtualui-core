package com.idyria.osi.vui.core


/**
    This class is used to notifiy that a function has not been implemented, and should not be used
*/
class NotImplementedException( message : String) extends RuntimeException(message) {


}
object NotImplementedException {

    def apply(msg: String) = new NotImplementedException(msg)
}
