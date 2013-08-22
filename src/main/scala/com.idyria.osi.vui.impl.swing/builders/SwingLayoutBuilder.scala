/**
 *
 */
package com.idyria.osi.vui.impl.swing.builders


import java.awt.Component
import javax.swing.BoxLayout
import java.awt.Container
import java.awt.GridBagLayout
import java.awt.GridBagConstraints

import com.idyria.osi.vui.core.components.scenegraph.SGGroup
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.components.scenegraph.SGNode

import com.idyria.osi.vui.core.styling._

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

      override def applyConstraints(node: SGNode[Component],inputConstraints:LayoutConstraints) = {

        // Resolve Constraints
        //---------
        var constraints = inputConstraints
        if (node.isInstanceOf[StylableTrait] && node.asInstanceOf[StylableTrait].fixedConstraints!=null) {
          constraints = inputConstraints + node.asInstanceOf[StylableTrait].fixedConstraints
        }

        // Prepare constraints
        var gridbagConstraints = new GridBagConstraints

        // Row / Column
        var row = constraints.get("row").asInstanceOf[Integer]
        var column = constraints.get("column").asInstanceOf[Integer]

        //println(s"Applying GridBagLayout Constraints $row:$column")

        // Anchor
        try {
          
          constraints.get("align") match {
            case "right" => 
                
                //println(s"---------> Align right")
                
                gridbagConstraints.anchor = GridBagConstraints.EAST
          }

        } catch {
          case e: Throwable =>
        }

        // Column Spanning
        constraints.getOption("colspan") match {
          case Some(colSpan) => 
            
            //  println(s"---------> Colspan $colSpan")
            
              gridbagConstraints.gridheight = colSpan.asInstanceOf[Int]
          
          case None => 
        }

        constraints.getOption("rowspan") match {
          case Some(rowSpan) => 
            
            println(s"---------> rowspan $rowSpan")
            
              gridbagConstraints.gridwidth = rowSpan.asInstanceOf[Int]
          
          case None => 
        }

        // Expand
        //------------------

        constraints.getOption("expand") match {
          case Some(colSpan) => 
              gridbagConstraints.weightx = 1
              gridbagConstraints.weighty = 1
              gridbagConstraints.fill = GridBagConstraints.BOTH
          
          case None => 
        }

        constraints.getOption("expandHeight") match {
          case Some(colSpan) => 
              gridbagConstraints.weighty = 1
              gridbagConstraints.fill = GridBagConstraints.BOTH
          
          case None => 
        }

        constraints.getOption("expandWidth") match {
          case Some(colSpan) => 

              println("------> Expand Width")

              gridbagConstraints.weightx = 1
              gridbagConstraints.fill = GridBagConstraints.BOTH
          
          case None => 
        }
        

        

        // Apply
        //-----------------------
        gridbagConstraints.gridy  = row
        gridbagConstraints.gridx = column
        this.layout.setConstraints(node.base, gridbagConstraints)

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
