/**
 *
 */
package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.styling.CSSStylable



/**
 * a VUIPanel is a simple container group, but customisable like a UI component
 *
 * @author rleys
 *
 */
trait VUIPanel[T] extends VUIComponent[T] with SGGroup[T] with CSSStylable with ApplyTrait  {
 
   type Self = VUIPanel[T]

  // Embedded Builder Interface
  //---------------------
  //def label(text:String) : VUILabel[T]

  /**
   * Constructs a simple button
   */
  //def button(text: String)(cl: VUIButton[T] => Unit) : VUIButton[T]

   // Title in the border
   //----------------
   def borderedTitle(str:String) : Unit = {
     throw new RuntimeException("Not implemented")
     
   }
   
}
