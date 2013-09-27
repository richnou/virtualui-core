/**
 *
 */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait

/**
 * @author rleys
 *
 */
trait VUIList[T] extends VUIComponent[T] with StylableTrait {

  type Self = VUIList[T]

  def add(obj: AnyRef)

  def select(obj: AnyRef)

  // Selection Interface
  //-------------------------

  def clearSelection

}
