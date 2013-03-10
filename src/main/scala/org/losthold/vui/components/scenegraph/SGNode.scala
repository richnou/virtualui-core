/**
 *
 */
package org.losthold.vui.components.scenegraph

/**
 * 
 * Base Node for all Scene Graph Nodes
 * 
 * @author rleys
 *
 */
trait SGNode[T] {

  def base : T

  /**
   * This method is a high level call to ask the underlying implementation
   * to make sure the node has been redrawn
   */
  def revalidate
  
  //def apply[NT <: SGNode[T]](cl : (NT => Unit)) 
  
}