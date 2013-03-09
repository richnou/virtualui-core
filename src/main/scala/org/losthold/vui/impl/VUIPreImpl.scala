/**
 *
 */
package org.losthold.vui.impl

import org.losthold.vui.VUIBuilder
import org.losthold.vui.components.main.VuiFrame
import javax.swing.JButton
import org.losthold.vui.components.controls.VUIButton
import java.awt.Component
import java.awt.event.ActionListener
import org.losthold.vui.components.containers.VUITabPane
import org.losthold.vui.components.containers.VUIPanel

/**
 * This Preimplementation layer ease implementation by providing a default
 * implementation of most of the trait builder methods, only enforcing underlying real Components creation
 * 
 * 
 * @author rleys
 *
 */
abstract class VUIPreImpl[T] extends VUIBuilder[T] {

  // Main Builder
  //--------------------
  
  def createFrame() : VuiFrame[T];
  override def frame( content : VuiFrame[T] => Unit) : VuiFrame[T] = {
    
    //-- Create Frame
    var frame = createFrame()
    
    //-- Execute content
    content(frame)
    
    //-- Return
    return frame
    
    
  }
  
  
  // Controls
  //---------------------------
  
  /**
   * Call underlying create method, and apply content closure
   */
  def createButton(text:String) : VUIButton[T]
  final def button(text: String)(cl: VUIButton[T] => Unit) : VUIButton[T] = {
    
    //-- Create Button
    var button = this.createButton(text)
    
    //-- Apply content
    cl(button)
    button
 
  }
  
  // Container
  //------------------------
  
  def createTabPane : VUITabPane[T] 
  def createPanel : VUIPanel[T]
  
  /**
   * Call underlying create method, and apply content closure
   */
  final def tabpane(cl: VUITabPane[T] => Unit) : VUITabPane[T] = {
    
    //-- Create
    var tabpane = this.createTabPane
    
    //-- Apply content
    cl(tabpane)
    tabpane
    
  }
  
  /**
   * Call provided create method and apply content closure
   */
  final def panel(cl: VUIPanel[T] => Unit) : VUIPanel[T] = {
    
    //-- Create
    var panel = this.createPanel
    
    //-- Apply content
    cl(panel)
    panel
    
  }
  
  
}