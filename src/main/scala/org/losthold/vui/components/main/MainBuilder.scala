/**
 *
 */
package org.losthold.vui.components.main

import org.losthold.vui.VUIBuilder

/**
 * @author rleys
 *
 */
trait MainBuilder[T] {

  
  def frame( ) : VuiFrame[T]
  
  /*= {
    
    //-- Create Frame
    var frame = createFrame()
    
    //-- Execute content
    content(frame)
    
    //-- Return
    return frame
    
    
  }*/
  
  
}