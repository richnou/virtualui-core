/**
 *
 */
package org.losthold.vui.components.main

import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.containers.ContainerTrait
import org.losthold.vui.components.scenegraph.SGContainerNode

/**
 * @author rleys
 *
 */
trait VuiFrame[T] extends SGContainerNode[T] with ContainerTrait {

  
  def title( title : String ); 
  
  // Events
  //----------------
  
  /**
   * Do something on close
   */
  def onClose(cl: => Unit) = {}
  
  
  def size (width:Int,height:Int) ;
  def width(width: Int);
  def height(height: Int);
  def show();
  
  
}