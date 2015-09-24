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
import com.idyria.osi.vui.core.constraints.Constraints
import com.idyria.osi.vui.core.constraints.Constrainable
import java.awt.Insets
import javax.swing.JComponent

/**
 * @author rleys
 *
 */
trait SwingLayoutBuilder extends LayoutBuilder[Component] {

  /**
   * Returns a VBox Layout that is created and added to component when the target group is passed in
   */
  def vbox: VUIVBoxLayout[Component] = {

    return new VUIVBoxLayout[Component] {

      /**
       * Set Layout on group
       */
      override def setTargetGroup(group: SGGroup[Component]) = {

        var bl = new BoxLayout(group.base.asInstanceOf[Container], BoxLayout.Y_AXIS)
        group.base.asInstanceOf[Container].setLayout(bl)
        group.base.asInstanceOf[Container].revalidate()
        //this.layoutContainer(group.base.asInstanceOf[Container])

        super.setTargetGroup(group)

      }

    }

  }

  def hbox: VUIHBoxLayout[Component] = {

    return new VUIHBoxLayout[Component] {

      override def setTargetGroup(group: SGGroup[Component]) = {

        var bl = new BoxLayout(group.base.asInstanceOf[Container], BoxLayout.X_AXIS)
        group.base.asInstanceOf[Container].setLayout(bl)
        group.base.asInstanceOf[Container].revalidate()

        super.setTargetGroup(group)
      }

    }

  }

  /**
   * Implementation of the Grid Special Layout using GridBagLayout from Swing
   */
  def grid: VUIGridLayout[Component] = {

    return new VUIGridLayout[Component] {

      var layout = new GridBagLayout

      override def setTargetGroup(group: SGGroup[Component]) = {

        group.base.asInstanceOf[Container].setLayout(layout)

        super.setTargetGroup(group)

      }

      /**
       * Update constraints of a given node
       */
      override def applyConstraints(node: SGNode[Component], inputConstraints: Constraints) = {

        // Resolve Constraints
        //---------
        var constraints = inputConstraints
        node match {
          case constrainable: Constrainable => constraints = inputConstraints + constrainable.fixedConstraints
          case _                            =>
        }

        // Prepare constraints
        var gridbagConstraints = this.layout.getConstraints(node.base) match {
          case actual if (actual == null) => new GridBagConstraints
          case actual                     => actual
        }

        // Row / Column
        //---------------------
        constraints.getOption("row") match {
          case Some(row) => gridbagConstraints.gridy = row.asInstanceOf[Int]
          case _         =>
        }

        constraints.getOption("column") match {
          case Some(column) => gridbagConstraints.gridx = column.asInstanceOf[Int]
          case _            =>
        }

        //println(s"Component is at: y:${gridbagConstraints.gridy}, x: ${gridbagConstraints.gridx}")
        //println(s"Applying GridBagLayout Constraints $row:$column")

        // Anchor
        //--------------------------
        constraints.getOption("align") match {

          case Some("right")  => gridbagConstraints.anchor = GridBagConstraints.EAST
          case Some("left")   => gridbagConstraints.anchor = GridBagConstraints.WEST
          case Some("top")    => gridbagConstraints.anchor = GridBagConstraints.NORTH
          case Some("bottom") => gridbagConstraints.anchor = GridBagConstraints.SOUTH
          case Some("center") => gridbagConstraints.anchor = GridBagConstraints.CENTER
          case _              =>
        }

        // Column Spanning
        //-------------------------
        constraints.getOption("colspan") match {
          case Some(colSpan) =>

            //  println(s"---------> Colspan $colSpan")

            gridbagConstraints.gridwidth = colSpan.asInstanceOf[Int]

          case None =>
        }

        constraints.getOption("rowspan") match {
          case Some(rowSpan) =>

            //println(s"---------> rowspan $rowSpan")

            gridbagConstraints.gridheight = rowSpan.asInstanceOf[Int]

          case None =>
        }

        // Expand & push
        //------------------

        constraints.getOption("expandHeight") match {
          case Some(colSpan) =>
            gridbagConstraints.weighty = 1.0
            gridbagConstraints.fill = GridBagConstraints.VERTICAL

          case None =>
        }

        constraints.getOption("expandWidth") match {
          case Some(colSpan) =>

            //println("------> Expand Width")

            gridbagConstraints.weightx = 1.0
            gridbagConstraints.fill = GridBagConstraints.HORIZONTAL
          //gridbagConstraints.gridwidth = 1

          case None =>

          //gridbagConstraints.gridwidth = GridBagConstraints.RELATIVE
        }

        constraints.getOption("expand") match {
          case Some(colSpan) =>

            gridbagConstraints.weightx = 1.0
            gridbagConstraints.weighty = 1.0
            gridbagConstraints.fill = GridBagConstraints.BOTH

            println(s"Doing Expand on ${node.base.asInstanceOf[JComponent].getName()}")

          case None =>
        }

        constraints.getOption("pushRight") match {
          case Some(pushRight) =>

            gridbagConstraints.weightx = 1
            gridbagConstraints.anchor = GridBagConstraints.EAST

          //println(s"------> pushRight on ${node.toString}#${node.base.hashCode}")

          case None =>
        }

        // Spread -> Means the grid width is remainder to take all the available columns
        //--------------------
        constraints.getOption("spread") match {

          case Some(spread) =>

            gridbagConstraints.gridwidth = GridBagConstraints.REMAINDER
          //gridbagConstraints.gridheight = GridBagConstraints.REMAINDER
          // println("------> Spread")

          case None =>

        }

        // Add Default Insets
        //------------------------
        gridbagConstraints.insets = new Insets(5, 5, 5, 5)

        node.base match {
          case e: JComponent => e.setToolTipText(s"name: ${e.getName()}, row: ${gridbagConstraints.gridy} , column: ${gridbagConstraints.gridx}, weight:(${gridbagConstraints.weightx},${gridbagConstraints.weighty})")
          case _             =>
        }

        // Apply
        //-----------------------
        this.layout.setConstraints(node.base, gridbagConstraints)
        //this.layout.invalidateLayout(node.base.getParent)
      }

    }

  }

  /**
   * Return a layout where elements can be freely placed
   */
  def none: VUIFreeLayout[Component] = {

    return new VUIFreeLayout[Component] {

      override def setTargetGroup(group: SGGroup[Component]) = {

        group.base.asInstanceOf[Container].setLayout(null)

        super.setTargetGroup(group)

      }

      // When elements are added: set their bounds property to their width and position
      //---------
      this.onWith("node.added") {
        node: SGNode[Component] =>

          //println(s"Setting bounds to: "+node.base.getPreferredSize())

          node.base.setBounds(node.base.getX(), node.base.getX(), node.base.getPreferredSize.width, node.base.getPreferredSize.height)

      }

    }

  }

}
