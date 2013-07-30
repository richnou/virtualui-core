/**
 *
 */
package com.idyria.osi.vui.core

import com.idyria.osi.vui.core.components.main.MainBuilder
import com.idyria.osi.vui.core.components.containers.ContainerBuilder
import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.core.components.layout.VuiSwitchLayout
import com.idyria.osi.vui.core.components.scenegraph.SceneGraphBuilder
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.layout.LayoutBuilder
import com.idyria.osi.vui.core.components.layout.VUIVBoxLayout
import com.idyria.osi.vui.core.components.controls.VUIButton
import com.idyria.osi.vui.core.components.controls.VUILabel
import com.idyria.osi.vui.core.components.layout.VUIHBoxLayout
import com.idyria.osi.vui.core.components.layout.VUIFreeLayout
import com.idyria.osi.vui.core.components.containers.VUITabPane
import com.idyria.osi.vui.core.components.containers.VUIPanel
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.form.FormBuilder
import com.idyria.osi.vui.core.components.form.VUIInputText
import com.idyria.osi.vui.core.components.main.VuiFrame
import com.idyria.osi.vui.core.components.form.VUIList


/**
 * @author rleys
 *
 */
trait VBuilderBase[T]  {

  implicit val defaultClosure : (SGNode[T] => Unit) = { t => }


  def apply[NT <: SGNode[T]](content: NT,cl : (NT => Unit)) : NT

  // Main
  //-------------------------
  def frame(implicit cl : VuiFrame[T] => Unit) : VuiFrame[T] = apply(VUIBuilder.selectedImplementation[T].frame,cl);

  // Controls
  //--------------------
  def button(text: String)(implicit cl: VUIButton[T] => Unit) : VUIButton[T] = apply(VUIBuilder.selectedImplementation[T].button(text),cl);
   def label(text:String)(implicit cl: VUILabel[T] => Unit) : VUILabel[T] = apply(VUIBuilder.selectedImplementation[T].label(text),cl);

  // Form
  //------------------
  def textInput(implicit cl: VUIInputText[T] => Unit): VUIInputText[T] =  apply(VUIBuilder.selectedImplementation[T].textInput(),cl)
  def list(implicit cl: VUIList[T] => Unit): VUIList[T] =   apply(VUIBuilder.selectedImplementation[T].list(),cl)

  // Scene Graph
  //--------------------------

  def group(cl : SGGroup[T] => Unit ) : SGGroup[T] = apply(VUIBuilder.selectedImplementation[T].group(),cl)

  // Layouts
  //-------------------
  def vbox : VUIVBoxLayout[T] = VUIBuilder.selectedImplementation[T].vbox
  def hbox : VUIHBoxLayout[T] = VUIBuilder.selectedImplementation[T].hbox
  def none : VUIFreeLayout[T] = VUIBuilder.selectedImplementation[T].none

  // Containers
  //----------------
  def tabpane(implicit cl: VUITabPane[T] => Unit) : VUITabPane[T] = apply(VUIBuilder.selectedImplementation[T].tabpane,cl);
  def panel(implicit cl: VUIPanel[T] => Unit) : VUIPanel[T] = {apply(VUIBuilder.selectedImplementation[T].panel,cl)}

  //def switch(content: VuiSwitchLayout[Any] => Unit) : VuiSwitchLayout[Any] = VUIBuilder.selectedImplementation[Any].switch(content)
}
trait VBuilder extends VBuilderBase[Any] {

  def apply[Any](node: Any,cl : (Any => Unit)) : Any= {
    cl(node)
    node
  }

}
