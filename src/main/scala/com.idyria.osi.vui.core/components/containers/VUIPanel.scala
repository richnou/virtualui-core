/**
 *
 */
package com.idyria.osi.vui.core.components.containers

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.core.VBuilderBase
import com.idyria.osi.vui.core.stdlib.form.VUIStdlibFormBuilder

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
