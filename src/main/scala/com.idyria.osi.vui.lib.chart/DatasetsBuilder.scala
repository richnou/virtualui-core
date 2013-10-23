package com.idyria.osi.vui.lib.chart

import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

/**
 * This trait is to be mixed in anywhere to get access to factory methods that create some default datasets
 */
trait DatasetsBuilder {
  
  
  def timeDataset[T : ClassTag](name: String) : TimeValuesDataset[T] =  {
    
    var ds = new TimeValuesDataset[T]
    ds.name = name
    
    ds
    
  }
  
}

