/**
  *
  */
package org.losthold.vui.components.form

import org.losthold.vui.VUIBuilder
import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.controls.VUIButton

/**
  * @author rleys
  *
  */
trait FormBuilder[T] {

  def textInput() : VUIInputText[T] 
  
  def list() : VUIList[T]
  
}