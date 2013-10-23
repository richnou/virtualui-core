package com.idyria.osi.vui.lib.chart

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.VUIBuilder
import com.idyria.osi.vui.core.styling.ApplyTrait


class Axis {
  
  var name : String = ""
  
}

trait XYChart[T] extends VUIComponent[T] with ApplyTrait {
  
  var xAxis = new Axis
  
  var yAxis = new Axis
  
  var datasets = List[XYDataset[_,_]]()
  
  /**
   * Stores the dataset internaly and calls an event
   */
  def addDataSet(ds:XYDataset[_,_]) = {
    datasets = datasets :+ ds
    this.@->("dataset.added", ds)
  }
  
}

/**
 * Just a Line chart
 */
trait LineChart[T] extends XYChart[T] {
  
  type Self = LineChart[T]
  
  
}


trait ChartBuilderInterface[T] {
  
  def lineChart : LineChart[T]
}


trait ChartBuilder extends ChartBuilderInterface[Any] {
  
  def lineChart : LineChart[Any] = VUIBuilder.as[ChartBuilderInterface[Any]].lineChart
  
  
}