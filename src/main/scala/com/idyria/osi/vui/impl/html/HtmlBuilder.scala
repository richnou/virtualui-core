package com.idyria.osi.vui.impl.html

import scala.language.implicitConversions
import scala.language.dynamics

import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.table.SGTable
import com.idyria.osi.vui.impl.html.components._
import com.idyria.osi.vui.impl.html.components.HTMLNode
import com.idyria.osi.vui.impl.html.js.JScript
import scala.reflect.ClassTag

/**
 * Builder for main html elements
 */
trait HtmlTreeBuilder extends TreeBuilder[HTMLNode[_ <: org.w3c.dom.Node]] with TableBuilder with Dynamic with HtmlFactory {

  /*def applyDynamic(name:String)(cl: => Any) = {
    switchToNode(new HTMLGen, cl)
  }
  */

  def createNode(name: String): SGNode[Any] = {

    name match {

      case "html" => new Html
      case "head" => new Head
      case "body" => new Body

    }

  }

  // Dynamic handles non defined elements
  //--------------------
  def applyDynamic(name: String)(cl: => Any): HTMLNode[_] = {
    //println(s"---- applyDynamic DYNAMIC $name----")
    // Create Generic
    var generic = new GenericHTMLElement(name)

    // IF first argument is a string, add as text content

    switchToNode(generic, cl)
  }

  def updateDynamic(name: String): HTMLNode[_] = {
    new GenericHTMLElement(name)
  }

  def updateDynamic(name: String)(value: String) = {
    //println("---- UPDATE DYNAMIC----")
    attribute(name -> value)
  }
  def selectDynamic(name: String): HTMLNode[_] = {
   // println("---- Select DYNAMIC----")
    var generic = new GenericHTMLElement(name)
    generic
  }

  // Generic Stuff like attributes
  //-----------------

  def dummyNode(cl: => Any) = {
    switchToNode(new DummyNode, cl)
  }

  /**
   * Generic node
   */
  def element(name: String)(cl: => Any) = {
    switchToNode(new GenericHTMLElement(name), cl)
  }

  /**
   * Set an attribute
   */
  def attribute(attr: (String, String)): Unit = this.currentNode(attr)

  def attr(attrs: (String, String)*): Unit = {
    attrs.foreach(this.currentNode(_))
  }

  /**
   * Set the id of an element
   */
  def id(id: String) = this.attribute("id" -> id)

  /**
   * Return a text node
   */
  def text(str: String) = switchToNode(new HTMLTextNode(str), {})

  def html(cl: => Any): Html = switchToNode(new Html, cl)

  // Search
  //------------
  def $(id: String)(cl: => Any) {
    currentNode.children.find {
      case n if (n.name.toString == id) => true
      case n: HTMLNode[_] => n.attributes.getOrElse("id", "") == id
      case _ => false
    } match {
      case Some(node: HTMLNode[_]) => switchToNode(node, cl)
      case _ => currentNode.children.foreach {
        case n: HTMLNode[_] => switchToNode(n, { $(id) { cl } })
        case _ =>
      }
    }
  }
  
  def reRender(id:String) = {
    $(id) {
      rebuild
    }
  }

  // Head
  //--------------------

  //-- Base
  //---------------

  def head(cl: => Any): Head = {

    currentNode.children.find { n => n.isInstanceOf[Head] } match {
      case None => switchToNode(new Head, cl)
      case Some(h: Head) =>
        onNode(h) {
          cl
        }
        h

      // Won't happen
      case _ => throw new RuntimeException("Impossible")

    }

  }

  def link(cl: => Any) = switchToNode(new GenericHTMLElement("link"), cl)

  def title(str: String) = switchToNode(new GenericHTMLElement("title"), { currentNode.textContent = s"$str" })

  def meta(cl: => Any) = switchToNode(new Meta, cl)

  //-- Extras
  //--------------------

  def stylesheet(path: String) = {

    link {
      attribute("rel" -> "stylesheet")
      attribute("href" -> path)
    }
  }

  // Styling
  //-----------
  def classes(cl: String*) = {
    attribute("class" -> ("" + currentNode.attributes.getOrElse("class", "") + cl.mkString(" ", " ", " ")))
  }
  def addClasses(cl: String*) = {
    attribute("class" -> ("" + currentNode.attributes.getOrElse("class", "") + cl.mkString(" ", " ", " ")))
  }

  def addStyle(styles: (String, String)*) = {
    attribute("style" -> ("" + currentNode.attributes.getOrElse("class", "") + styles.map { case (k, v) => s"$k=$v" }.mkString(";", ";", "")))
  }

  // Body 
  //----------------
  def body(cl: => Any) = switchToNode(new Body, cl)

  def div(cl: => Any) = switchToNode(createDiv, cl)
  def div = switchToNode(createDiv, {})
  
  def div(txt: String)(cl: => Any) = {
    switchToNode(createDiv, {
     text(txt)
      cl
    })
  }
  
