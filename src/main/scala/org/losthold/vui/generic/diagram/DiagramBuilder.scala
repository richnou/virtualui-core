/**
 *
 */
package org.losthold.vui.generic.diagram

import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.VUIComponent

/**
 * @author rleys
 *
 */
trait DiagramBuilder {

  
  class DiagramNodeManager(var node: VUIComponent[Any]) {
    
    
    // On Mouse Pressed: Note the position
    var basePosition = Pair[Int,Int](-1,-1)
    node.onMousePressed {
      ev =>
        println("Saving position of click")
       /* basePosition._1 = ev.actualX
        basePosition._2 = ev.actualY*/
    }
    
    // On Mouse drag, move by the difference
    node.onDrag {
      ev =>
        
    }
  }
  
  /**
   * Configure a Component to be put in a diagram like group
   * That means adding the appropriate listeners to be able to move the components and so on
   */
  
  def diagramNode[NT <: VUIComponent[Any]](nd : NT) :NT = {
    
    // On Mouse Pressed: Note the position
    var basePosition = new Pair[Int,Int](-1,-1) // base position in container
    nd.onMousePressed {
      ev =>
        
        // Save base position of click in component, this is the offset to keep the mouse corretly relative to the moved object
        basePosition = (ev.actualX,ev.actualY)
    }
    
    // On Mouse drag, move by the difference of previous
    nd.onDrag {
      ev =>
        
        // Position is the difference between offset in component click and actual drag offset
        // This gives out the offset to the original click position, which is the delta to move the object
        var delta = Pair[Int,Int]((ev.actualX-basePosition._1),(ev.actualY-basePosition._2))
        nd deltaX delta._1
        nd deltaY delta._2
 
    }
    
    
    nd
  }
  
  
}