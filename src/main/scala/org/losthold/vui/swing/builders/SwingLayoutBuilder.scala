/**
 *
 */
package org.losthold.vui.swing.builders

import org.losthold.vui.components.layout.LayoutBuilder
import org.losthold.vui.components.layout.VUIVBoxLayout
import java.awt.Component
import javax.swing.BoxLayout
import org.losthold.vui.components.layout.VUIVBoxLayout
import org.losthold.vui.components.scenegraph.SGGroup
import java.awt.Container
import org.losthold.vui.components.layout.VUIHBoxLayout
import org.losthold.vui.components.layout.VUIFreeLayout
import org.losthold.vui.components.scenegraph.SGNode

/**
 * @author rleys
 *
 */
trait SwingLayoutBuilder extends LayoutBuilder[Component] {

  /**
   * Returns a VBox Layout that is created and added to component when the target group is passed in
   */
  def vbox : VUIVBoxLayout[Component] = {
    
    return new VUIVBoxLayout[Component] {
      
      def setTargetGroup(group : SGGroup[Component]) = {
        
        var bl = new BoxLayout(group.base.asInstanceOf[Container],BoxLayout.Y_AXIS)
        group.base.asInstanceOf[Container].setLayout(bl)
        group.base.asInstanceOf[Container].revalidate()
        //this.layoutContainer(group.base.asInstanceOf[Container])
        
      }
      
      // No need to do anything
      def nodeAdded(node : SGNode[Component]) = {
      }
      
    }
 
  }
  
  def hbox : VUIHBoxLayout[Component] = {
    
    return new VUIHBoxLayout[Component] {
      
      def setTargetGroup(group : SGGroup[Component]) = {
        
        var bl = new BoxLayout(group.base.asInstanceOf[Container],BoxLayout.X_AXIS)
        group.base.asInstanceOf[Container].setLayout(bl)
        group.base.asInstanceOf[Container].revalidate()
      }
      
      // No need to do anything
      def nodeAdded(node : SGNode[Component]) = {
      }
      
    }
 
  }
  
  /**
   * Return a layout where elements can be freely placed
   */
  def none : VUIFreeLayout[Component] =  {
    
    return new VUIFreeLayout[Component] {
      
      def setTargetGroup(group : SGGroup[Component]) = {
        
        
        group.base.asInstanceOf[Container].setLayout(null)
         group.base.asInstanceOf[Container].revalidate()
        
      }
      
      // When elements are added: set their bounds property to their width and position
      def nodeAdded(node : SGNode[Component]) = {
        
        println(s"Setting bounds to: "+node.base.getPreferredSize())
        
        node.base.setBounds(node.base.getX(), node.base.getX(), node.base.getPreferredSize.width, node.base.getPreferredSize.height)
        
      }
      
    }
    
  }
  
  
  
}