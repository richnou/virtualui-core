/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core._
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.styling.CSSStylable

/**
 *
 * Trait to define requirements for a Label
 * @author rleys
 *
 */
trait VUIRadioButton[T] extends VUIComponent[T] with CSSStylable with ApplyTrait {

  type Self = VUIRadioButton[T]

  //------------------------
  // General
  //------------------------
  /*override def disable = {
        throw NotImplementedException(s"button.disable not implemented in ${getClass}")
    }*/
  
  /**
   * The the label of the button
   */
  def setText(str:String)
  def getText : String

  /**
   * Returns true if the checkbox is checked or false otherwise
   */
  def isChecked: Boolean
  
  /**
   * Sets the "checked" state of the button
   */
  def setChecked(b:Boolean)

  // Toggle Group
  //------------------
  var toggleGroup: ToggleGroup = null
  def setToggleGroup(tg: ToggleGroup): Unit = {
    this.toggleGroup = tg
    tg(this)
    this.@->("togglegroup", tg)

  }

}

trait ToggleGroup {

  var buttons = List[VUIRadioButton[_]]()

  /**
   * Listener triggered when a selection changed in the group 
   */
  def onSelected(cl: VUIRadioButton[_] => Unit) : Unit = {
    throw new NotImplementedException("")
  }
  
  /**
   * Listener triggered when a selection changed in the group 
   */
  def onSelectedIndex(cl: Int => Unit) : Unit = {
    throw new NotImplementedException("")
  }
  
  /**
   * Record a new button in this group
   * If it is the first button, select it
   */
  def apply(b: VUIRadioButton[_]) = {

   
    this.buttons = this.buttons :+ b
    
     if (this.buttons.size == 1) {
      b.setChecked(true)
    }

  }

  /**
   * Returns the actual selected button, or -1 if none
   */
  def selectedIndex: Int = {

    buttons.zipWithIndex.find {
      case (b,i) => b.isChecked
    } match {
      case Some((b,i)) => i
      case None => -1
    }
    
  }

  def selected : Option[VUIRadioButton[_]] = {
    buttons.find {
      case b => b.isChecked
    } 
  }
  
}