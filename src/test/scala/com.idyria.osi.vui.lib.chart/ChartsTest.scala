package com.idyria.osi.vui.lib.chart

import org.scalatest.FunSuite
import com.idyria.osi.ooxoo.core.buffers.structural.io.sax.StAXIOBuffer
import com.idyria.osi.ooxoo.core.buffers.structural.AnyXList



class ChartsTest extends FunSuite with DatasetsBuilder {
  
  
  test("Data Set serialize/deserialize") {
    
    // Create DS
    //-----------------------
    
    var ds = timeDataset[Long]("test")
    
    Thread.sleep(100)
    ds <= 0
    
    Thread.sleep(100)
    ds <= 1
    
    Thread.sleep(100)
    ds <= 2
    
    // Serialize
    //--------------------
    var res = StAXIOBuffer(ds,true)
    println("Size: "+ds.values.size)
    println("Result: "+res)
    
    
    // Stream back in using AnyXlist to make sure the correct Dataset type is read back
    //-------------------
    
    var any = AnyXList()
    any.appendBuffer(StAXIOBuffer(res))
    any.lastBuffer.streamIn
    
    // Test Type
    //---------------
    expectResult(true)(any.head.isInstanceOf[TimeValuesDataset[_]])
    
    // Test Values types
    //--------------------
    
    
    
  }
  
}