/**
 *
 */
package com.idyria.osi.vui.core



import com.idyria.osi.vui.core.components.containers._
import com.idyria.osi.vui.core.components.scenegraph._
import com.idyria.osi.vui.core.components.controls._
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.components.form._
import com.idyria.osi.vui.core.components.main._

import java.net._

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
  def image(path: URL)(implicit cl: VUIImage[T] => Unit) :  VUIImage[T] = apply(VUIBuilder.selectedImplementation[T].image(path),cl);

  // Form
  //------------------
  def textInput(implicit cl: VUIInputText[T] => Unit): VUIInputText[T] =  apply(VUIBuilder.selectedImplementation[T].textInput(),cl)
  def list(implicit cl: VUIList[T] => Unit): VUIList[T] =   apply(VUIBuilder.selectedImplementation[T].list(),cl)

  // Scene Graph
  //--------------------------

  def group(implicit cl : SGGroup[T] => Unit ) : SGGroup[T] = apply(VUIBuilder.selectedImplementation[T].group(),cl)

  // Layouts
  //-------------------
  def vbox : VUIVBoxLayout[T] = VUIBuilder.selectedImplementation[T].vbox
  def hbox : VUIHBoxLayout[T] = VUIBuilder.selectedImplementation[T].hbox
  def grid : VUIGridLayout[T] = VUIBuilder.selectedImplementation[T].grid
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
