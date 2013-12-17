package com.idyria.osi.vui.impl.html

import scala.language.dynamics
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import scala.collection.immutable.Stack

trait TreeBuilder[BT <: SGNode[Any]] extends Dynamic {

  var nodesStack = Stack[SGGroup[Any]]()

  var currentNode : BT = _
  
  def createNode(name: String): SGNode[Any]

  /**
   * Add an already build node to current 
   */
  def add [NT <: BT](node: NT) : NT = switchToNode(node,{})
  
  def switchToNode[NT <: BT](node: NT, cl: => Any): NT = {

    // Try to create node based on name
    //-------
    
    currentNode = node
    
    //var node = createNode(name)
    node match {

      // If group, add  - stack - execute - destack
      case n: SGGroup[Any] =>

        nodesStack.headOption match {
          case Some(head) => head <= n
          case _          =>
        }

        nodesStack = nodesStack.push(n)

        cl

        nodesStack = nodesStack.pop
        nodesStack.headOption match {
          case Some(head) => currentNode = head.asInstanceOf[BT]
          case _          =>
        }
        
      // If node, add - execute
      case n: SGNode[_] =>

        nodesStack.headOption match {
          case Some(head) => head <= n
          case _          =>
        }

        cl

    }

    // Return
    return node

  }
  


  /*
  def applyDynamic(name:String)(cl: => Any) : SGNode[Any] = {
    
    // Try to create node based on name
    //-------
    var node = createNode(name)
    node match {
      
      case null => throw new RuntimeException(s"Tree builder cannot build node: $name, no implementation provided ")
      
       // If group, add  - stack - execute - destack
      case n : SGGroup[Any] => 
        
        nodesStack.headOption match {
          case Some(head) => head <= n
          case _ =>
        }
        
        nodesStack = nodesStack.push(n)
        
        cl
        
        nodesStack = nodesStack.pop
        
      
      // If node, add - execute
      case n : SGNode[_] => 
        
        nodesStack.headOption match {
          case Some(head) => head <= n
          case _ =>
        }
        
        cl
        
    }
    
    // Return
    return node
    
  }*/
  
  

}