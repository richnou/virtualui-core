package com.idyria.osi.vui.core.styling
 

/**
 * This trait contains common type info definition
 */
trait CommonVUIElementTrait {
  
  type Self <: CommonVUIElementTrait
  
}

/**
 * This trait defines the apply functions needed to customize a component at creation time
 */
trait ApplyTrait extends  CommonVUIElementTrait {
  
  //type Self <: ApplyTrait
  
  def apply(cl: Self => Unit) : Self = {
    cl(this.asInstanceOf[Self])
    this.asInstanceOf[Self]
  }
}