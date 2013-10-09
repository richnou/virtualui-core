/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.constraints.Constrainable

/**
 *
 * Base Node for all Scene Graph Nodes
 *
 * @author rleys
 *
 */
trait SGNode[+T] extends ListeningSupport with Constrainable {

  /**
   * base implementatino class, if the node is wrapping
   */
  def base: T

  var parent: SGGroup[Any] = null

  /**
   * Optional ID for the Node
   */
  var id: String = ""

  /**
   * Optional Name for the node
   */
  var name: String = ""

  /**
   * Set the name
   */
  def setName(str: String) = this.name = str

  /**
   * parent setup
   */
  def setParent(p: SGGroup[Any]) = {

    // Remove from actual if in one
    //---------
    this.parent match {
      case null =>
      case p =>
        p.removeChild(this)
    }

    // Set
    this.parent = p

  }

  /**
   * This method is a high level call to ask the underlying implementation
   * to make sure the node has been redrawn
   */
  def revalidate

  //def apply[NT <: SGNode[T]](cl : (NT => Unit))

  /**
   * When constraints of element are updated, call on layout manager of container to update constraints if needed
   */
  this.on("constraints.updated") {

    this.parent match {
      case null                    =>
      case p if (p.layout != null) => p.asInstanceOf[SGGroup[T]].layout.applyConstraints(this, this.fixedConstraints)
      case _                       =>
    }

  }

}
