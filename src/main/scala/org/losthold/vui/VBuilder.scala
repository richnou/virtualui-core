/**
 *
 */
package org.losthold.vui

import org.losthold.vui.components.main.MainBuilder
import org.losthold.vui.components.containers.ContainerBuilder
import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.components.layout.VuiSwitchLayout
import org.losthold.vui.components.scenegraph.SceneGraphBuilder
import org.losthold.vui.components.scenegraph.SGGroup
import org.losthold.vui.components.layout.LayoutBuilder
import org.losthold.vui.components.layout.VUIVBoxLayout
import org.losthold.vui.components.controls.VUIButton
import org.losthold.vui.components.controls.VUILabel
import org.losthold.vui.components.layout.VUIHBoxLayout
import org.losthold.vui.components.layout.VUIFreeLayout
import org.losthold.vui.components.containers.VUITabPane
import org.losthold.vui.components.containers.VUIPanel
import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.form.FormBuilder
import org.losthold.vui.components.form.VUIInputText
import org.losthold.vui.components.main.VuiFrame
import org.losthold.vui.components.form.VUIList


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