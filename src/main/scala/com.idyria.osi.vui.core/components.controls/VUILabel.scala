/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph._

/**
 *
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
/*trait VUILabel[T] extends VUIComponent[T] {

   def apply( content: VUILabel[T] => Unit) : VUILabel[T] = {content(this);this}

   /**
        Change the text of this label
   */
   def setText(str: String)

}*/

trait VUILabel[T] extends VUIComponent[T]    {

   def apply( content: VUILabel[T] => Unit) : VUILabel[T] = {content(this);this}

   /**
        Change the text of this label
   */
   def setText(str: String)

}
