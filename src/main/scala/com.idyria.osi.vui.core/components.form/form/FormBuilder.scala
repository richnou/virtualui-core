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

  def textInput() : VUIInputText[T]

  def list() : VUIList[T]

}
