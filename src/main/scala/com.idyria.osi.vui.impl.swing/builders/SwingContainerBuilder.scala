/**
 *
 */
package com.idyria.osi.vui.impl.swing.builders

import java.awt.Component
import com.idyria.osi.vui.core.components.containers.ContainerBuilder
import com.idyria.osi.vui.core.components.containers.VUIPanel
import com.idyria.osi.vui.core.components.containers.VUITabPane
import com.idyria.osi.vui.core.components.layout.VUILayout
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.scenegraph.SceneGraphBuilder
import javax.swing.JPanel
import javax.swing.JTabbedPane
import javax.swing.JComponent
/**
 * @author rleys
 *
 */
trait SwingContainerBuilder extends ContainerBuilder[Component] with SceneGraphBuilder[Component] {


  /**
   * Creates a tabpane component to store nodes into panes
   */
  def tabpane : VUITabPane[Component] = {

    return new SwingJComponentCommonDelegate[JTabbedPane](new JTabbedPane) with VUITabPane[Component]  {

       override def clear = {
         super.clear
       }
      
        /**
         * Node method override to add components as tabs
         */
        def node[NT <: SGNode[Component]](title:String)(content:NT) : NT = {
        	super.node(content)
          delegate.addTab(title,content.base)
          content
        }

       
        

    }

   

  }

  /**
   * Return group as JPanel:
   *
   */
  def group(): SGGroup[Component] = panel

  /**
    Build panel
  */
  def panel : VUIPanel[Component] = {

      return new SwingJComponentCommonDelegate[JPanel](new JPanel) with VUIPanel[Component] {

        //-----------------------------
        // Scene Graph

        //var definedLayout : VUILayout[Component] = null
  
        
       override def clear = {
         super.clear
       }
        
        /**
         * Node Add
         */
        this.onWith("child.added") {
          nd : SGNode[Component] => 
         
            delegate.add(nd.base)
            revalidate
            
        }
        

        /**
         * Add The node to the jpanel
         */
        /*override def node[NT <: SGNode[Component]](nd: NT): NT = {

          super.node(nd)
          
          // Add to group
          delegate.add(nd.base)

          // If we have a layout, notify it
          if (definedLayout!=null) {
            definedLayout nodeAdded nd
          }

          // Revalidate
          nd.revalidate
          this.revalidate
          this.delegate.validate
          this.delegate.invalidate
          
          nd

        }
*/
        /*def clear = {
          delegate.removeAll
          delegate.revalidate
        }*/

        /*def layout(l: VUILayout[Component]) = {

           this.definedLayout = l
           l.setTargetGroup(this)

        }

        def layout : VUILayout[Component] = this.definedLayout*/

      }

    
/*
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

      def clear = {
        this.removeAll
        this.revalidate
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

*/

  }

  /**
   * Creates a Tab to be inserted inside a tabpane
   */
  /*def tab : VUITab[Container] = {


    new JTab

  }*/



}
