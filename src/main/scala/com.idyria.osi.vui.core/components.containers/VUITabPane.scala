/**
 *
 */
package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph._
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.core.VBuilderBase
import com.idyria.osi.vui.core.styling.StylableTrait


/**
 * @author rleys
 *  
  
 *
 */
trait VUITabPane[T] extends VUIComponent[T] with SGGroup[T] with VBuilderBase[T] with StylableTrait {

   type Self = VUITabPane[T] 
  
  // Overrides The normal add functions to be able to add a title
  //------------------------

  /**
   * Not overridable anymore, because implementation switches to calls giving title
   * and/or applying closure for tab component
   */
  final override def node [NT <: SGNode[T]](content: NT) : NT = node[NT]("")(content)
  final def <=[NT <: SGNode[T]](title:String,content:NT)  = node[NT](title)(content)

  /**
   * Node method override to add components as tabs
   */
  def node[NT <: SGNode[T]](title:String)(content:NT) : NT

  // Special methods to add tabs and control them
  //------------------

  /**
   * Add a titled tab
   */
  def <= (title:String)(ct: SGGroup[T] ) : SGGroup[T] = {

    // Create  group
    var tabgroup  =  this <=(title,group)

    tabgroup

  }

  // This component cannot really be layouted
  //---------------------
  final def layout(layout: VUILayout[T]) = {

  }


}
