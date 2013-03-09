/**
 *
 */
package org.losthold.vui.components.layout

import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.scenegraph.SGGroup

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