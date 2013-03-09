/**
 *
 */
package org.losthold.vui.components.scenegraph

/**
 * @author rleys
 *
 */
abstract trait SGContainerNode[T] extends SGNode[T] {

  

  /**
   * This method add a new node to the current container node
   */
  def node[NT <: SGNode[T]](content: NT) : NT
  
  /**
   * Alias to node(SGNode[T])
   */
  def <=[NT <: SGNode[T]](n: NT) : NT = this.node(n)

  
}