/**
  *
  */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.controls.VUIButton
import com.idyria.osi.vui.core.components.controls.VUIRadioButton
import com.idyria.osi.vui.core.components.controls.ToggleGroup

/**
  * @author rleys
  *
  */
trait FormBuilder[T] {

  // Data
  //------------------
    

  
  // Text Inputs
  //----------------
  
  /**
   * This is a simple text input
   */
  def textInput: VUIInputText[T] = VUIBuilder.as[FormBuilderInterface[T]].textInput
  
  /**
   * Also a text input but beeing a text area
   */
  def textArea : VUIInputText[T] = VUIBuilder.as[FormBuilderInterface[T]].textArea

  // Buttons
  //------------------
  
  def checkBox : VUICheckBox[T] = VUIBuilder.as[FormBuilderInterface[T]].checkBox

  def radioButton : VUIRadioButton[T] = VUIBuilder.as[FormBuilderInterface[T]].radioButton
  
  def radioButton(text: String) : VUIRadioButton[T] = {
    var b = VUIBuilder.as[FormBuilderInterface[T]].radioButton
    b.setText(text)
    b
  } 
  
  
  def toggleGroup : ToggleGroup = VUIBuilder.as[FormBuilderInterface[T]].toggleGroup
  
}


trait FormBuilderInterface[T] {
  
 
  
  // Text Inputs
  //----------------
  
  /**
   * This is a simple text input
   */
  def textInput() : VUIInputText[T]
  
  /**
   * Also a text input but beeing a text area
   */
  def textArea() : VUIInputText[T]

  // Buttons
  //------------------
  
  def checkBox : VUICheckBox[T]

  def radioButton : VUIRadioButton[T]
  
  def toggleGroup : ToggleGroup
}
