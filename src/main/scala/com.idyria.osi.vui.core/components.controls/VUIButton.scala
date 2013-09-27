/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.styling.ApplyTrait


/**
 *
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
trait VUIButton[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

    type Self = VUIButton[T]
  
    //------------------------
    // General
    //------------------------
    /*override def disable = {
        throw NotImplementedException(s"button.disable not implemented in ${getClass}")
    }*/


}
