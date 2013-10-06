package com.idyria.osi.vui.impl.html

import com.idyria.osi.vui.impl.html.components.HTMLNode
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.impl.html.components._

/**
 * Builder for main html elements
 */
trait HtmlTreeBuilder extends TreeBuilder {
  
  
  def createNode(name : String) : SGNode[Any] = {
    
    name match {
      
      case "html" => new HTML
      case "head" => new Head
      case "body" => new Body
      
      
    }
    
  }
  
}