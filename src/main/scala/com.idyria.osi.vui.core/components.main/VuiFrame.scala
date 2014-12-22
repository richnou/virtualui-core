/**
 *
 */
package com.idyria.osi.vui.core.components.main

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.containers.ContainerTrait
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.styling.ApplyTrait

/**
 * @author rleys
 *
 */
trait VuiFrame[T] extends SGGroup[T] with ApplyTrait {

  type Self = VuiFrame[T]

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
  def close();


}
