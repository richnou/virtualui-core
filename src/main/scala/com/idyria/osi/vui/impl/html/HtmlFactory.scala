package com.idyria.osi.vui.impl.html

import com.idyria.osi.vui.impl.html.components.A
import com.idyria.osi.vui.impl.html.components.InputText
import com.idyria.osi.vui.impl.html.components.Div

trait HtmlFactory {
  
  
  def createA : A = new A("#","#")
  def createInputText(name:String)  = new InputText(name)
  def createDiv = new Div
}