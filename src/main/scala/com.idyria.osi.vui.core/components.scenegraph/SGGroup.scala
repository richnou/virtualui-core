/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

import com.idyria.osi.vui.core.components.layout.VUILayout

/**
 * A Group of nodes
 *
 * @author rleys
 *
 */
trait SGGroup[T] extends SGNode[T] {

  def apply[NT <: SGNode[T]](content: NT, cl: (NT => Unit)): NT = { node(content); cl(content); content }

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
    content.@->("parent.changed",this)
    
    // Return and call added
    this.@->("child.added",content)
    
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
  def children : Seq[SGNode[T]]
  
  /**
   * Clear Children components
   */
  def clear
  
  /**
   * Remove a child from current group
   */
  def removeChild(c: SGNode[T]) = {
    
  }
  

  def apply[NT <: SGNode[T]](g: SGGroup[T]) = {

    this.node(g)

  }

  def apply[NT <: SGNode[T]](cl: SGGroup[T] => Unit) {
    cl(this)
  }

  // Layout and constraints
  //----------------------------

  var layoutImpl : VUILayout[T] = null
  
  /**
   * Called to apply a layout to the group
   * 
   * @event layout.updated(VUIlayout)
   * 
   */
  def layout_=(layout: VUILayout[T]) : Unit = {
    this.layoutImpl = layout
    this.layoutImpl.setTargetGroup(this)
    
    this.@->("layout.updated", layout)
  }

  /**
   * Returns the defined layout
   */
  def layout : VUILayout[T] = this.layoutImpl

  // Search 
  //-----------------
  def searchByName(name:String) : Option[SGNode[_]] = {
    
    def search(current: SGNode[T], name: String): Option[SGNode[T]] = {

      //println("Testing for header: " + current.name)

      current match {
        // Found
        case c if (c.name != null && c.name == name) =>

          return Option(current)

        // Not found and is a group -> search
        case g : SGGroup[T] =>

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
    search(this,name)
    
    
    
  }
  
  
}
