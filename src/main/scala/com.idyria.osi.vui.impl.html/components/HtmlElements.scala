package com.idyria.osi.vui.impl.html.components

import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.lib.placeholder.PlaceHolder
import com.idyria.osi.tea.listeners.ListeningSupport
import scala.annotation.tailrec

/**
 * Base Class for an HTML Node
 */
abstract class HTMLNode(var htmlNodeName : String = "undefined") extends SGGroup[Any] {

  // Parameters
  // var name: String = null

  // SGGroup Implementation
  //-------------------

  var childrenSeq = Seq[HTMLNode]()

  def base: Any = this

  /**
   * Clear Children
   */
  def clear: Unit = {
    this.childrenSeq = this.childrenSeq.filter(_ => true)
  }

  /**
   * Remove one child
   */
  override def removeChild(n: SGNode[Any]) = {
    this.children.contains(n) match {
      case true  => this.childrenSeq = this.children diff Seq(n)
      case false =>
    }
  }
  
  /**
   * Returns all children
   */
  def children = this.childrenSeq

  /**
   * Not doing anything
   */
  def revalidate: Unit = {}

  /**
   * Children nodes are saved locally
   */
  override def node[NT <: SGNode[Any]](content: NT): NT = {

    childrenSeq = childrenSeq :+ content.asInstanceOf[HTMLNode]
    super.node(content)
  }

  def :::[NT <: HTMLNode](parent: NT): HTMLNode = {

    parent.node(this)

    this
  }

  /**
   * Placeholder mechanism
   */
  def waitFor(id: String) = {
    
    this.onWith(id) {
      content : Any => 
        
       // println("*** Adding content: "+id+" of type: "+content.getClass)
        
        content match {
        	case node : SGNode[Any] => this <= node
        	case nodes if (nodes.isInstanceOf[Iterable[_]])=> nodes.asInstanceOf[Iterable[_]].foreach( n => this <= n.asInstanceOf[SGNode[Any]])
        	case _ => 
        }
    }
    
    /*this.onWith(id) {
      node: SGNode[Any] => this <= node
    }*/
  }
  
  def place(id: String)(cl: HTMLNode) = {
    this.@->(id,cl)
  }

  // HTML Stuff
  //-----------------

  /**
   * Node name used to produce html
   */
  //var htmlNodeName: String

  var textContent: String = ""

  var attributes = List[(String, String)]()

  // Attributes
  //-----------------
  def apply(attr: (String, String)) = this.attributes = attr :: this.attributes

  // Render
  //----------------
  
  /**
   * FIXME : Optimise this ? 
   */
  final def indentCount(nd:SGNode[Any]) : Int= {
    if (nd.parent==null)
      0
    else {
       1 + indentCount(nd.parent)
     }
  }
  
  /**
   * Renders HTML Node structure as String
   */
  override def toString: String = {

    // Prepare attributes
    //-------------------------
    var attrs = attributes.map { t => s"""${t._1}="${t._2}"""" }.mkString(" ", " ", "")
    		
    var indentString = for (i <- 0 to this.indentCount(this)) yield "    "
    
    s"""
${indentString.mkString}<$htmlNodeName$attrs>
${indentString.mkString}    ${textContent}
${indentString.mkString}${this.children.map(_.toString).mkString("\n\n")}
${indentString.mkString}</$htmlNodeName>
    """
  }

}

// Generic
//--------------------
class GenericHTMLElement(nodeName: String, textC: String = "") extends HTMLNode(nodeName) {

  this.textContent = textC
}

// Common Stuff
//------------------
class HTMLTextNode(var content: String) extends HTMLNode("") {


  /*def base: Any = this
  def revalidate: Unit = {

  }*/
 

  // Render
  //----------------

  /**
   * Renders HTML Node structure as String
   */
  override def toString: String = content

}

// Main
//-------------

/**
 * Automatically gets a head and body
 */
class Html extends HTMLNode("html") {



}

class Body extends HTMLNode("body") {



}

class Head extends HTMLNode("head") {


}

// Containers
//---------------

class Div extends HTMLNode("div") with ListeningSupport {


}

class Span extends HTMLNode("span") {


}

// Titles
//------------
class Htitle(var level: Int, text: String) extends HTMLNode(s"h$level") {

  this.textContent = text

}

class H1(text: String = "") extends Htitle(1, text)
class H2(text: String = "") extends Htitle(2, text)
class H3(text: String = "") extends Htitle(3, text)
class H4(text: String = "") extends Htitle(4, text)
class H5(text: String = "") extends Htitle(5, text)
class H6(text: String = "") extends Htitle(6, text)

// Linking / Actions
//------------

class A(var text: String, var destination: String) extends HTMLNode("a") {

  this("href" -> destination)
  this.textContent = text
}


// List
//-----------------
class Ul extends HTMLNode("ul")
class Ol extends HTMLNode("ol")
class Li extends HTMLNode("li")


// Form
//------------
class Form extends HTMLNode("form")

abstract class FormInput(lname:String) extends HTMLNode("input") {
  this.setName(lname)
  this("name"->lname)
}

/**
 * <input type="hidden" name="name" value="value"
 */
class FormParameter(p: (String,String)) extends FormInput(p._1) {
  this("type"->"hidden")
  this("value"->p._2)
}

	/**
	 * input type text
	 */
	class InputText(lname:String) extends FormInput(lname) {
	  
	  this("type"->"text")
	  
	}

	/**
	 * input type password
	 */
	class InputPassword(lname:String) extends FormInput(lname) {
	  
	  this("type"->"password")
	  
	}
	
	class FormSubmit(value: String) extends HTMLNode("input") {
	  this("type"->"submit")
	  this("value"->value)
	  
	  
	}



