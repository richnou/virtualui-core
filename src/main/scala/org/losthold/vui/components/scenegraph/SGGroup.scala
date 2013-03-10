/**
 *
 */
package org.losthold.vui.components.scenegraph


import org.losthold.vui.components.layout.VUILayout

/**
 * A Group of nodes
 * 
 * @author rleys
 *
 */
trait SGGroup[T] extends SGContainerNode[T] {

  
  def apply(g : SGGroup[T]) = {
    
    this.node(g)
    
  }
  
  
  def apply(cl: SGGroup[T] => Unit) {
    cl(this)
  }
  
  
  /**
   * Called to apply a layout to the group
   */
  def layout(layout: VUILayout[T])
  
  
  
}