package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.tea.listeners.ListeningSupport


/**
 * Common trait for Tree component
 * 
 * Events: 
 * 
 * node.doubleclick(node)
 * 
 */
trait VUITree[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

  type Self = VUITree[_]
  
  
   // Listeners for clicks and selection
   //-----------------------------------------
  this.onDoubleClick {
    
    //-- Get selected 
    getSelection.headOption match {
      
      case Some(node) => 
        	this.getModel.@->("node.doubleclicked", node);
        	node.@->("doubleclicked")
      case _ => 
    }
    
  }
  
  // Model
  //----------------
  
  def setModel(m: TreeModel)
  
  def getModel : TreeModel
 
  // Selection
  //---------------
  def getSelection : Iterable[TreeNode]
  
}

trait TreeModel extends ListeningSupport {
  
  /**
   * A tree model must have a node root
   */
  var root : TreeNode 
  
  
}


trait TreeNode extends ListeningSupport {
  
  /**
   * Should we show this node ? 
   */
  var show  = true
  
  var children : List[TreeNode]
  
  var parentNode : TreeNode = null
  
  /**
   * Add a child
   * 
   * @return The added child for chaining purpose
   */
  def <=(child: TreeNode) : TreeNode = {
    this.children = child :: this.children
    child.parentNode = this
    child
  }
  
  /**
   * Return the list from top to this nodes parent
   */
  def pathToParent : Array[TreeNode] = {
    
    var res = List[TreeNode]()
    
    //-- Gather from bottom to top
    var current = this.parentNode
    while (current!=null) {
      res = current :: res
      current = current.parentNode
    }
    
    //-- Reverse and return
    res.reverse.toArray

    
  }
 
  
}

trait DefaultTreeModel extends TreeModel {
  
  var root : TreeNode = null
  
}


trait DefaultTreeNode extends TreeNode {
  
  var children : List[TreeNode] = List[TreeNode]()
  
}

