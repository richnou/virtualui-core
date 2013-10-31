/**
 *
 */
package com.idyria.osi.vui.core.components

import com.idyria.osi.vui.core.components.events._
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.styling.ApplyTrait

/**
 *
 * This is a global trait for all components that are supposed to be used by VUI
 * @author rleys
 *
 */
trait VUIComponent[T] extends SGNode[T]  with ApplyTrait {


  //----------------------
  // General Control
  //----------------------

  //-- Enable/Disable
  
  /**
    To be overriden if the component can be disabled
  */
  def disable : Unit 
  
  /**
   *  To be overriden if the component can be enabled
   */
  def enable
  
  /**
   * Calls disable/enable based on provided state
   */
  def setEnabled(state: Boolean) = state match {
    case true => enable
    case false => disable
  }

  //-- Visible/Invisible
  def setVisible(state:Boolean) : Unit = {
    throw new RuntimeException("Not Implemented")
  }
  
  //----------------------
  // Actions  
  //----------------------

  //----------------------
  //--  Mouse Listeners


  /**
   * When the mouse is pressed, but not released already
   */
  def onMousePressed(action: VUIMouseEvent => Unit) = { }

  /**
   * The provided closure is to be executed on the click event
   */
  def onClicked(action: => Any) : Unit = this.onClicked{e : VUIClickEvent => action}

  /**
    Alias for @see #onClicked
  */
  def onClick(action: => Any) : Unit = onClicked(action)
  
  /**
   * To be overrided
   */
  def onClicked(action: VUIClickEvent => Any) : Unit = {  }
  
  def onClick(action: VUIClickEvent => Any) : Unit = onClicked(action)
  
  /**
   * Special Double click function
   */
  def onDoubleClick(action: => Any) : Unit = onClicked {
    e : VUIClickEvent => e.clickCount match {
      case 2 => action
      case _ => 
    }
  }
  
  /**
   * The drag event is triggered when the mouse is hold and moved on the object
   */
  def onDrag(action: VUIMouseEvent => Unit) = {}

  //---------------------------------
  //-- Keyboard listener
  def onEnter( action : => Unit) = {}

  //----------------------
  //-- Geometry listeners

  def onShown(action: => Unit) = {}


  //----------------------
  //-- Positioning listeners
  def onPositionChanged(action: ( Int , Int) => Unit) = {}


  //----------------------
  // Positioning
  //----------------------
  /**
   * Set the position of the component
   */
  def setPosition(x:Int,y:Int)

   /**
   * Get the position of the component
   */
  def getPosition : Pair[Int,Int]

  def setX(x:Int) = setPosition(x,getY)
  def getX : Int = getPosition._1
  /**
   * Adds the provided diff to the x position
   * if diff is negative, it updates the position consequently
   */
  def deltaX(diff: Int) = setX(getX+diff)

  def setY(y:Int) = setPosition(getX,y)

  /**
   * Adds the provided diff to the x position
   * if diff is negative, it updates the position consequently
   */
  def deltaY(diff: Int) = setY(getY+diff)

  def getY : Int = getPosition._2



  //----------------------
  // Styling
  //----------------------

  def size(width: Int, height: Int)

}
