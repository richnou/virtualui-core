/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.VUIBuilder

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
  def button(text: String) : VUIButton[T]


}
