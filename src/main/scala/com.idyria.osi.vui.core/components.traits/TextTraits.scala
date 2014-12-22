package com.idyria.osi.vui.core.components.traits

import com.idyria.osi.vui.core.styling.CommonVUIElementTrait



/**
 * A trait for all Components whose text might be set with setText
 */
trait TextTrait extends CommonVUIElementTrait {
  
  /**
    * Set the content of text element
    */
   def apply(text:String) : Self = {
     this.setText(text)
     this.asInstanceOf[Self]
   }
   
    /**
    * Set the content of text element
    */
   def apply(textCl: => String) : Self = this.apply(textCl)
  
  /**
   * Set text of this input text
   */
  def setText(str: String)
   
  /**
   * Get the text of this element
   */
  def getText : String
  
}