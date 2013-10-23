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

  // Utils
  //--------------------
  
  /**
   * The provided closure should run on the UI thread
   * The default implementation just runs the closure on current Thread
   * 
   * Should be overriden by implementors
   */
  def onUIThread(cl: => Unit) = VUIBuilder.selectedImplementation[T].onUIThread({cl})
  
  // Main
  //-------------------------
  def frame(implicit cl : VuiFrame[T] => Unit) : VuiFrame[T] = apply(VUIBuilder.selectedImplementation[T].frame,cl);

  // Controls
  //--------------------
  def button(text: String)(implicit cl: VUIButton[T] => Unit) : VUIButton[T] = apply(VUIBuilder.selectedImplementation[T].button(text),cl);
  def label(text:String) : VUILabel[T] = VUIBuilder.selectedImplementation[T].label(text)
  def image(path: URL)(implicit cl: VUIImage[T] => Unit) :  VUIImage[T] = apply(VUIBuilder.selectedImplementation[T].image(path),cl);
  def text :  VUIText[T] = VUIBuilder.selectedImplementation[T].text;
  def tree : VUITree[T] = VUIBuilder.selectedImplementation.tree
  
  // Form
  //------------------
  
  def checkBox : VUICheckBox[T] =  VUIBuilder.selectedImplementation[T].checkBox()
  
  def textInput : VUIInputText[T] =  VUIBuilder.selectedImplementation[T].textInput()
  def textArea : VUIInputText[T] = VUIBuilder.selectedImplementation[T].textArea()
  
  def list(implicit cl: VUIList[T] => Unit): VUIList[T] =   apply(VUIBuilder.selectedImplementation[T].list(),cl)
  def comboBox : VUIComboBox[T] =   VUIBuilder.selectedImplementation[T].comboBox

  
  
  // Scene Graph
  //--------------------------

  def group(implicit cl : SGGroup[T] => Unit ) : SGGroup[T] = apply(VUIBuilder.selectedImplementation[T].group(),cl)

  // Layouts
  //-------------------
  def vbox : VUIVBoxLayout[T] = VUIBuilder.selectedImplementation[T].vbox
  def hbox : VUIHBoxLayout[T] = VUIBuilder.selectedImplementation[T].hbox
  def grid : VUIGridLayout[T] = VUIBuilder.selectedImplementation[T].grid
  def none : VUIFreeLayout[T] = VUIBuilder.selectedImplementation[T].none
  def stack : VUIStackPane[T] = VUIBuilder.selectedImplementation[T].stack

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
