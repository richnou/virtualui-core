package com.idyria.osi.vui.core.components.table

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.VUIBuilder



trait TableBuilderInterface[T] {
  
  def table[CT]: SGTable[CT,T]
  
}


trait TableBuilder extends TableBuilderInterface[Any] {
  
  def table[CT]: SGTable[CT,Any] = VUIBuilder.as[TableBuilderInterface[Any]].table
  
}


trait SGTable[CT,T] extends SGGroup[T] with ApplyTrait {
  
  // Columns Specification
  //------------
  def column(name: String) : SGTableColumn = {
    
    //-- Create Column
    var c = new SGTableColumn(name)
    
    
    c
  }
  
}

class SGTableColumn(var name : String) extends ApplyTrait {
  type Self = SGTableColumn
  
  // Column content definition
  
}
