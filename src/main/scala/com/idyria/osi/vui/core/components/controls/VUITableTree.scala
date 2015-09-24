package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.components.VUIComponent
import com.idyria.osi.vui.core.components.table.SGTable
import com.idyria.osi.vui.core.styling.ApplyTrait
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.VUIBuilder


trait TableTreeBuilderInterface[T] {

  def tableTree[CT <: TreeNode](tree: VUITree[CT,T]): VUITableTree[CT,T]
}

trait TableTreeBuilder[T] extends TableTreeBuilderInterface[T] {

  
  def tableTree[CT <: TreeNode](tree: VUITree[CT,T]) : VUITableTree[CT,T] = {
    var tableTree = VUIBuilder.as[TableTreeBuilderInterface[T]].tableTree[CT](tree)
    tableTree.tree = tree
    tableTree
  }

}

trait VUITableTree[CT <: TreeNode,T] extends SGTable[CT,T] with StylableTrait with ApplyTrait  {
  
  //override type Self = VUITableTree[CT,T]
  
  // Tree
  //--------------
  
  var _tree : VUITree[CT,T] = null
  
  def tree_=(t : VUITree[CT,T]) = {
    this._tree = t
    this.@->("tree.set",t)
  }
  def tree = _tree
  
  // Root Handle
  //---------------
  def setShowRootHandle(v:Boolean) = {
    
  }
  
  
}