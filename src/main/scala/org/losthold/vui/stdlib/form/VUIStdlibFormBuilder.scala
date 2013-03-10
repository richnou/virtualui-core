/**
  *
  */
package org.losthold.vui.stdlib.form

import org.losthold.vui.VBuilder
import org.losthold.vui.components.scenegraph.SGNode
import org.losthold.vui.components.form.VUIList
import org.losthold.vui.components.form.VUIInputText
import org.losthold.vui.VBuilderBase

/**
  * @author rleys
  *
  */
trait VUIStdlibFormBuilder[T] extends VBuilderBase[T] {

  
  /**
   * Creates an input text with a list under it.
   * The provided closure allows the user to provide behaviours
   */
  def editableList(cl:(VUIInputText[T] , VUIList[T]) => Unit ) : SGNode[T] = {
    
    var res = panel {
      
    	p => 
    	  
    	  p layout vbox
      
    	  //-- Input List
	      var inputList =  list
                
          //-- Input text list
          var inputField = textInput
          
           p <= inputField
           p <= inputList
    	  
           //-- Call closure
           cl (inputField,inputList)
           
    }
    
    
    res
    
    
  }
  
  
}