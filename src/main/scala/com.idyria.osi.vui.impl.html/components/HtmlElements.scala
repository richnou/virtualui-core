package com.idyria.osi.vui.impl.html.components

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SGGroup

/**
 * Base Class for an HTML Node
 */
abstract class HTMLNode extends SGGroup[Any] {

  // Parameters
  var name: String = null

  // SGGroup Implementation
  //-------------------
  
  var children = Seq[SGNode[Any]]()
  
  def base: Any = this

  /**
   * Not doing anything
   */
  def revalidate: Unit = {}

  def setName(str: String): Unit = this.name = name

  def clear: Unit = {

  }
  def layout : com.idyria.osi.vui.core.components.layout.VUILayout[Any] = {
    null
  }

  def layout(layout: com.idyria.osi.vui.core.components.layout.VUILayout[Any]): Unit = {

  }
  
  /**
   * Children nodes are saved locally
   */
  override def node[NT <: SGNode[Any]](content: NT) : NT = {
  
   children = children :+  content
   super.node(content)
  }
  
  
  // HTML Stuff
  //-----------------
 
  
  /**
   * Node name used to produce html
   */
  var htmlNodeName : String
  
  /**
   * Renders HTML Node structure as String
   */
  override def toString : String = {
    
    s"""
    <$htmlNodeName>
    	${this.children.map(_.toString).mkString("\n\n")}
    </$htmlNodeName>
    """
  }
  
}

// Main
//-------------

/**
 * Automatically gets a head and body 
 */
class HTML extends HTMLNode {
  
  var htmlNodeName = "html"
  
    
    
}

class Body extends HTMLNode {
  
  var htmlNodeName = "body"
  
}

class Head extends HTMLNode {
  
  var htmlNodeName = "head"
  
}


// Containers
//---------------

class Div extends HTMLNode {
  
  var htmlNodeName = "div"
  
}

class Span extends HTMLNode {
  
  var htmlNodeName = "div"
  
}

// Titles
//------------
class Htitle(var level:Int) extends HTMLNode {
  
  var htmlNodeName = s"h$level"
  
}

class H1 extends Htitle(1)
class H2 extends Htitle(2)
class H3 extends Htitle(3)
class H4 extends Htitle(4)
class H5 extends Htitle(5)
class H6 extends Htitle(6)


// Content
//-----------------




