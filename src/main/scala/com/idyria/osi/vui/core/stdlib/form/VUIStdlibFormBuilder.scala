/**
  *
  */
package com.idyria.osi.vui.core.stdlib.form

import com.idyria.osi.vui.core.VBuilder
import com.idyria.osi.vui.core.components.scenegraph.SGNode
import com.idyria.osi.vui.core.components.form.VUIList
import com.idyria.osi.vui.core.components.form.VUIInputText
import com.idyria.osi.vui.core.VBuilderBase
import scala.language.postfixOps
/**
  * @author rleys
  *
  */
trait VUIStdlibFormBuilder[T] extends VBuilderBase[T] {


  /**
   * Creates an input text with a list under it.
   * The provided closure allows the user to provide behaviours
   */
  def editableList[CT](cl:(VUIInputText[T] , VUIList[CT,T]) => Unit ) : SGNode[T] = {

 
    
    var res = panel {

    	p =>

    	  p layout = vbox

    	  //-- Input List
	      var inputList =  list[CT]

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
