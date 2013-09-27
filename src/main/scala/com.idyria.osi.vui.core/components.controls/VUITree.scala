package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.styling.ApplyTrait


/**
 * Common trait for Tree component
 */
trait VUITree[T] extends VUIComponent[T] with StylableTrait with ApplyTrait  {

  type Self = VUITree[_]
  
  
  def setModel(m: TreeModel)
 
}

trait TreeModel {
  
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
  
  /**
   * Add a child
   * 
   * @return The added child for chaining purpose
   */
  def <=(child: TreeNode) : TreeNode = {
    this.children = child :: this.children
    child
  }
 
  
}

trait DefaultTreeModel extends TreeModel {
  
  var root : TreeNode = null
  
}


trait DefaultTreeNode extends TreeNode {
  
  var children : List[TreeNode] = List[TreeNode]()
  
}

