/**
 *
 */
package org.losthold.vui.components.containers

import org.losthold.vui.components.layout.VUILayout
import org.losthold.vui.components.layout.VUIVBoxLayout
import org.losthold.vui.components.layout.VuiSwitchLayout
import org.losthold.vui.components.VUIComponent

/**
 * 
 * This trait defines component creation Methods.
 * 
 * They are to be implemented by provider, and their results are usually used by ContainerTrait to pack components
 * in layout traits
 * 
 * @author rleys
 *
 */
trait ContainerBuilder[T] {

 
  /**
   * Creates a tabpane component to store nodes into panes
   */
  def tabpane : VUITabPane[T]
  
  /**
   * Creates a Tab to be inserted inside a tabpane
   */
  //def tab : VUITab[T] 
  

  def panel : VUIPanel[T]
  
  
}