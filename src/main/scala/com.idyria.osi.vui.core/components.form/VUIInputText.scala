/**
 *
 */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.components.model._
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.components.traits.TextTrait
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.styling.CSSStylable
import com.idyria.osi.vui.core.NotImplementedException

/**
 * @author rleys
 *
 */
trait VUIInputText[T] extends VUIComponent[T] with TextModelSupport with TextTrait with CSSStylable with ApplyTrait {

  type Self = VUIInputText[T]


  override def toString: String
  
  def clearText = setText("")
  
  // Columns
  //-----------------
  def setColumns(c:Int) : Unit = {
    throw new NotImplementedException("")
  }
  
  // Print Interface
  //--------------------
  def println(str: String) = {
    this.model.insertText(s"$str\n")
  } 

}
