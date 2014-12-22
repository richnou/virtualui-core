package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.components.traits.TextTrait
import com.idyria.osi.vui.core.styling.ApplyTrait

/**
 * 
 */
trait VUICheckBox[T] extends VUIComponent[T] with StylableTrait with TextTrait with ApplyTrait {

  type Self = VUICheckBox[T]

  /**
   * Returns true if the checkbox is checked or false otherwise
   */
  def isChecked : Boolean
 
  override def toString: String

}