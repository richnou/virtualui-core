/**
 *
 */
package org.losthold.vui.swing

import org.losthold.vui.components.containers.ContainerTrait

import javax.swing.JPanel

/**
 * @author rleys
 *
 */
trait SwingContainerTrait extends ContainerTrait {

  /**
   * Construct a Swing Panel
   */
  def panel(content:  => Unit) = {
    
    val panel = new JPanel();
    content
    //return panel
  }
  
  
}