package com.idyria.osi.vui.core.components.model


trait ListModelSupport extends ModelSupport[ListModel] {
  
  modelImpl = new DefaultListModel
  
  

  
  
}

trait ComboBoxModelSupport extends ModelSupport[ComboBoxModel] {
  
  modelImpl = new DefaultComboBoxModel
  
}

trait ListModel {
 
  var data  = List[Object]()
  
  /**
   * Add an object to the model
   */
  def add(o: Object) = data = o :: data
  
  /**
   * If the object is already in the list
   */
  def contains(o: Object) : Boolean = data.contains(o)
  
  
}

trait ComboBoxModel extends ListModel {
  
  var selected : Any = null
  
}

class DefaultListModel extends ListModel {
  
}

class DefaultComboBoxModel extends  ComboBoxModel {
  
}