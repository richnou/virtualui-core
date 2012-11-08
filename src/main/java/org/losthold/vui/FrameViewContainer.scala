/**
 *
 */
package org.losthold.vui

import org.losthold.vui.components.layout.LayoutContainer

/**
 * @author rleys
 *
 */
abstract class FrameViewContainer [+T,CT]
		(val base : T) extends ViewContainer with LayoutContainer[CT] {

  
  def title( title : String );
  
  def size (width:Int,height:Int) ;
  def width(width: Int);
  def height(height: Int);
  
  def show() : Unit;
  
}