  def textDiv(str:String) = switchToNode(new Div, {text(str)})
  
  
  def span(cl: => Any) = switchToNode(new Span, cl)
  def span(str:String)(cl: => Any) = switchToNode(new Span, {text(str);cl})

  def pre(cl: => Any) = switchToNode(new Pre, cl)

  def p(cl: => Any) = switchToNode(new Paragraph, cl)

  implicit def stringToSpan(str: String): Span = {
    span {
      text(str)
    }
  }

  def h1(t: String) = switchToNode(new H1(t), {})
  def h2(t: String) = switchToNode(new H2(t), {})
  def h3(t: String) = switchToNode(new H3(t), {})
  def h4(t: String) = switchToNode(new H4(t), {})
  def h5(t: String) = switchToNode(new H5(t), {})
  def h6(t: String) = switchToNode(new H6(t), {})

  //--- Linking / Actions
  //--------------
  def a(name: String, dest: String)(cl: => Any) = {
    
    var aelt = createA
    aelt.textContent = name
    aelt.setDestination(dest)
    switchToNode(aelt, cl)
    
  }

  // Lists
  //--------------------

  def ul(cl: => Any) = switchToNode(new Ul, cl)
  def ol(cl: => Any) = switchToNode(new Ol, cl)
  def li(cl: => Any) = switchToNode(new Li, cl)
  //def li(s:String) =  switchToNode(new Li,{currentNode.textContent=s})

  // Button
  //----------------
  //def button(text: String)(cl: Button => Any) = switchToNode(new Button, { currentNode.textContent = text; cl(currentNode.asInstanceOf[Button]) })
  //def button(text: String, cl: => Any) = switchToNode(new Button, { currentNode.textContent = text; cl })

  def button(text: String)(cl: => Any) = switchToNode(new Button, { currentNode.textContent = text; cl })

  // Form
  //----------------
  def form(cl: => Any) = switchToNode(new Form, cl)

  def inputText(name: String)(cl: => Any) = switchToNode(createInputText(name), cl)
  def inputPassword(name: String)(cl: => Any) = switchToNode(new InputPassword(name), cl)
  def inputCheckBox(name: String)(cl: => Any) = switchToNode(new InputCheckBox(name), cl)
  def inputCheckBox(name: String, value: String)(cl: => Any) = switchToNode(new InputCheckBox(name), { cl; attr("value" -> value) })

  def inputRadioBox(name: String, value: String)(cl: => Any) = switchToNode(new InputRadioBox(name), { cl; attr("value" -> value); })

  def textArea(name: String)(cl: => Any): Textarea = switchToNode(new Textarea(name), cl)

  def select(name: String)(cl: => Any) = switchToNode(new Select(name), cl)
  def option(name: String, value: String)(cl: => Any) = switchToNode(new SelectOption(name, value), cl)

  def formSubmit(text: String)(cl: => Any) = switchToNode(new FormSubmit(text), cl)
  def formParameter(p: (String, String))(cl: => Any) = switchToNode(new FormParameter(p), cl)

  def submit(text: String)(cl: => Any) = formSubmit(text)(cl)
  def parameter(p: (String, String))(cl: => Any) = formParameter(p)(cl)

  def label(target: String, txt: String)(cl: => Any): Label = switchToNode(new Label, { attribute("for" -> target); text(txt); cl })

  def label(cl: => Any): Label = switchToNode(new Label, { cl })

  // Table
  //---------
  override def table[OT](implicit tag : ClassTag[OT]): SGTable[OT, org.w3c.dom.html.HTMLElement] = switchToNode(super.table[OT].asInstanceOf[HTMLNode[org.w3c.dom.html.HTMLElement]], {}).asInstanceOf[SGTable[OT, org.w3c.dom.html.HTMLElement]]

  def thead(cl: => Any) = switchToNode(new GenericHTMLElement("thead"), cl)
  def tbody(cl: => Any) = switchToNode(new GenericHTMLElement("thead"), cl)
  def tr(cl: => Any) = switchToNode(new GenericHTMLElement("tr"), cl)
  def td(cl: => Any) = switchToNode(new GenericHTMLElement("td"), cl)
  def th(cl: => Any) = switchToNode(new GenericHTMLElement("th"), cl)

  // Scripting
  //------------------
  def script(cl: => Any) = switchToNode(new Script, cl)
  

  def script(t:String)(txt:String) = {
    
     switchToNode(new Script, {attr("type"->t);text(txt)})
   
  }
    
  def javaScript(path: String) = switchToNode(new Script, { attribute("src" -> path) })
  def jscript(s: String) = new JScript(s)

  // Attributes
  //-------------------
  //ssdef @placeHolder

  // Navigation
  //-----------------------
  def nav(cl: => Any) = {
    switchToNode(new Nav, cl)
  }
  
  
  // DOM Events
  //------------------
  def onKeyTyped(cl: Char => Any) = {
    currentNode.onKeyTyped {
      c => cl(c)
    }
  }
  
  def onChanged(cl: => Any) = {
    currentNode.onChanged {
      cl
    }
  }

}

object HtmlTreeBuilder {

}

class TestBuilder extends HtmlTreeBuilder {

}
