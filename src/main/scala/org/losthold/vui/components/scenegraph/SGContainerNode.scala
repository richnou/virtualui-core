/**
 *
 */
package org.losthold.vui.components.scenegraph

import org.losthold.vui.components.controls.ControlsBuilder

/**
 * @author rleys
 *
 */
abstract trait SGContainerNode[T] extends SGNode[T]  {

  def apply[NT <: SGNode[T]](content: NT,cl : (NT => Unit)) : NT = {node(content);cl(content);content}
  

  /**
   * This method add a new node to the current container node
   */
  def node[NT <: SGNode[T]](content: NT) : NT
  
  /**
   * Alias to node(SGNode[T])
   */
  def <=[NT <: SGNode[T]](n: NT) : NT = this.node(n)

  
  
  
}