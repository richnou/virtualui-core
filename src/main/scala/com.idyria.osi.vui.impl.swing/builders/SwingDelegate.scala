package com.idyria.osi.vui.impl.swing.builders

import java.awt._
import java.awt.event._
import javax.swing._


import com.idyria.osi.vui.core.components._
import com.idyria.osi.vui.core.components.events._
import com.idyria.osi.vui.core.components.scenegraph.SGNode

import scala.language.implicitConversions


/**
    Wrapper for a JComponent to be seen as an SGNode
*/
class SwingJComponentCommonDelegate[DT <: JComponent]( val delegate : DT)  extends VUIComponent[Component] {

    //---------------------------------------
    // Node
    //---------------------------------------

    def base : DT = delegate
    override def revalidate = delegate.revalidate

    def clear = {delegate.removeAll;  delegate.revalidate ; delegate.validate}

    //---------------------------------------
    // General
    //---------------------------------------
    override def disable = delegate.setEnabled(false)

    //---------------------------------------
    // Actions
    //---------------------------------------

    override def onMousePressed(action: VUIMouseEvent => Unit) = {

        delegate.addMouseListener(new MouseAdapter() {

           override def  mousePressed(e : MouseEvent) = {
             action(e)
           }

        })

    }

    override def onDrag(action: VUIMouseEvent => Unit) = {

        delegate.addMouseMotionListener(new MouseMotionAdapter() {

          override def mouseDragged(ev:MouseEvent) = {
            action(ev)
          }

          /*override def mouseDrag(evt, x, y) = {

          }*/

        })

    }

    override def onClicked(action: => Any) =  delegate.addMouseListener(new MouseAdapter() {
        override def  mouseClicked(e : MouseEvent) = SwingUtilities.invokeLater(new Runnable {

            override def run() = action
          })
    })

    //----------------------
    //-- Geometry listeners
    override def onShown(action: => Unit) = delegate.addComponentListener(new ComponentAdapter() {

        println("Registering action for ComponentShown")

        override def  componentShown(e : ComponentEvent) = SwingUtilities.invokeLater(new Runnable {

            override def run() = action
          })
        override def  componentResized(e : ComponentEvent) = SwingUtilities.invokeLater(new Runnable {

            override def run() = action
          })
    })


    //---------------------------------------
    // Positioning
    //---------------------------------------
    def setPosition(x:Int,y:Int) = {delegate.setLocation(x, y); delegate.setBounds(x,y,delegate.getBounds().width,delegate.getBounds().height)}
    def getPosition : Pair[Int,Int] = Pair[Int,Int](delegate.getLocation().x,delegate.getLocation().y)

    //----------------------
    // Styling
    //----------------------
    
    def size(width: Int, height: Int) = {
        delegate.setPreferredSize(new Dimension(width,height))
    }

    // Conversions
    //-----------------------

    //-- Convert component events
    def convertMouseEventToVUIDragEvent(ev: MouseEvent) : VUIDragEvent = {
    this.populateVUIMouseEvent[VUIDragEvent](ev, new VUIDragEvent)
    }
    implicit def convertMouseEventToVUIMouseEvent(ev: MouseEvent) : VUIMouseEvent = {
    this.populateVUIMouseEvent[VUIMouseEvent](ev, new VUIMouseEvent)
    }
    private def populateVUIMouseEvent[ET <: VUIMouseEvent](srcEvent:MouseEvent,targetEvent: ET) : ET = {

        // Fill in positions
        targetEvent.actualX = srcEvent.getX()
        targetEvent.actualY = srcEvent.getY()

        targetEvent
    }


    //override def toString = delegate.toString

} 

object SwingJComponentCommonDelegate {
  
	implicit def convertJComponentToSGNode[T <: JComponent](comp: T) : SGNode[Any] = {new SwingJComponentCommonDelegate[T](comp)}

    implicit def convertJComponentToSGNodeNoParam(comp: JComponent) : SGNode[Any] = {new SwingJComponentCommonDelegate[JComponent](comp)}

}

object SwingNodeWrapper {

    def apply[T <: JComponent](comp: T) : SGNode[Any] = {new SwingJComponentCommonDelegate[T](comp)}

}

