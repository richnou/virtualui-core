package com.idyria.osi.vui.core.components.layout

import scala.language.implicitConversions
import com.idyria.osi.vui.core.constraints.Constraint

/**
 * Implementation of constraint for LayoutConstraints...if needed
 */
class LayoutConstraint( name : String) extends Constraint(name) {
  
}
object LayoutConstraint {
  
  def apply(value : (String,Any)) : LayoutConstraint = {
    var res = new LayoutConstraint(value._1)
    res.value = value._2
    res
  }
}
