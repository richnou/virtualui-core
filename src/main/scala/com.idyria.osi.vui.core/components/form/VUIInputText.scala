/**
  *
  */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.VUIBuilder

import com.idyria.osi.vui.core.components.model._

/**
  * @author rleys
  *
  */
trait VUIInputText[T] extends VUIComponent[T] with TextModelSupport  {

	/**
	 * Set text of this input text
	 */
	def setText(str:String)


  override def toString : String



}
