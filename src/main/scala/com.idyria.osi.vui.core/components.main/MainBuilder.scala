/**
 *
 */
package com.idyria.osi.vui.core.components.main

import com.idyria.osi.vui.core.VUIBuilder

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
