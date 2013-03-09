/**
 *
 */
package org.losthold.vui.components.controls

import org.losthold.vui.VUIBuilder

/**
 * 
 * This trait defines the methods used to create the VUI controls
 * 
 * @author rleys
 *
 */
trait ControlsBuilder[T] {

  /**
   * Contstruct a simple text display
   */
  def label(text:String) : VUILabel[T] 
  
  /**
   * Constructs a simple button
   */
  def button(text: String)(cl: VUIButton[T] => Unit) : VUIButton[T]
  
  
}