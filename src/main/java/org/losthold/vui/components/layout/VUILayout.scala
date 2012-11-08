/**
 *
 */
package org.losthold.vui.components.layout

import org.losthold.vui.components.VUIComponent

/**
 * @author rleys
 *
 */
abstract class VUILayout[T] {

  var components = Set[VUIComponent[T]]()
  
  /**
   * Adds the specified component to the Layout list
   */
  def add( component: VUIComponent[T]) : Unit = components += component

  
  
}