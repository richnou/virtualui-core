/**
 *
 */
package com.idyria.osi.vui.core.components.layout

import com.idyria.osi.vui.core.components.containers.ContainerTrait
import com.idyria.osi.vui.core.components.scenegraph._

/**
 *
 * Trait for containers that may be layouted
 * @author rleys
 *
 */
trait LayoutContainer[T] extends SGGroup[T] {


    var definedLayout : VUILayout[T] = null

    /**
     * Add The node to the jpanel
     */
    override def node[NT <: SGNode[T]](nd: NT): NT = {

      super.node(nd)
      
      // If we have a layout, notify it
      if (definedLayout!=null) {
        definedLayout nodeAdded nd
      }

      nd

    }

    def layout(l: VUILayout[T]) = {

       this.definedLayout = l
       l.setTargetGroup(this)

    }

    def layout : VUILayout[T] = this.definedLayout

  /**
   * Main interface to trigger a layout operation
   */
  //def layout( layout: VUIVBoxLayout[T]) : Unit;


}
