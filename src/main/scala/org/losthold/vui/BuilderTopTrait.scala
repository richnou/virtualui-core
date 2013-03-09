/**
 *
 */
package org.losthold.vui

import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.components.containers.ContainerBuilder

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