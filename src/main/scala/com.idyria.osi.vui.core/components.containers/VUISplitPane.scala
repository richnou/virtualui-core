package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.styling.StylableTrait


/**
 * Main Trait for a split pane
 * Per default Vertical
 */
trait VUISplitPane[T] extends VUIComponent[T] with SGGroup[T] with StylableTrait with ApplyTrait  {
 
   type Self = VUISplitPane[T]
   
   
   // Orientation
   //----------------
   def setVertical
   def setHorizontal
   
}

object VUISplitPane {
  object Orientation extends Enumeration {
    type Orientation = Value
    val VERTICAL,HORIZONTAL = Value
  }
}