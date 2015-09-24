package com.idyria.osi.vui.impl.swing.model

import com.idyria.osi.vui.core.components.controls.TreeModel
import com.idyria.osi.vui.core.components.controls.TreeNode
import javax.swing.event.TreeModelEvent

/**
 * This class is an adapater to convert from VUI TreeModel specification to Swing Implementation
 */
class SwingTreeModelAdapter(var model: TreeModel[_ <: TreeNode])  extends javax.swing.tree.TreeModel {

  
  // Root infos
  //-----------------
 
  
  // Reload and stuff
  //-----------------
  model.onWith("node.reload") {
    e : TreeNode => 
      
      var event = new TreeModelEvent(e,e.pathToParent.asInstanceOf[Array[Object]])
      
      listeners.foreach {
        l => 
          l.treeNodesChanged(event)
           l.treeStructureChanged(event)
      }
      
      
  }
  
  // Listeners
  //---------------------
  
  var listeners = List[javax.swing.event.TreeModelListener]()
  
  def addTreeModelListener(l: javax.swing.event.TreeModelListener): Unit = {
	  listeners = l :: listeners
  }

  def removeTreeModelListener(x$1: javax.swing.event.TreeModelListener): Unit = {

  }
  def valueForPathChanged(x$1: javax.swing.tree.TreePath, x$2: Any): Unit = {

  }
  
  // node informations
  //-----------------
  
  def getChild(parent: Any, index: Int): Object  = parent.asInstanceOf[TreeNode].children(index)
  
  def getChildCount(node: Any): Int				 = node.asInstanceOf[TreeNode].children.size
  
  def getIndexOfChild(parent: Any, child: Any): Int = parent.asInstanceOf[TreeNode].children.indexOf(child)
  
  def getRoot(): Object = model.root
  
  def isLeaf(node: Any): Boolean = {
    node.asInstanceOf[TreeNode].children.size match {
      case s if (s>0) => false
      case _ => true
    }
  }
  
  
  

}
  