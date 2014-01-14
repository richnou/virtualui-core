/**
 *
 */
package com.idyria.osi.vui.impl.swing.builders

import com.idyria.osi.vui.impl.swing.model._
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
import com.idyria.osi.vui.core.components.layout._
import com.idyria.osi.vui.core.components.model._
import javax.swing.JTextArea
import com.idyria.osi.vui.core.components.form.VUICheckBox
import javax.swing.JCheckBox
import javax.swing.JScrollPane
import com.idyria.osi.vui.core.components.form.VUIComboBox
import javax.swing.JComboBox
import javax.swing.DefaultComboBoxModel
import java.awt.event.ItemListener
import java.awt.event.ItemEvent

/**
 * @author rleys
 *
 */
trait SwingFormBuilder extends FormBuilder[Component] {

  // Data
  //--------------------------
  
  def list(): VUIList[Component] = {

    return new SwingJComponentCommonDelegate[JList[Object]](new JList[Object]()) with VUIList[Component] {

       
      
      // Model
      //-------------------
    	
      //-- Default
      this.delegate.setModel(new SwingListModelAdapter(modelImpl))
        
      onWith("model.changed") {
        m : ListModel => 
          this.delegate.setModel(new SwingListModelAdapter(m))
      }
      
      // Implementation
      //----------------------------------

      /**
       * Select the object if contained into model
       */
      def select(obj: AnyRef) = {

        this.delegate.setSelectedValue(obj, true)

      }

      // Selection
      //-----------------

      def clearSelection = delegate.clearSelection

    }.asInstanceOf[VUIList[Component]]

  }
  
  def comboBox : VUIComboBox[Component] = {
    
    return new SwingJComponentCommonDelegate[JComboBox[Object]](new JComboBox[Object]()) with VUIComboBox[Component] {

     
      
      // Model
      //-------------------
    	
      //-- Default
      this.delegate.setModel(new SwingComboBoxModelModelAdapter(modelImpl))
        
      onWith("model.changed") {
        m : ComboBoxModel => 
          this.delegate.setModel(new SwingComboBoxModelModelAdapter(m))
      }
      

      // Events
      //--------------
      
      //FIXME
      def onSelected(cl: Any => Unit)= {
        
        this.delegate.addItemListener(new ItemListener {
          
          
          def itemStateChanged( e : ItemEvent) = {
            
            cl(e)
            
          }
          
        }
        )
        
      }

      

      // Selection
      //-----------------

      /**
       * Select the object if contained into model
       */
      def select(obj: AnyRef) = {

        this.delegate.setSelectedItem(obj)

      }
      
      //def clearSelection = delegate.clearSelection

    }.asInstanceOf[VUIComboBox[Component]]
    
    
  }
  
  // Text Inputs
  //--------------------------------------------------
  
  class SwingTextInput[T <: JTextComponent](component: T) extends SwingJComponentCommonDelegate[T](component) with VUIInputText[Component] with SwingTextModelSupport {

      // Constraints
      //---------------------------

      var v = ("expandWidth" -> true)

      this.fixedConstraints(v)

      //-- Listen to constraints for area support
      on("constraints.updated") {
         this.fixedConstraints.getOption("area") match {
           
           // Switch to JTextArea on area request
           case Some(area) if(area==true) =>
               
           // Switch to JTextField on field request (not area)
           case Some(area) if(area==false) =>
             
           case None =>
         }
      }

      // Events
      //---------------------
      override def onEnter(action: => Unit) = {
        /*delegate.addActionListener(new ActionListener() {
                def actionPerformed(ev: ActionEvent) {
                  action
                }
              })*/
      }

      // Text and Model
      //--------------
      
      def getDocument: Document = delegate.getDocument
      def setText(str: String) = delegate.setText(str)
      def getText = delegate.getText
      
      // Text Return
      //----------------------
      override def toString: String = "Field: " + delegate.getText()
    }
  

  def textInput(): VUIInputText[Component] = {

    return new SwingTextInput[JTextField](new JTextField)
    
  

  }
  
  def textArea(): VUIInputText[Component] = {

    var ta = new JTextArea
    
    return new SwingJComponentCommonDelegate[JScrollPane](new JScrollPane(ta)) with VUIInputText[Component] with SwingTextModelSupport {
      
      
       // Some Defaults
      //-------------------
      ta.setLineWrap(true)
      ta.setAutoscrolls(true)
      
      def setText(str: String) = ta.setText(str)
      def getText = ta.getText()
      def getDocument() = ta.getDocument()
      
      
    }
    
 
  

  }
  
  
  // Buttons
  //-----------------------
  
  def checkBox : VUICheckBox[Component] = {
    
    return new SwingJComponentCommonDelegate[JCheckBox](new JCheckBox()) with VUICheckBox[Component] {

    	
      // State
      //--------------
      def isChecked = this.delegate.isSelected()
      
      // Text
      //-----------
      def setText(str: String) = {
        this.delegate.setText(str)
      } 
      def getText = delegate.getText()

    }
    
  }

}
