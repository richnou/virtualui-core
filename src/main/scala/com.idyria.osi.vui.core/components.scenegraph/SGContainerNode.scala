/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

import com.idyria.osi.vui.core.components.controls.ControlsBuilder

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


  /**
    Clear Children components
  */
  def clear


}
