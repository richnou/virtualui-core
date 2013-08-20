/**
 *
 */
package com.idyria.osi.vui.core.swing.builders


import java.awt.Component
import javax.swing.BoxLayout
import java.awt.Container
import java.awt.GridBagLayout
import java.awt.GridBagConstraints

import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.components.scenegraph.SGNode

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

  def grid : VUIGridLayout[Component] = {

    return new VUIGridLayout[Component] {

      var layout = new GridBagLayout

      def setTargetGroup(group : SGGroup[Component]) = {

        group.base.asInstanceOf[Container].setLayout(layout)
        
        group.revalidate
        
      }

      // No need to do anything
      def nodeAdded(node : SGNode[Component]) = {

      }

      override def applyConstraints(node: SGNode[Component],constraints:LayoutConstraints) = {

        var cstr = new GridBagConstraints

        var row = constraints.get("row").asInstanceOf[Integer]
        var column = constraints.get("column").asInstanceOf[Integer]

        try {
            var expand = constraints.get("expand")
            cstr.weightx = 1
            cstr.weighty = 1

        } catch {
          case e: Throwable =>



        }

        println(s"Applying GridBagLayout Constraints $row:$column")

        
        cstr.gridy  = row
        cstr.gridx = column

        this.layout.setConstraints(node.base, cstr)

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
