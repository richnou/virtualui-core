/**
 *
 */
package com.idyria.osi.vui.core.components

import com.idyria.osi.vui.core.components.events._

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.styling.StylableTrait

import com.idyria.osi.vui.core._

/**
 *
 * This is a global trait for all components that are supposed to be used by VUI
 * @author rleys
 *
 */
trait VUIComponent[T] extends SGNode[T] {


  //----------------------
  // General Control
  //----------------------

  /**
    To be overriden is the component can be disabled
  */
  def disable : Unit 

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
  def onClicked(action: => Any) = {}

  /**
    Alias for @see #onClicked
  */
  def onClick(action: => Any) = onClicked(action)
  
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
