/**
 *
 */
package com.idyria.osi.vui.core.components.layout

/**
 * @author rleys
 *
 */
trait LayoutBuilder[T] {

  /**
   * Creates a layout to organise nodes in a vertical box
   */
  def vbox : VUIVBoxLayout[T]

  /**
   * Creates a layout to organise nodes in an horizontal box
   */
  def hbox : VUIHBoxLayout[T]

  /**
   * Creates a layout to organise nodes depending on each specified position
   */
  def none : VUIFreeLayout[T]


}
