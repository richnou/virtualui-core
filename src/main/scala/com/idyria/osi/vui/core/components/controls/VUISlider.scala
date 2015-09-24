package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.VUIBuilder


trait SliderBuilderInterface[T]  {

  def slider: VUISlider[T]
}

trait SliderBuilder[T] extends SliderBuilderInterface[T] {

  
  def slider : VUISlider[T] = VUIBuilder.as[SliderBuilderInterface[T]].slider

}

/**
 * Common Trait for Sliders
 * 
 * 
 */
 
trait VUISlider[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

  type Self = VUISlider[_]
  
  // Parameters for a slider
  //---------------
  def setMin(min:Double)
  def setMax(max:Double)
  
  def setShowTickLabel(show:Boolean)
  def setShowTickMarks(show:Boolean)
  def setMajorTickUnit(u:Double)
  def setMinorTickCount(u:Int)
  def setBlockIncrement(u:Double)
  
  // Value
  //-----------
  def setValue(v:Double) 
  def onChange(cl: (Double => Unit))
  

  
 }