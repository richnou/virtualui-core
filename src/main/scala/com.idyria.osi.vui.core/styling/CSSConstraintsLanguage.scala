package com.idyria.osi.vui.core.styling

import com.idyria.osi.vui.core.components.layout.LayoutConstraint


trait CSSConstraintsLanguage {
  
  
  def cssClass(cl:String) = LayoutConstraint("css.class" -> cl)
  
  
  //-- Properties
  //----------------------
  def backgroundColor(color:String) = LayoutConstraint("css.background-color" -> color)
  def backgroundColor(rgb:(Int,Int,Int)) = LayoutConstraint("css.background-color" -> s"($rgb._1,$rgb._2,$rgb._3)")
  
}