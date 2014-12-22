package com.idyria.osi.vui.core.components.model

import com.idyria.osi.tea.listeners.ListeningSupport


trait ListModelSupport[CT] extends ModelSupport[ListModel[CT]] {
  
  modelImpl = new DefaultListModel[CT]
  
  

  
  
}

trait ComboBoxModelSupport[CT] extends ModelSupport[ComboBoxModel[CT]] {
  
  modelImpl = new DefaultComboBoxModel[CT]
  
}

trait ListModel[CT] extends ListeningSupport {
 
  var data  = List[CT]()
  
  /**
   * Add an object to the model
   */
  def add(o: CT) = {
    data = o :: data
    this.@->("added", o)
  } 
  
  /**
   * If the object is already in the list
   */
  def contains(o: CT) : Boolean = data.contains(o)
  
  /**
   * Remove the provided object if necessary
   */
  def remove(o:CT) : Boolean = data.contains(o) match {
    case true => 
      data = data.filterNot(_ == o)
       this.@->("removed", o)
      true
    case false => false
  }
  
  def clear = {
    data.foreach(remove(_))
  }
  
} 

trait ComboBoxModel[CT] extends ListModel[CT] {
  
  var selected : CT = _
  
}

class DefaultListModel[CT] extends ListModel[CT] {
  
}

class DefaultComboBoxModel[CT] extends  ComboBoxModel[CT]  with ListeningSupport {
  
}