package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.VUIBuilder


trait SpinnerBuilderInterface[T]  {

  def spinner: VUISpinner[T]
}

trait SpinnerBuilder[T] extends SpinnerBuilderInterface[T] {

  
  def spinner : VUISpinner[T] = VUIBuilder.as[SpinnerBuilderInterface[T]].spinner

}

/**
 * Common Trait for Spinners
 * 
 * 
 */
 
trait VUISpinner[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

  type Self = VUISpinner[_]
  
  // Parameters for a Spinner
  //---------------
  def setMin(min:Double)
  def setMax(max:Double)
  def setStep(step:Double)
  
  // Value
  //-----------
  def setValue(v:Double) 
  def getValue : Double
  def onChange(cl: (Double => Unit))
  
  // Editable
  //------------------
  def setEditable(v:Boolean)

  
 }