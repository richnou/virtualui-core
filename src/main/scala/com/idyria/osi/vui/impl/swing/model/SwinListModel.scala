package com.idyria.osi.vui.impl.swing.model

import com.idyria.osi.vui.core.components.model.ListModel
import com.idyria.osi.vui.core.components.model.ComboBoxModel


class SwingListModelAdapter[CT](var listModel : ListModel[CT]) extends javax.swing.ListModel[CT] {
  
  var listeners = List[javax.swing.event.ListDataListener]()
  
  
  def  addListDataListener(x: javax.swing.event.ListDataListener): Unit = listeners = x :: listeners
  
  def getElementAt(index: Int): CT = listModel.data(index)
  
  def getSize(): Int = listModel.data.size
  
  def  removeListDataListener(l: javax.swing.event.ListDataListener): Unit = listeners = listeners.filter(_!= l)
  
}


class SwingComboBoxModelModelAdapter[CT](var comboBoxModel : ComboBoxModel[CT]) extends SwingListModelAdapter[CT](comboBoxModel) with javax.swing.ComboBoxModel[CT] {
  
  
  def getSelectedItem(): Object = comboBoxModel.selected.asInstanceOf[Object]
  
  def setSelectedItem(s: Any): Unit = comboBoxModel.selected = s.asInstanceOf[CT]
  
  /*def  addListDataListener(x$1: javax.swing.event.ListDataListener): Unit = ??? 
  def getElementAt(x$1: Int): Object = ??? 
  def getSize(): Int = ??? 
  def  removeListDataListener(x$1: javax.swing.event.ListDataListener): Unit = ???*/
  
}