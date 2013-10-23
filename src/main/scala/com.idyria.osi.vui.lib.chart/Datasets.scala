package com.idyria.osi.vui.lib.chart

import com.idyria.osi.ooxoo.core.buffers.datatypes.XSDStringBuffer
import com.idyria.osi.ooxoo.core.buffers.datatypes.LongBuffer
import com.idyria.osi.ooxoo.core.buffers.structural.xattribute
import com.idyria.osi.ooxoo.core.buffers.structural.ElementBuffer
import com.idyria.osi.ooxoo.core.buffers.structural.xelement
import com.idyria.osi.ooxoo.core.buffers.datatypes.MapBuffer
import com.idyria.osi.ooxoo.core.buffers.structural.DataUnit
import com.idyria.osi.ooxoo.core.buffers.structural.XList
import com.idyria.osi.ooxoo.core.buffers.structural.xcontent
import com.idyria.osi.ooxoo.core.buffers.structural.Buffer
import scala.reflect.runtime.universe._
import scala.reflect._
import com.idyria.osi.ooxoo.core.buffers.structural.AnyXList
import com.idyria.osi.tea.listeners.ListeningSupport
import scala.reflect.ClassTag
import com.idyria.osi.ooxoo.core.buffers.datatypes.ClassBuffer


/*
This file contains some default Data sets models for the chart API
*/

/**
 * This trait contains some common definitions for datasets
 */
trait Dataset extends ElementBuffer with ListeningSupport {

  var name: XSDStringBuffer = "unnamed"

}

@xelement(name = "Value")
class ValueTuple[X, Y] extends ElementBuffer {

  var value: Tuple2[X, Y] = null

  @xattribute
  var x: XSDStringBuffer = null

  @xcontent
  var y: Buffer = null

  // Streamout
  //------------

  /**
   * X is turned to a string using toString method
   * Y is added as a String if it not a buffer
   */
  override def streamOut(du: DataUnit) = {

    x = value._1.toString
    y = value._2 match {
      case b: Buffer ⇒ b
      case v         ⇒ new XSDStringBuffer(v.toString)
    }

    super.streamOut(du)

  }

}

/**
 * XY Datasets contain Value pairs
 */
@xelement()
class XYDataset[X : ClassTag , Y : ClassTag] extends Dataset {

  //var valuesMap = Map[X,Y]()

  // X/Y types
  //---------------
  
  type XT = X
  type YT = Y
  
  @xattribute
  var xType: ClassBuffer[X] = classTag[X]
 

  @xattribute
  var yType: ClassBuffer[Y] =  classTag[Y]

  @xelement
  var values = XList { new ValueTuple[X,Y] }

  // Data input
  //----------------
  def <=(x: X, y: Y): Unit = {

    // Create Tuple
    var vt = new ValueTuple[X, Y]
    vt.value = (x -> y)

    // Add
    this.values += vt
    
    this.@->("value.added",vt)
  }
  
  def getXType(implicit xtag: ClassTag[X]) = xtag
  
 // def getType[T : TypeTag]

  // Streamout
  //-----------------------------
  override def streamOut(du: DataUnit) = {

    // Define x/y types
    //-----------------
    //getXType.runtimeClass
    //getXType.runtimeClass
   // scala.reflect.api.
    //var xt :  scala.reflect.api.TypeTag[X] = typeTag[X]
    
    //xt.tpnme.tpe
    
   
	//xType = typeOf[X]
	
    // Normal Streamout
    //---------
    super.streamOut(du)

  }

}

/*class UntypedXYDataset extends XYDataset[Any,Any] {
  
}*/

/**
 * This Dataset stores some values for which the key (or X value) will be the timestamp at which they were sampled
 *
 * Some utility methods will help transforming the datas for convenience (like define a time = 0) etc...
 *
 */
@xelement
class TimeValuesDataset[Y : ClassTag] extends XYDataset[Long, Y] {

  //AnyXList.register[TimeValuesDataset[_]]
  
   AnyXList.register {
    new TimeValuesDataset[Y]
  }
  
  // Time Manangement
  //---------------------

  
  //-- Default time 0 is the creation time of this dataset
  @xattribute
  var startTime: LongBuffer = System.currentTimeMillis()

  /**
   * Reset start time to current time
   */
  def resetStartTime = startTime = System.currentTimeMillis()

  /**
   * This method changes all the x axis times to become relative to startTime
   */
  def relativeToStartTime : Unit = {
    
   /* this.values.foreach {
      value => 
        
        value.value = ( value.value._1 -  startTime -> value.value._2)
        
    }
    */
  }
  
  // Value Add
  //----------------
  def <=(value: Y): Unit = {

    this <= (System.currentTimeMillis() - startTime, value)

  }

}

