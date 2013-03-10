/**
 *
 */
package org.losthold.vui.swing.builders

import org.losthold.vui.components.containers.ContainerBuilder
import java.awt.Component
import org.losthold.vui.components.layout.VuiSwitchLayout
import org.losthold.vui.components.scenegraph.SGGroup
import org.losthold.vui.components.containers.VUITab
import org.losthold.vui.components.containers.VUITabPane
import java.awt.Container
import javax.swing.JTabbedPane
import org.losthold.vui.components.scenegraph.SGContainerNode
import org.losthold.vui.components.scenegraph.SGNode
import javax.swing.JPanel
import org.losthold.vui.components.layout.VUILayout
import org.losthold.vui.components.containers.VUIPanel
import org.losthold.vui.swing.style.SwingStylableTrait

/**
 * @author rleys
 *
 */
trait SwingContainerBuilder extends ContainerBuilder[Component] {

  
  /**
   * Creates a tabpane component to store nodes into panes
   */
  def tabpane : VUITabPane[Component] = {
    
    new JTabbedPane() with VUITabPane[Component] {
      
      def base : Container = this
      

      
      // Node
      //------------
     
      /**
       * Node method override to add components as tabs
       */
      def node[NT <: SGNode[Component]](title:String)(content:NT) : NT = {
        
        this.addTab(title,content.base)
        content
      }
      
      // Positioning
      //---------------------  
      def setPosition(x:Int,y:Int) = super.setLocation(x, y)
      def getPosition : Pair[Int,Int] = Pair[Int,Int](super.getLocation().x,super.getLocation().y) 
  
      // Dummy Overrides for JComponent compatibility
      override def setX(x:Int) = super.setX(x)
      override def getX: Int = getPosition._1
      override def setY(y:Int) = super.setY(y)
      override def getY: Int = getPosition._2
      
    }
    
    
  }
  
  def panel : VUIPanel[Component] = {
    
    new JPanel() with VUIPanel[Component] with SwingStylableTrait {

      //-----------------------------
      // Scene Graph
           
      var definedLayout : VUILayout[Component] = null
      def base() = this

      /**
       * Add The node to the jpanel
       */
      def node[NT <: SGNode[Component]](nd: NT): NT = {

        // Add to group
        this.add(nd.base)
        
        // If we have a layout, notify it
        if (definedLayout!=null) {
          definedLayout nodeAdded nd
        }
        
        nd

      }

      def layout(l: VUILayout[Component]) = {
        
    	this.definedLayout = l
        l.setTargetGroup(this)

      } 

      //-----------------------------
      // Positioning
      def setPosition(x:Int,y:Int) = super.setLocation(x, y)
      def getPosition : Pair[Int,Int] = Pair[Int,Int](super.getLocation().x,super.getLocation().y) 
  
      // Dummy Overrides for JComponent compatibility
      override def setX(x:Int) = super.setX(x)
      override def getX: Int = getPosition._1
      override def setY(y:Int) = super.setY(y)
      override def getY: Int = getPosition._2
      
    }
    
    
    
  }
  
  /**
   * Creates a Tab to be inserted inside a tabpane
   */
  /*def tab : VUITab[Container] = {
    
    
    new JTab
    
  }*/
  
	
  
}