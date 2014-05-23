/**
 *
 */
package com.idyria.osi.vui.core.components.controls

import com.idyria.osi.vui.core.VUIBuilder

import java.net._

/**
 *
 * This trait defines the methods used to create the VUI controls
 *
 * @author rleys
 *
 */
trait ControlsBuilder[T] {

  /**
   * Contstruct a simple text display
   */
  def label(text:String) : VUILabel[T] = VUIBuilder.as[ControlsBuilderInterface[T]].label(text)
 
  /**
    Construct an Image
  */
  def image(text: URL) : VUIImage[T] = VUIBuilder.as[ControlsBuilderInterface[T]].image(text)

  /**
   * Constructs a simple button
   */
  def button(text: String) : VUIButton[T] = VUIBuilder.as[ControlsBuilderInterface[T]].button(text)

  /**
   * Construct an area that can hold more text
   */
  def text : VUIText[T] = VUIBuilder.as[ControlsBuilderInterface[T]].text
  
 

}

trait ControlsBuilderInterface[T] {
  
   /**
   * Contstruct a simple text display
   */
  def label(text:String) : VUILabel[T]
 
  /**
    Construct an Image
  */
  def image(text: URL) : VUIImage[T]

  /**
   * Constructs a simple button
   */
  def button(text: String) : VUIButton[T]

  /**
   * Construct an area that can hold more text
   */
  def text : VUIText[T]
  
  
  
  
  
}


