/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph._
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.components.traits.TextTrait
import com.idyria.osi.vui.core.styling.ApplyTrait

/**
 *
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
trait VUILabel[T] extends VUIComponent[T] with StylableTrait with TextTrait with ApplyTrait   {

  type Self = VUILabel[T]
  

}
