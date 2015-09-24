package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.VUIBuilder


trait ProgressBarBuilderInterface[T]  {

  def progressBar: VUIProgressBar[T]
}

trait ProgressBarBuilder[T] extends ProgressBarBuilderInterface[T] {

  
  def progressBar : VUIProgressBar[T] = VUIBuilder.as[ProgressBarBuilderInterface[T]].progressBar

}

/**
 * Common Trait for ProgressBars
 * 
 * 
 */
 
trait VUIProgressBar[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

  type Self = VUIProgressBar[_]
  
  // Parameters for a ProgressBar
  //---------------
  def setProgress(p:Double)
  def onChange(cl: (Double => Unit))
  
  
  
 }