/**
  *
  */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.controls.VUIButton

/**
  * @author rleys
  *
  */
trait FormBuilder[T] {

  // Data
  //------------------
    
  def list() : VUIList[T]
  
  def comboBox : VUIComboBox[T]
  
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

}
