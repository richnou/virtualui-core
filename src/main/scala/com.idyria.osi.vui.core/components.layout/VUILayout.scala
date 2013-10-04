/**
 *
 */
package com.idyria.osi.vui.core.components.layout

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.constraints.Constraints
import com.idyria.osi.vui.core.constraints.Constrainable

/**
 * @author rleys
 *
 */
trait VUILayout[T] extends Constrainable {

  type Self = VUILayout[T]
  
  /**
   */
  //var targetGroup: SGGroup[T] = null

  /**
   * Used to passing the group to be layouted to this layout
   */
  def setTargetGroup(grpup: SGGroup[T])

  def nodeAdded(node : SGNode[T])

  def applyConstraints(node: SGNode[T], constraints: Constraints) = {
    
  }

}
