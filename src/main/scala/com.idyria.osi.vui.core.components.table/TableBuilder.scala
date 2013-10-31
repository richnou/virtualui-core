package com.idyria.osi.vui.core.components.table

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.VUIBuilder

trait TableBuilderInterface[T] {

  def table[CT]: SGTable[CT, T]

}

trait TableBuilder extends TableBuilderInterface[Any] {

  def table[CT]: SGTable[CT, Any] = VUIBuilder.as[TableBuilderInterface[Any]].table

}

trait SGTable[CT, T] extends SGGroup[T] with ApplyTrait {

  type Self = SGTable[CT, T]

  
  
  // Columns Specification
  //------------
  
  var columns = List[SGTableColumn[CT]]()
  
  def column(name: String): SGTableColumn[CT] = {

    //-- Create Column
    var c = new SGTableColumn[CT](name)

    columns = columns :+ c
    
    // Signal listeners
    this.@->("column.added", c)
    
    c
  }

  // Datas add
  //----------------
  
  var rows = List[List[Any]]()
  
  /**
   * Add a new data object to the table
   */
  def add(data: CT)= {
    
    // Gather Datas from columns
    //------------
    rows = rows :+ columns.map { c => c.contentClosure(data)}
    
    // Signal listeners
    this.@->("row.added", rows.last)
    
  }
  
  
}

class SGTableColumn[CT](var name: String) extends ApplyTrait {
  type Self = SGTableColumn[CT]

  // Column content definition
  //-------------------

  var contentClosure: CT ⇒ Any = { o ⇒ }

  def content(cl: CT ⇒ Any) = {
    this.contentClosure = { ct ⇒ cl(ct) }
  }
}
