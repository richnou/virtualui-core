package org.losthold.vui.swing

import java.awt.Component
import java.awt.Dimension
import java.awt.Window

import org.losthold.vui.BuilderTopTrait
import org.losthold.vui.FrameViewContainer
import org.losthold.vui.components.VUIComponent
import org.losthold.vui.components.layout.VUIVBoxLayout

import javax.swing.Box
import javax.swing.JFrame
import javax.swing.WindowConstants

class SwingVUI extends BuilderTopTrait[Component]
				with SwingControlsBuilder {

				
  
  var current : Unit = null
  
  
  def createFrame() : FrameViewContainer[JFrame,Component] =  {
    
    //- -Create Frame
    val swingFrame = new JFrame()
    
    //-- Return FrameView Container
    new FrameViewContainer[JFrame,Component] (swingFrame) with SwingContainerTrait {
      
      // Per default dispose
      base.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE)
      
      def title( title: String ) = base.setTitle(title)
      
      def size  (width:Int,height:Int) = {
        base.setSize(width,height)
      }
      def width(width: Int) = base.setSize(new Dimension(width,base.getSize().height))
      def height(height: Int) = base.setSize(new Dimension(base.getSize().width,height))
      
      def show() {
        base.setVisible(true)
      }
      
      def layout( layout: VUIVBoxLayout[Component]) : Unit = {
        
        println("Applying VBOX Layout")
        
        //-- Create a panel
        var panel = Box.createVerticalBox()
        
        //-- Add
        base.setContentPane(panel)
        //base.getContentPane().add(panel);
        
        //-- Add components to box
        for ( component <- layout.components) {
          
           println("Adding to vertical box: "+component)
          
          panel.add(component.toBase)

        }
        
        panel revalidate
        
      }
      
      
    }
    
  }
 
  
  implicit def vuiComponentToComponent(cp: VUIComponent[Component]) = cp.toBase

  
  
}