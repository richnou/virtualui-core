/**
 *
 */
package org.losthold.vui.components

import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.styling.StylableTrait

/**
 * 
 * This is a global trait for all components that are supposed to be used by VUI
 * @author rleys
 *
 */
trait VUIComponent[T] extends SGNode[T] with StylableTrait {
  
  
  //----------------------
  // Actions
  
  //----------------------
  //--  Mouse Listeners
  
  /**
   * When the mouse is pressed, but not released already
   */
  def onMousePressed(action: VUIMouseEvent => Unit) = { }
  
  /**
   * The provided closure is to be executed on the click event
   */
  def onClicked(action: => Unit) = {}
  
  /**
   * The drag event is triggered when the mouse is hold and moved on the object
   */
  def onDrag(action: VUIMouseEvent => Unit) = {}

  //---------------------------------
  //-- Keyboard listener
  def onEnter( action : => Unit) = {}
   
  //----------------------
  //-- Geometry listeners
  
  //----------------------
  //-- Positioning listeners
  def onPositionChanged(action: ( Int , Int) => Unit) = {}
  
  
  //----------------------
  // Positioning
  
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
  
}