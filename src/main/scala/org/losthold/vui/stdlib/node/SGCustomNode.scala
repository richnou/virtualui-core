/**
 *
 */
package org.losthold.vui.stdlib.node

import org.losthold.vui.components.scenegraph.SGNode

/**
 * This class is a convinience class to implement a user specific class
 * that should be integrated into VUI.
 * 
 * The user must only provide the method creating the real ui, like this:
 * 
 * class MyNode extends SGNodeWrapper with SwingVUI {
 * 
 * 	var ui = container {
 * 		container =>
 * 			....
 *  }
 * 
 * }
 * 
 * 
 * @author rleys
 *
 */
abstract class SGCustomNode[T] extends SGNode[T] {

  /**
   * ui result will be stored here
   */
  protected var uiNode : SGNode[T] = null
  
  /**
   * Returns the base of created ui
   */
  def base : T = this.getUi.base
  
  def revalidate = this.getUi.revalidate
  
  /**
   * This must be defined by sublass.
   */
  def getUi : SGNode[T] = {
    
    // Create UI if none
    if (this.uiNode==null)
    	this.uiNode = createUI
    this.uiNode
  }
  
  protected def createUI : SGNode[T]
   
}
object SGCustomNode {
  
  implicit def convertSGCustomNodeToSGNode[T](wrapper: SGCustomNode[T]): SGNode[T] = wrapper.uiNode
  
}