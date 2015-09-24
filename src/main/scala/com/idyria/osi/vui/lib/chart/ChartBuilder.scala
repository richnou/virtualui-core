package com.idyria.osi.vui.lib.chart

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.styling.ApplyTrait


class Axis {
  
  var name : String = ""
  
}
trait Chart[T,DT <: Dataset]  extends VUIComponent[T] with ApplyTrait {
  
  var datasets = List[DT]()
  
  /**
   * Stores the dataset internaly and calls an event
   */
  def addDataSet(ds:DT) = {
    datasets = datasets :+ ds
    this.@->("dataset.added", ds)
  }
  
  def removeDatasets = {
    
    var dss = datasets
    datasets = List[DT]()
    this.@->("datasets.clean")
    /*datasets.foreach {
      ds => 
        this.@->("dataset.removed", ds)
    }*/
  }
  
}

trait XYChart[T] extends Chart[T,XYDataset[_,_]] {
 
  
  var xAxis = new Axis
  
  var yAxis = new Axis
  

}

/**
 * Just a Line chart
 */
trait LineChart[T] extends XYChart[T] {
  
  type Self = LineChart[T]
  
  
}

trait CustomChart[T,DT <: Dataset] extends Chart[T,DT] {
	type Self = CustomChart[T,DT]
	
	
	def extraValue(cl:(Number,Number) => Option[Any])
	
}
  

trait ChartBuilderInterface[T] {
  
  def lineChart : LineChart[T]
  
  
  def customChart[DT <: Dataset] : CustomChart[T,DT]  = {
    throw new RuntimeException("Not implemented")
  }
}


trait ChartBuilder[T] extends ChartBuilderInterface[T] {
  
  def lineChart : LineChart[T] = VUIBuilder.as[ChartBuilderInterface[T]].lineChart
  
  override def customChart[DT <: Dataset] : CustomChart[T,DT] = VUIBuilder.as[ChartBuilderInterface[T]].customChart[DT]
  
}