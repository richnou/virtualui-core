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
import com.idyria.osi.vui.core.VUIBuilder
import java.util.Collection


trait ListBuilder[T] extends ListBuilderInterface[T] {
    
  def list[CT]: VUIList[CT,T] =  VUIBuilder.as[ListBuilderInterface[T]].list[CT]
  def comboBox[CT] : VUIComboBox[CT,T] =   VUIBuilder.as[ListBuilderInterface[T]].comboBox[CT]
}

trait ListBuilderInterface[T] {
  
  def list[CT] : VUIList[CT,T]
  def comboBox[CT]: VUIComboBox[CT,T]
  
}

/**
 * @author rleys
 *
 */
trait VUIList[CT,T] extends VUIComponent[T] with StylableTrait with ListModelSupport[CT] {

  type Self = VUIList[CT,T]

  //var modelImpl = new DefaultListModel
  
  // Selection Interface
  //-------------------------
  
  /**
   * Called when selected an element
   */
  def onSelected(cl: Seq[CT] => Unit)
  
  def select(obj: CT)
  def clearSelection

}

trait VUIComboBox[CT,T] extends VUIComponent[T] with StylableTrait with ComboBoxModelSupport[CT] {

  type Self = VUIComboBox[CT,T]

  //var modelImpl = new DefaultComboBoxModel
    
  /**
   * Called when selected an element
   */
  def onSelected(cl: CT => Unit)
  
  def select(obj: CT): Unit = {
    throw new RuntimeException("Not Implemented")
  } 
  
}