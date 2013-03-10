/**
  *
  */
package org.losthold.vui.components.form

import org.losthold.vui.components.VUIComponent
import org.losthold.vui.VUIBuilder

/**
  * @author rleys
  *
  */
trait VUIInputText[T] extends VUIComponent[T]  {

	/**
	 * Set text of this input text
	 */
	def setText(str:String)
  
  override def toString : String 
  
  
}
