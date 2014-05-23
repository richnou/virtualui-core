/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.components.traits.TextTrait
import com.idyria.osi.vui.core.styling.CSSStylable

/**
 *
 * Trait to define requirements for a VUI  text component
 * @author rleys
 *
 */
trait VUIText[T] extends VUIComponent[T] with CSSStylable   with TextTrait  {
 
  type Self = VUIText[T]
  
 
  def println(str: String) = {
    this.setText(this.getText + s"$str\n")
  }
  
}
