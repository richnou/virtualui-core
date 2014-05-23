package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.VUIBuilder
import scala.reflect.ClassTag


trait TreeBuilderInterface[T] extends TreeModelBuilder {

  def tree: VUITree[T]
}

trait TreeBuilder[T] extends TreeBuilderInterface[T] {

  
  def tree : VUITree[T] = VUIBuilder.as[TreeBuilderInterface[T]].tree

}

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
   
  // Node Representation
  //------------------------
  var nodesViews = Map[Class[_],(AnyRef  =>  Any)]()
  /**
   * Records a mapping function for a specific node data type
   */
  def nodeView[T:ClassTag](cl: T => Any)(implicit tag: ClassTag[T]) = {
    
    var r = tag.runtimeClass
    nodesViews = nodesViews + (r -> { o => cl(o.asInstanceOf[T])})
    
  }
  
  
  var modelImpl : TreeModel = new DefaultTreeModel
  modelImpl.root = new DefaultTreeNode {
    this.show = false
  }
  
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
  
  def setModel(m: TreeModel) = modelImpl=m
  
  def getModel : TreeModel = modelImpl
 
  // Selection
  //---------------
  def getSelection : Iterable[TreeNode]
  
}

/**
 * Interface for the Nodes of a VUITree to provide special functions
 */
trait VUITreeItem {
  
  var nodeModel : TreeNode = null
  
}

trait TreeModel extends TreeNode with ListeningSupport {
  
  /**
   * A tree model must have a node root
   */
  var root : TreeNode  = null
  
  
}


trait TreeNode extends ListeningSupport {
  
  /**
   * Should we show this node ? 
   */
  var show  = true
  
  var children =  List[TreeNode]()
  
  var parentNode : TreeNode = null
  
  /**
   * Add a child
   * 
   * @return The added child for chaining purpose
   */
  def <=(child: TreeNode) : TreeNode = {
    this.children = child :: this.children
    child.parentNode = this
    this.@->("node.added", child)
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

class DefaultTreeModel extends TreeModel {
  

  
}

class DefaultTreeNode extends TreeNode {
  
 
  
}

class DataTreeNode[T](var data : T) extends TreeNode with ApplyTrait {
 
  type Self = DataTreeNode[T]
  
  override def toString = data.toString
  
}

/**
 * A trait containing utility methods to build a tree model
 */
trait TreeModelBuilder {
  
  def treeNode[T](data:T) : DataTreeNode[T] = new DataTreeNode(data)
  
}

