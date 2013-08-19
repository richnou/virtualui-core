/**
 *
 */
package com.idyria.osi.vui.core.swing.builders

import com.idyria.osi.vui.core.components.containers.ContainerBuilder
import java.awt.Component
import com.idyria.osi.vui.core.components.layout.VuiSwitchLayout
import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.containers.VUITab
import com.idyria.osi.vui.core.components.containers.VUITabPane
import java.awt.Container
import javax.swing.JTabbedPane
import com.idyria.osi.vui.core.components.scenegraph.SGContainerNode
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import javax.swing.JPanel
import com.idyria.osi.vui.core.components.layout.VUILayout
import com.idyria.osi.vui.core.components.containers.VUIPanel
import com.idyria.osi.vui.core.swing.style.SwingStylableTrait

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

      def clear = {
        this.removeAll
        this.revalidate
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



  }

  /**
   * Creates a Tab to be inserted inside a tabpane
   */
  /*def tab : VUITab[Container] = {


    new JTab

  }*/



}
