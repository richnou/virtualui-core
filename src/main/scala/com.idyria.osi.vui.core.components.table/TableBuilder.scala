package com.idyria.osi.vui.core.components.table

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.VUIBuilder
import scala.reflect.ClassTag

trait TableBuilderInterface[T] {

  def table[CT]: SGTable[CT, T]

}

trait TableBuilder[T] extends TableBuilderInterface[T] {

  def table[CT]: SGTable[CT, T] = VUIBuilder.as[TableBuilderInterface[T]].table

}

trait SGTable[CT, T] extends SGGroup[T] with ApplyTrait {

  type Self = SGTable[CT, T]

  // Configuration
  //-----------------
  var mode = SGTable.DataMode.ROW

  // Columns Specification
  //------------

  var columns = List[SGTableColumn[CT]]()

  def column(name: String)(cl: SGTableColumn[CT] => Any): SGTableColumn[CT] = {

    //-- Create Column
    var c = new SGTableColumn[CT](name)
    cl(c)
    columns = columns :+ c

    // Signal listeners
    this.@->("column.added", c)

    c
  }

  // Datas add
  //----------------

  /**
   * Rows for the ROW mode
   */
  var rows = List[List[Any]]()

  /**
   * Objects for the OBJECT mode
   */
  var objects = List[CT]()

  /**
   * Add a new data object to the table
   */
  def add(data: CT) = {

    this.mode match {
      case SGTable.DataMode.OBJECT =>

        objects = objects :+ data
        
        // Signal listeners
        this.@->("object.added", data)
        
      case SGTable.DataMode.ROW =>

        // Gather Datas from columns
        //------------
        rows = rows :+ columns.map { c => c.contentClosures.map(_(data)) }.flatten

        // Signal listeners
        this.@->("row.added", rows.last)

    }
    
    this

  }
  
  def removeAll = {
    
    rows = List[List[Any]]()
    objects = List[CT]()
    this.@->("removeAll")
    
  }

}

object SGTable {

  /**
   * Row mode: The add functions gathers datas and add them to the model as row data+
   * Object mode: The add functions only adds the provided object to the model
   */
  object DataMode extends Enumeration {
    type DataMode = Value
    val ROW, OBJECT = Value
  }

}

class SGTableColumn[CT](var name: String) extends ApplyTrait {
  type Self = SGTableColumn[CT]

  // Columns Parameters
  //---------------
  var rowSpan = 1
  var colSpan = 1

  // Column contents definition
  // - We store here a list of closures, because a Column definition can be a col span
  //-------------------
  var contentClosures = List[CT => Any]()
  var contentClosuresResultsType = Map[(CT => _),Class[_]]()
  
  // var contentClosure: CT ⇒ Any = { o ⇒ }

  def content[R : ClassTag](cl: CT ⇒ R)(implicit tag : ClassTag[R]) = {
    
    var resCl =  { ct ⇒ cl(ct) }
    this.contentClosures = contentClosures :+ resCl
    this.contentClosuresResultsType = contentClosuresResultsType + (resCl -> tag.runtimeClass)
  }
}
