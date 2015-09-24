/**
 *
 */
package com.idyria.osi.vui.core.stdlib.node

import com.idyria.osi.vui.core.components.scenegraph.SGNode

import scala.language.implicitConversions

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
trait SGCustomNode extends SGNode[Any] {

  type Self = SGCustomNode
  
  /**
   * ui result will be stored here
   */
  protected var uiNode : SGNode[Any] = null

  /**
   * Returns the base of created ui
   */
  def base : Any = this.getUi.base

  def revalidate = this.getUi.revalidate

  override def setName(str: String) = this.getUi.setName(str)
  
  /**
   * This must be defined by sublass.
   */
  def getUi : SGNode[Any] = {

    // Create UI if none
    if (this.uiNode==null)
    	this.uiNode = createUI
    this.uiNode
  }

   /**
   * This must be defined by sublass.
   */
  protected def createUI : SGNode[Any]

}
object SGCustomNode {

 // implicit def convertSGCustomNodeToSGNode[Any](wrapper: SGCustomNode[Any]): SGNode[Any] = wrapper.uiNode

}
