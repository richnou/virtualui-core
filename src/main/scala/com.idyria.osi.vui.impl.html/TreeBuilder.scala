package com.idyria.osi.vui.impl.html

import scala.language.dynamics
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import scala.collection.immutable.Stack


trait TreeBuilder extends Dynamic {
  
  var nodesStack = Stack[SGGroup[Any]]()
  
  
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
    
  }
  
  
  def createNode(name:String): SGNode[Any] 
  
}