package com.idyria.osi.vui.impl.swing.model

import com.idyria.osi.vui.core.components.controls.TreeModel
import com.idyria.osi.vui.core.components.controls.TreeNode

/**
 * This class is an adapater to convert from VUI TreeModel specification to Swing Implementation
 */
class SwingTreeModelAdapter(var model: TreeModel)  extends javax.swing.tree.TreeModel {

  
  // Root infos
  //-----------------
  
  
  // Listeners
  //---------------------
  def addTreeModelListener(x$1: javax.swing.event.TreeModelListener): Unit = {

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
/*
class SwingTreeNode(var node: TreeNode) extends javax.swing.tree.TreeNode {

  // Local Infos
  //------------------------

  // Children Infos
  //--------------------



  def children(): java.util.Enumeration = node.
  def getAllowsChildren(): Boolean = {
    0
  }
  def getChildAt(x$1: Int): javax.swing.tree.TreeNode = {
    0
  }
  def getChildCount(): Int = {
    0
  }
  def getIndex(x$1: javax.swing.tree.TreeNode): Int = {
    0
  }
  def getParent(): javax.swing.tree.TreeNode = {
    0
  }
  def isLeaf(): Boolean = {
    0
  }

}*/