/**
 *
 */
package com.idyria.osi.vui.core

import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.core.components.containers.ContainerBuilder

/**
 * @author rleys
 *
 */
abstract class BuilderTopTrait[T] extends ContainerBuilder[T] with ControlsBuilder[T] {

  var current : Unit

  /*def  frame (content : FrameViewContainer[AnyRef,T] => Unit) : FrameViewContainer[AnyRef,T] =  {

    //-- Create Frame
    val frame = createFrame()

    //-- Execute content
    current = frame
    println("Current: "+current);
    content(frame)
    //frame doContent(content)

    //-- Return
    return frame


  }*/


  /**
   * Builder Method to be implemented
   */
  //def createFrame() : FrameViewContainer[AnyRef,T];

}
