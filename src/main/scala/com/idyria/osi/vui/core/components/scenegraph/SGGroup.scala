/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

import com.idyria.osi.vui.core.components.layout.VUILayout
import com.idyria.osi.vui.core.styling.ApplyTrait
import scala.collection.mutable.Stack

/**
 * A Group of nodes
 *
 * @author rleys
 *
 */
trait SGGroup[T] extends SGNode[T] {

  //type Self = SGGroup[T]

  //def apply[NT <: SGNode[T]](content: NT, cl: (NT => Unit)): NT = { node(content); cl(content); content }

  var sgChildren = List[SGNode[T]]()

  /**
   * This method add a new node to the current container node
   *
   * To Be Overriden by Implementation to actually get a new Node
   *
   */
  def node[NT <: SGNode[T]](content: NT): NT = {

    // Change parent
    content.setParent(this.asInstanceOf[SGGroup[Any]])
    content.@->("parent.changed")
    content.@->("parent.changed", this)

    sgChildren = sgChildren :+ content

    // Return and call added
    this.@->("child.added", content)

    // Update layout

    content
  }
  
   /**
   * This method cannot be override to make sure its basic implementation can still be reached
   *
   *
   */
  final def addChild[NT <: SGNode[T]](content: NT): NT = {

    // Change parent
    content.setParent(this.asInstanceOf[SGGroup[Any]])
    content.@->("parent.changed")
    content.@->("parent.changed", this)

    sgChildren = sgChildren :+ content

    // Return and call added
    this.@->("child.added", content)

    // Update layout

    content
  }

  /**
   * Alias to node(SGNode[T])
   */
  def <=[NT <: SGNode[T]](n: NT): NT = this.node(n)

  /**
   * Returns the list of children
   */
  def children: List[SGNode[T]] = this.sgChildren

  /**
   * Clear Children components
   */
  def clear: Unit = {
     //super.clear
    var bk = this.sgChildren
    this.sgChildren = this.sgChildren.filter(_ ⇒ false)

    bk.foreach {
      c => this.@->("child.removed", c)
    }
    //this.sgChildren = this.sgChildren diff Seq(n)
   

  }

  def removeChild(n: SGNode[Any]) = {
    this.sgChildren.contains(n) match {
      case true ⇒
        this.sgChildren = this.sgChildren diff Seq(n)
        this.@->("child.removed", n)
      //println(s"Child Removed");
      case false ⇒
        //println("Child not removed")
        /*this.sgChildren.foreach {
          n => println(s"node: $n")

        }*/
    }
  }

  /*def apply[NT <: SGNode[T]](g: SGGroup[T]) = {

    this.node(g)

  }
*/
  /*def apply[NT <: SGNode[T]](cl: SGGroup[T] => Unit) {
    cl(this)
  }*/

  // Tree Processing
  //---------------------------

  /**
   * Executes closure on all Subnodes
   */
  def onSubNodes(cl: SGNode[T] => Unit): Unit = {
    this.onSubNodesMatch {
      case n => cl(n)
    }
  }

  def onSubNodesMatch(f: PartialFunction[SGNode[T], Unit]): Unit = {

    // Stack of nodes to process
    //------------------
    var nodes = Stack[SGNode[T]]()
    this.children.foreach(nodes.push(_))

    while (nodes.isEmpty == false) {

      // Take current
      var current = nodes.pop

      // Execute function
      f(current)

      // Add Children if some
      current match {
        case g: SGGroup[T] => g.children.foreach(nodes.push(_))
        case _ =>
      }

    }

  }

  // Layout and constraints
  //----------------------------

  var layoutImpl: VUILayout[T] = null

  /**
   * Called to apply a layout to the group
   *
   * @event layout.updated(VUIlayout)
   *
   */
  def layout_=(layout: VUILayout[T]): Unit = {
    this.layoutImpl = layout
    this.layoutImpl.setTargetGroup(this)

    this.@->("layout.updated", layout)
  }

  /**
   * Returns the defined layout
   */
  def layout: VUILayout[T] = this.layoutImpl

  // Search 
  //-----------------
  def searchByName(name: String): Option[SGNode[_]] = {

    def search(current: SGNode[T], name: String): Option[SGNode[T]] = {

      //println("Testing for header: " + current.name)

      current match {
        // Found
        case c if (c.name != null && c.name == name) =>

          return Option(current)

        // Not found and is a group -> search
        case g: SGGroup[T] =>

          var res: Option[SGNode[T]] = None
          g.children.find {
            c =>
              search(c, name) match {
                case Some(r) =>
                  res = Option(r); true
                case None => false
              }
          }
          res

        // Not found, and not a group -> 
        case _ => None

      }
    }
    search(this, name)

  }

}
