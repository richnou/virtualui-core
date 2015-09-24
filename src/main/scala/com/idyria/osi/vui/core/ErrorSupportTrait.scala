package com.idyria.osi.vui.core

/**
 * @author zm4632
 */
trait ErrorSupportTrait {
  
  
  // Error Catching
  //----------------------
  
  /**
   * Catch Any Exception caused by the provided closure
   * Dispatch it to main error Catching
   */
  def catchError(cl: => Unit) = {
    
    try {
      cl
    } catch {
      case e : Throwable => VUIError.dispatchError(e)
    }
    
  }
}

/**
 * Singleton to dispatch errors
 */
object VUIError {
  
  var errorHandlers = List[(Throwable => Unit)]( )
  
  def onError(cl: Throwable => Unit) = this.errorHandlers = this.errorHandlers :+ cl
  
  def dispatchError(e: Throwable) = {
    
    errorHandlers foreach {
      handler => 
        
        try {
          handler(e)
        } catch {
          case e : Throwable => 
            //e.printStackTrace()
        }
    }
  }
  
}