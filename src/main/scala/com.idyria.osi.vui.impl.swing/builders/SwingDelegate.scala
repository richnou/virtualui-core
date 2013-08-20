package com.idyria.osi.vui.core.swing.builders

import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

import javax.swing.JComponent
import javax.swing.SwingUtilities

import com.idyria.osi.vui.core.components._
import com.idyria.osi.vui.core.components.events._
import com.idyria.osi.vui.core.components.scenegraph.SGNode

import scala.language.implicitConversions



class SwingJComponentCommonDelegate[DT <: JComponent]( val delegate : DT)  extends VUIComponent[Component] {

    //---------------------------------------
    // Node
    //---------------------------------------

    def base : DT = delegate
    override def revalidate = delegate.revalidate

    def clear = {delegate.removeAll;  delegate.revalidate}

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



    //---------------------------------------
    // Positioning
    //---------------------------------------
    def setPosition(x:Int,y:Int) = {delegate.setLocation(x, y); delegate.setBounds(x,y,delegate.getBounds().width,delegate.getBounds().height)}
    def getPosition : Pair[Int,Int] = Pair[Int,Int](delegate.getLocation().x,delegate.getLocation().y)


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


}


