/**
 *
 */
package com.idyria.osi.vui.core.components.form

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.components.model.ListModelSupport
import com.idyria.osi.vui.core.components.model.ListModel
import com.idyria.osi.vui.core.components.model.ComboBoxModelSupport
import com.idyria.osi.vui.core.components.model.DefaultListModel
import com.idyria.osi.vui.core.components.model.DefaultComboBoxModel

/**
 * @author rleys
 *
 */
trait VUIList[T] extends VUIComponent[T] with StylableTrait with ListModelSupport {

  type Self = VUIList[T]

  //var modelImpl = new DefaultListModel
  
  // Selection Interface
  //-------------------------
  def select(obj: AnyRef)
  def clearSelection

}

trait VUIComboBox[T] extends VUIComponent[T] with StylableTrait with ComboBoxModelSupport {

  type Self = VUIComboBox[T]

  //var modelImpl = new DefaultComboBoxModel
    
  /**
   * Called when selected an element
   */
  def onSelected(cl: Any => Unit)
  
}