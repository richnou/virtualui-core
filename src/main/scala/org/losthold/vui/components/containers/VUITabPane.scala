/**
 * 
 */
package org.losthold.vui.components.containers

import org.losthold.vui.components.VUIComponent
import org.losthold.vui.components.scenegraph.SGContainerNode
import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.scenegraph.SGGroup
import org.losthold.vui.VBuilder
import org.losthold.vui.VBuilderBase


/**
 * @author rleys
 *
 */
trait VUITabPane[T] extends VUIComponent[T] with SGContainerNode[T] with VBuilderBase[T] {

  // Overrides The normal add functions to be able to add a title
  //------------------------
  
  /**
   * Not overridable anymore, because implementation switches to calls giving title 
   * and/or applying closure for tab component
   */
  final def node[NT <: SGNode[T]](content: NT) : NT = node[NT]("")(content)
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
    var tabgroup  =  this <=(title,group{g => })
    
    tabgroup
    
  }
  
  
}
