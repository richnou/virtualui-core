/**
 *
 */
package org.losthold.vui.swing

import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter

import org.losthold.vui.components.VUIDragEvent
import org.losthold.vui.components.VUIMouseEvent
import org.losthold.vui.components.controls.ControlsBuilder
import org.losthold.vui.components.controls.VUIButton
import org.losthold.vui.components.controls.VUILabel
import org.losthold.vui.components.scenegraph.SGNode

import javax.swing.JButton
import javax.swing.JLabel

/**
 * @author rleys
 *
 */
trait SwingControlsBuilder extends ControlsBuilder[Component] {

  trait NodeCommon extends SGNode[Component] {
    
    //override def revalidate = base.revalidate()
    
  }
  
  /**
   * Implements returning a label
   */
  override def label(text:String) : VUILabel[Component] = {
    
    new JLabel(text) with VUILabel[Component] with SGNode[Component] with NodeCommon {
      
      // Node
      //---------
      
      def base : Component = this
      override def revalidate = super.revalidate
      
      // Actions
      //------------------
      
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
      
      override def onClicked(action: => Unit) =  this.addMouseListener(new MouseAdapter() {          
        override def  mouseClicked(e : MouseEvent) = action
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
    
  }
  
  /**
   * Implements returning a button
   */
  def createButton(text: String) : VUIButton[Component] = {
    
    // Create Button
    //-------------------
    
    return new JButton(text) with VUIButton[Component] {
      
      // Node
      //--------------
      
      def base : Component = this
      override def revalidate = super.revalidate
      
      
      // Positioning
      //---------------------  
      def setPosition(x:Int,y:Int) = {super.setLocation(x, y); super.setBounds(x,y,super.getBounds().width,super.getBounds().height)}
      def getPosition : Pair[Int,Int] = Pair[Int,Int](super.getLocation().x,super.getLocation().y) 
  
      // Dummy Overrides for JComponent compatibility
      override def setX(x:Int) = super.setX(x)
      override def getX: Int = getPosition._1
      override def setY(y:Int) = super.setY(y)
      override def getY: Int = getPosition._2
      
      override def onClicked(action: => Unit) = {
        
        this.addActionListener(new ActionListener() {
          
          def actionPerformed(ev: ActionEvent) = {
            action
          }
          
        })
        
      }
      
    }
    
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
  
  
}