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
  def frame : VuiFrame[T] = VUIBuilder.selectedImplementation[T].frame

  // Controls
  //--------------------
  def button(text: String) : VUIButton[T] = VUIBuilder.selectedImplementation[T].button(text)
  def label(text:String) : VUILabel[T] = VUIBuilder.selectedImplementation[T].label(text)
  def image(path: URL) :  VUIImage[T] = VUIBuilder.selectedImplementation[T].image(path)
  def text :  VUIText[T] = VUIBuilder.selectedImplementation[T].text;
  def tree : VUITree[T] = VUIBuilder.selectedImplementation.tree
  
  // Form
  //------------------
  
  def checkBox : VUICheckBox[T] =  VUIBuilder.selectedImplementation[T].checkBox()
  
  def textInput : VUIInputText[T] =  VUIBuilder.selectedImplementation[T].textInput()
  def textArea : VUIInputText[T] = VUIBuilder.selectedImplementation[T].textArea()
  
  def list: VUIList[T] =  VUIBuilder.selectedImplementation[T].list()
  def comboBox : VUIComboBox[T] =   VUIBuilder.selectedImplementation[T].comboBox

  
  
  // Scene Graph
  //--------------------------

  def group : SGGroup[T] = VUIBuilder.selectedImplementation[T].group()

  // Layouts
  //-------------------
  def vbox : VUIVBoxLayout[T] = VUIBuilder.selectedImplementation[T].vbox
  def hbox : VUIHBoxLayout[T] = VUIBuilder.selectedImplementation[T].hbox
  def grid : VUIGridLayout[T] = VUIBuilder.selectedImplementation[T].grid
  def none : VUIFreeLayout[T] = VUIBuilder.selectedImplementation[T].none
  def stack : VUIStackPane[T] = VUIBuilder.selectedImplementation[T].stack

  // Containers
  //----------------
  def tabpane : VUITabPane[T] = VUIBuilder.selectedImplementation[T].tabpane
  def panel : VUIPanel[T] = VUIBuilder.selectedImplementation[T].panel

  //def switch(content: VuiSwitchLayout[Any] => Unit) : VuiSwitchLayout[Any] = VUIBuilder.selectedImplementation[Any].switch(content)
}
trait VBuilder extends VBuilderBase[Any] {

  def apply[Any](node: Any,cl : (Any => Unit)) : Any= {
    cl(node)
    node
  }

}
