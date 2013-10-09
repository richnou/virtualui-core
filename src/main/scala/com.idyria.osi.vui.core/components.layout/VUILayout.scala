/**
 *
 */
package com.idyria.osi.vui.core.components.layout

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.constraints.Constraints
import com.idyria.osi.vui.core.constraints.Constrainable
import com.idyria.osi.vui.core.constraints.Constraint

/**
 * @author rleys
 *
 */
trait VUILayout[T] extends Constrainable {

  type Self = VUILayout[T]

  /**
   */
  //var targetGroup: SGGroup[T] = null

  // Constraints Listen
  //--------------
  
  // On Node add to this layout:
  // - Apply existing constraints
  // - Listen to existing constraints
  val nodeAddListener = this.onWith("node.added") {
    child: SGNode[T] =>

      // Already existing constraints
      //--------
      this.applyConstraints(child, child.fixedConstraints)

      // Added constraints
      //--------------
      child.on("constraints.updated") {
        this.applyConstraints(child, child.fixedConstraints)
      }
  }
  /**
   * Used to passing the group to be layouted to this layout
   *
   * @event node.added
   */
  def setTargetGroup(group: SGGroup[T]) = {

    // Already Existing Nodes
    //----------------
    group.children.foreach {
      node =>
        this.@->("node.added", node)
    }

    // On Group child Added, add to this layout
    //---------------
    val groupChildAdded  = group.onWith("child.added") {
      child: SGNode[T] =>

        this.@->("node.added", child)

    }
    
    // On Child remove, deregister this listener from the child
    //--------------
    val groupChildRemoved = group.onWith("child.removed") {
      child: SGNode[T] =>

        child.deregister(this)
        
        

    }

  }

  def applyConstraints(node: SGNode[T], constraints: Constraints) = {

  }

}

