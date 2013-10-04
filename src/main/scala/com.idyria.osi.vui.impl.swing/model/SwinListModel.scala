package com.idyria.osi.vui.impl.swing.model

import com.idyria.osi.vui.core.components.model.ListModel
import com.idyria.osi.vui.core.components.model.ComboBoxModel


class SwingListModelAdapter(var listModel : ListModel) extends javax.swing.ListModel[Object] {
  
  var listeners = List[javax.swing.event.ListDataListener]()
  
  
  def  addListDataListener(x: javax.swing.event.ListDataListener): Unit = listeners = x :: listeners
  
  def getElementAt(index: Int): Object = listModel.data(index)
  
  def getSize(): Int = listModel.data.size
  
  def  removeListDataListener(l: javax.swing.event.ListDataListener): Unit = listeners = listeners.filter(_!= l)
  
}


class SwingComboBoxModelModelAdapter(var comboBoxModel : ComboBoxModel) extends SwingListModelAdapter(comboBoxModel) with javax.swing.ComboBoxModel[Object] {
  
  
  def getSelectedItem(): Object = comboBoxModel.selected.asInstanceOf[Object]
  
  def setSelectedItem(s: Any): Unit = comboBoxModel.selected = s
  
  /*def  addListDataListener(x$1: javax.swing.event.ListDataListener): Unit = ??? 
  def getElementAt(x$1: Int): Object = ??? 
  def getSize(): Int = ??? 
  def  removeListDataListener(x$1: javax.swing.event.ListDataListener): Unit = ???*/
  
}