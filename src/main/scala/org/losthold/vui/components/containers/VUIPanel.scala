/**
 *
 */
package org.losthold.vui.components.containers

import org.losthold.vui.components.VUIComponent
import org.losthold.vui.components.scenegraph.SGGroup
import org.losthold.vui.VBuilder
import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.VBuilderBase
import org.losthold.vui.stdlib.form.VUIStdlibFormBuilder

/**
 * a VUIPanel is a simple container group, but customisable like a UI component
 * 
 * @author rleys
 *
 */
trait VUIPanel[T] extends VUIComponent[T] with SGGroup[T]  with VBuilderBase[T] {

  
  // Embedded Builder Interface
  //---------------------
  //def label(text:String) : VUILabel[T] 
  
  /**
   * Constructs a simple button
   */
  //def button(text: String)(cl: VUIButton[T] => Unit) : VUIButton[T]
  
}