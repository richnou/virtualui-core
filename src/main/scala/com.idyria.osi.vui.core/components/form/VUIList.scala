/**
  *
  */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent

/**
  * @author rleys
  *
  */
trait VUIList [T] extends VUIComponent[T]  {

	def add(obj : AnyRef)


}
