package com.idyria.osi.vui.core.components.table

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.VUIBuilder
import scala.reflect.ClassTag
import com.idyria.osi.tea.listeners.ListeningSupport

trait TableBuilderInterface[T] {

  def table[CT](implicit tag : ClassTag[CT]): SGTable[CT, T]
  //def column[CT](name:String) : SGTableColumn[CT]
}

trait TableBuilder[T] extends TableBuilderInterface[T] {

  def table[CT](implicit tag : ClassTag[CT]): SGTable[CT, T] = VUIBuilder.as[TableBuilderInterface[T]].table

  //def column[CT](name:String) : SGTableColumn[CT] = VUIBuilder.as[TableBuilderInterface[T]].column(name)
  
}

trait SGTable[CT, T] extends SGGroup[T] with ApplyTrait with TableBuilder[T] {

  type Self = SGTable[CT, T]

  // Configuration
  //-----------------
  var mode = SGTable.DataMode.ROW

  // Columns Specification
  //------------

  var columns = List[SGTableColumn[CT]]()

  def createColumn(name:String) : SGTableColumn[CT]
  
  def column(name: String)(cl: SGTableColumn[CT] => Any): SGTableColumn[CT] = {

    //-- Create Column
    var c = createColumn(name)
    //var c = new SGTableColumn[CT](name)
    cl(c)
    columns = columns :+ c

    // Signal listeners
    this.@->("column.added", c)

    c
  }
  
  // Edit 
  def setEditable(v:Boolean)

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
  
  // Listeners
  //----------
  
  /**
   * Called when an object is added to the table
   */
  def onObjectAdded(cl: CT => Unit)(implicit tag : ClassTag[CT]) = {
    this.onWith("object.added") {
      obj : CT => cl(obj)
    }
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

class SGTableColumn[CT](var name: String) extends ApplyTrait with ListeningSupport {
  type Self = SGTableColumn[CT]

  // Columns Parameters
  //---------------
  var rowSpan = 1
  var colSpan = 1

  // Edit
  //-------------------
  def setEditable(v:Boolean) = {
    this.@->("editable", v)
  }
  def onEditDone(cl:(CT,String,String) => Any) : Unit = {
    throw new NotImplementedError
  }
  
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
  
  // Last Row
  //---------------
  var lastRowClosures = List[List[CT] => Any]()
  var lastRowClosuresResultsType = Map[(List[CT] => _),Class[_]]()
  def lastRow[R : ClassTag](cl: List[CT] ⇒ R)(implicit tag : ClassTag[R]) = {
    
    var resCl =  { ct ⇒ cl(ct) }
    this.lastRowClosures = lastRowClosures :+ resCl
    this.lastRowClosuresResultsType = lastRowClosuresResultsType + (resCl -> tag.runtimeClass)
  }
}
