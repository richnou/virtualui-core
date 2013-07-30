/**
 *
 */
package com.idyria.osi.vui.core.components.layout

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SGGroup

/**
 * @author rleys
 *
 */
trait VUILayout[T] {

  /**
   */
  //var targetGroup: SGGroup[T] = null

  /**
   * Used to passing the group to be layouted to this layout
   */
  def setTargetGroup(grpup: SGGroup[T])

  def nodeAdded(node : SGNode[T])

}
