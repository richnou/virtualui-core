/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph


import com.idyria.osi.vui.core.components.layout.VUILayout

/**
 * A Group of nodes
 *
 * @author rleys
 *
 */
trait SGGroup[T] extends SGContainerNode[T] {


  def apply[NT <: SGNode[T]](g : SGGroup[T]) = {

    this.node(g)

  }


  def apply[NT <: SGNode[T]](cl: SGGroup[T] => Unit) {
    cl(this)
  }

  // Layout and constraints
  //----------------------------
  
 

  /**
   * Called to apply a layout to the group
   */
  def layout(layout: VUILayout[T])

  def layout : VUILayout[T]

}
