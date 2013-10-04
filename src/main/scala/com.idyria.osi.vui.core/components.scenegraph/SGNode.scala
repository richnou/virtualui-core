/**
 *
 */
package com.idyria.osi.vui.core.components.scenegraph

import com.idyria.osi.tea.listeners.ListeningSupport
import com.idyria.osi.vui.core.styling.StylableTrait
import com.idyria.osi.vui.core.constraints.Constrainable

/**
 *
 * Base Node for all Scene Graph Nodes
 *
 * @author rleys
 *
 */
trait SGNode[+T] extends ListeningSupport with Constrainable  {

   var parent : SGGroup[_] = null
  
    /**
        Optional ID for the Node
    */
    var id : String = ""

    def setName(str: String)
	
   
    def base : T

    /**
    * This method is a high level call to ask the underlying implementation
    * to make sure the node has been redrawn
    */
    def revalidate

    //def apply[NT <: SGNode[T]](cl : (NT => Unit))

   /**
   * When constraints of element are updated, call on layout manager of container to update constraints if needed
   */
  this.on("constraints.updated") {
    
    this.parent match {
      case null => 
      case p if (p.layout!=null) => p.asInstanceOf[SGGroup[T]].layout.applyConstraints(this, this.fixedConstraints)
      case _ =>
    }
    
  }
    
}
