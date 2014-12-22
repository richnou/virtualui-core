package com.idyria.osi.vui.impl.swing.builders

import java.awt._
import java.awt.event._
import javax.swing.event._
import javax.swing._
import com.idyria.osi.vui.core.components._
import com.idyria.osi.vui.core.components.events._
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import scala.language.implicitConversions
import com.idyria.osi.vui.impl.swing.style.SwingComponentStyleSupport

trait SwingComponentDelegate {

  def base: JComponent

}

/**
 * Wrapper for a JComponent to be seen as an SGNode
 */
class SwingJComponentCommonDelegate[DT <: JComponent](val delegate: DT) extends VUIComponent[Component] with SwingComponentStyleSupport {

  //---------------------------------------
  // Node
  //---------------------------------------

  override def setName(str: String) = {
    //super.setName(str)
    delegate.setName(str)
  }

  def base: DT = delegate
  override def revalidate = delegate.revalidate

  def clear = { delegate.removeAll; delegate.revalidate; delegate.validate }

  /**
   *
   */
  /*def removeChild(c: SGNode[Component]) = {

  }*/

  /**
   * Do not return anything
   */
  /*def children: Seq[SGNode[Component]] = {
    Nil
  }*/

  //---------------------------------------
  // General
  //---------------------------------------
  override def disable = delegate.setEnabled(false)
  override def enable = delegate.setEnabled(true)

  //---------------------------------------
  // Actions
  //---------------------------------------

  override def onMousePressed(action: VUIMouseEvent => Unit) = {

    delegate.addMouseListener(new MouseAdapter() {

      override def mousePressed(e: MouseEvent) = {
        action(populateVUIMouseEvent(e, new VUIMouseEvent))
      }

    })

  }

  override def onDrag(action: VUIMouseEvent => Unit) = {

    delegate.addMouseMotionListener(new MouseMotionAdapter() {

      override def mouseDragged(ev: MouseEvent) = {
        action(populateVUIMouseEvent(ev, new VUIMouseEvent))
      }

      /*override def mouseDrag(evt, x, y) = {

          }*/

    })

  }

  override def onClicked(action: VUIClickEvent => Any) = delegate.addMouseListener(new MouseAdapter() {
    override def mouseClicked(e: MouseEvent) = SwingUtilities.invokeLater(new Runnable {

      override def run() = action(e)
    })
  })

  //----------------------
  //-- Geometry listeners
  override def onShown(action: => Unit) = {

    println("Registering action for ComponentShown")
    var wrapper: (() => Unit) = {
      () => action
    }

    delegate.addComponentListener(new ComponentAdapter() {

      override def componentShown(e: ComponentEvent) = wrapper()
      override def componentResized(e: ComponentEvent) = wrapper()
    })

    delegate.addAncestorListener(new AncestorListener() {

      var wrapper: (() => Unit) = {
        () => action
      }

      override def ancestorAdded(e: AncestorEvent) = wrapper()
      override def ancestorMoved(e: AncestorEvent) = {}
      override def ancestorRemoved(e: AncestorEvent) = {}

    })

  }

  //---------------------------------------
  // Positioning
  //---------------------------------------
  def setPosition(x: Int, y: Int) = { delegate.setLocation(x, y); delegate.setBounds(x, y, delegate.getBounds().width, delegate.getBounds().height) }
  def getPosition: Pair[Int, Int] = Pair[Int, Int](delegate.getLocation().x, delegate.getLocation().y)

  //----------------------
  // Styling
  //----------------------

  def size(width: Int, height: Int) = {
    delegate.setPreferredSize(new Dimension(width, height))
  }

  // Conversions
  //-----------------------

  //-- Convert component events
  //-----------------------
  def convertMouseEventToVUIDragEvent(ev: MouseEvent): VUIDragEvent = {
    this.populateVUIMouseEvent[VUIDragEvent](ev, new VUIDragEvent)
  }

  //-- Mouse Events
  //-----------------------
  implicit def convertMouseEventToVUIMouseEvent(ev: MouseEvent): VUIMouseEvent = {
    this.populateVUIMouseEvent[VUIMouseEvent](ev, new VUIMouseEvent)
  }

  implicit def convertMouseEventToClickEvent(ev: MouseEvent): VUIClickEvent = {

    // Common
    var click = this.populateVUIMouseEvent[VUIClickEvent](ev, new VUIClickEvent)

    // Click
    click.clickCount = ev.getClickCount()
    click
  }

  private def populateVUIMouseEvent[ET <: VUIMouseEvent](srcEvent: MouseEvent, targetEvent: ET): ET = {

    // Fill in positions
    //-----------
    targetEvent.actualX = srcEvent.getX()
    targetEvent.actualY = srcEvent.getY()

    // Click counts and button
    //-------------------

    targetEvent
  }

  //override def toString = delegate.toString

}

object SwingJComponentCommonDelegate {

  implicit def convertJComponentToSGNode[T <: JComponent](comp: T): SGNode[Any] = { new SwingJComponentCommonDelegate[T](comp) }

  implicit def convertJComponentToSGNodeNoParam(comp: JComponent): SGNode[Any] = { new SwingJComponentCommonDelegate[JComponent](comp) }

}

object SwingNodeWrapper {

  def apply[T <: JComponent](comp: T): SGNode[Any] = { new SwingJComponentCommonDelegate[T](comp) }

}

