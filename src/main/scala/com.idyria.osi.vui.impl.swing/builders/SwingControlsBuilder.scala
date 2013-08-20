/**
 *
 */
package com.idyria.osi.vui.core.swing.builders

import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

import com.idyria.osi.vui.core.components.events._

import com.idyria.osi.vui.core.components.controls.ControlsBuilder
import com.idyria.osi.vui.core.components.controls.VUIButton
import com.idyria.osi.vui.core.components.controls.VUILabel
import com.idyria.osi.vui.core.components.scenegraph.SGNode

import javax.swing._
import javax.swing.JButton
import javax.swing.JLabel

import scala.language.implicitConversions


/**
 * @author rleys
 *
 */
trait SwingControlsBuilder extends ControlsBuilder[Component] {



  /**
   * Implements returning a label
   */
  override def label(text:String) : VUILabel[Component] = {

    return new SwingJComponentCommonDelegate[JLabel](new JLabel(text)) with VUILabel[Component] {

        // Text
        //--------------
        def setText(str: String) = delegate.setText(str)

    }

    /*
    new JLabel(text) with VUILabel[Component] with SGNode[Component] {

      // Node
      //---------

      def base : Component = this
      override def revalidate = super.revalidate

      //---------------------------------------
      // General
      //---------------------------------------
      override def disable = base.setEnabled(false)

      //---------------------------------------
      // Actions
      //---------------------------------------

      override def onMousePressed(action: VUIMouseEvent => Unit) = {

        this.addMouseListener(new MouseAdapter() {

           override def  mousePressed(e : MouseEvent) = {
             action(e)
           }

        })

      }

      override def onDrag(action: VUIMouseEvent => Unit) = {

        	this.addMouseMotionListener(new MouseMotionAdapter() {

        	  override def mouseDragged(ev:MouseEvent) = {
        	    action(ev)
        	  }

        	  /*override def mouseDrag(evt, x, y) = {

        	  }*/

        	})

      }

      override def onClicked(action: => Any) =  this.addMouseListener(new MouseAdapter() {
        override def  mouseClicked(e : MouseEvent) = SwingUtilities.invokeLater(new Runnable {

            override def run() = action
          })
        })



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
    */

  }

  /**
   * Implements returning a button
   */
  def button(text: String) : VUIButton[Component] = {

    // Create Button
    //-------------------
    return new SwingJComponentCommonDelegate[JButton](new JButton(text)) with VUIButton[Component] {

        override def disable = super.disable

        //---------------------------------------
        // Actions
        //---------------------------------------
        /*override def onClicked(action: => Any) = {

          delegate.addActionListener(new ActionListener() {

            def actionPerformed(ev: ActionEvent) = {
              action
            }

          })

        }*/

        //---------------------------------------
        // Positioning
        //---------------------------------------
        //def setPosition(x:Int,y:Int) = {delegate.setLocation(x, y); delegate.setBounds(x,y,delegate.getBounds().width,delegate.getBounds().height)}
        //def getPosition : Pair[Int,Int] = Pair[Int,Int](delegate.getLocation().x,delegate.getLocation().y)

        // Dummy Overrides for JComponent compatibility
        /*override def setX(x:Int) = delegate.setX(x)
        override def getX: Int = getPosition._1
        override def setY(y:Int) = delegate.setY(y)
        override def getY: Int = getPosition._2*/

        
    }

    /*
    return new JButton(text) with VUIButton[Component] {

      //---------------------------------------
      // Node
      //---------------------------------------

      def base : Component = this
      override def revalidate = super.revalidate

      //---------------------------------------
      // General
      //---------------------------------------
      //override def disable 

      //---------------------------------------
      // Positioning
      //---------------------------------------
      def setPosition(x:Int,y:Int) = {super.setLocation(x, y); super.setBounds(x,y,super.getBounds().width,super.getBounds().height)}
      def getPosition : Pair[Int,Int] = Pair[Int,Int](super.getLocation().x,super.getLocation().y)

      // Dummy Overrides for JComponent compatibility
      override def setX(x:Int) = super.setX(x)
      override def getX: Int = getPosition._1
      override def setY(y:Int) = super.setY(y)
      override def getY: Int = getPosition._2

      override def onClicked(action: => Any) = {

        this.addActionListener(new ActionListener() {

          def actionPerformed(ev: ActionEvent) = {
            action
          }

        })

      }

    }
    */
  }


  


}
