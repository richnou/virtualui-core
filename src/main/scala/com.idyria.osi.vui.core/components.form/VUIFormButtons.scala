package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.components.traits.TextTrait

/**
 * 
 */
trait VUICheckBox[T] extends VUIComponent[T] with StylableTrait with TextTrait {

  type Self = VUICheckBox[T]

 
  override def toString: String

}