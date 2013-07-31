/**
  *
  */
package com.idyria.osi.vui.core.swing

import com.idyria.osi.vui.core.swing.model._

import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JTextField
import javax.swing.JList
import javax.swing.DefaultListModel

import javax.swing.text._

import com.idyria.osi.vui.core.components.form.FormBuilder
import com.idyria.osi.vui.core.components.form.VUIInputText
import com.idyria.osi.vui.core.components.form.VUIList

import com.idyria.osi.vui.core.components.model._

/**
  * @author rleys
  *
  */
trait SwingFormBuilder extends FormBuilder[Component]{

  def list() : VUIList[Component] = {

		  new JList[Object] with VUIList[Component] {

			  val model = new DefaultListModel[Object]
			  this.setModel(model)

			  // Basic OVerrides for all JComponents
		      //--------------------------
			  def base : Component = this
    		  override def revalidate = super.revalidate

    		  def setPosition(x:Int,y:Int) = super.setLocation(x, y)
    		  def getPosition : Pair[Int,Int] = Pair[Int,Int](super.getLocation().x,super.getLocation().y)

    		  // Dummy Overrides for JComponent compatibility
		      override def setX(x:Int) = super.setX(x)
		      override def getX: Int = getPosition._1
		      override def setY(y:Int) = super.setY(y)
		      override def getY: Int = getPosition._2

		      // Implementation
		      //----------------------------------
		      def add(obj : AnyRef)  = {
		    	  model.addElement(obj)
			  }

		  }

  }


  def textInput(): VUIInputText[Component] = {

    var textField = new JTextField with VUIInputText[Component]  with NodeCommon with SwingTextModelSupport {

    		def base : Component = this
    		override def revalidate = super.revalidate

    		// Events
    		//---------------------
    		override def onEnter( action : => Unit) = {
    		  	this.addActionListener(new ActionListener() {
    		  		def actionPerformed(ev: ActionEvent) {
    		  			action
    		  		}
    		  	})
    		}

    		// Text Return
    		//----------------------
    		override def toString:String = this.getText()

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

    return textField

  }

}
