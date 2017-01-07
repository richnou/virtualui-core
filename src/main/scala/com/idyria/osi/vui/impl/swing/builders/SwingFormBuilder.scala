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
import com.idyria.osi.vui.core.components.form.FormBuilderInterface
import com.idyria.osi.vui.core.components.controls.VUIRadioButton
import javax.swing.JRadioButton
import com.idyria.osi.vui.core.components.controls.ToggleGroup
import java.util.Collection
import javax.swing.event.ListSelectionListener
import javax.swing.event.ListSelectionEvent
import com.idyria.osi.vui.core.components.form.ListBuilderInterface
import scala.reflect.ClassTag

/**
 * @author rleys
 *
 */
trait SwingFormBuilder extends FormBuilderInterface[Component] with ListBuilderInterface[Component] {

  // Data
  //--------------------------
  
  def list[CT]: VUIList[CT,Component] = {

    return new SwingJComponentCommonDelegate[JList[CT]](new JList[CT]()) with VUIList[CT,Component] {

       
      
      // Model
      //-------------------
    	
      //-- Default
      this.delegate.setModel(new SwingListModelAdapter[CT](modelImpl))
        
      onWith("model.changed") {
        m : ListModel[CT] => 
          this.delegate.setModel(new SwingListModelAdapter(m))
      }
      
      // Implementation
      //----------------------------------

      /**
       * Select the object if contained into model
       */
      def select(obj: CT) = {

        this.delegate.setSelectedValue(obj, true)

      }

      // Selection
      //-----------------

      def clearSelection = delegate.clearSelection
      
      def onSelected(cl: Seq[CT] => Unit) = {
        
        this.delegate.addListSelectionListener(new ListSelectionListener {
          
          def valueChanged( e : ListSelectionEvent ): Unit = {
           
            //-- Gather Selection
            e.getValueIsAdjusting() match {
              case true => 
              case false => 
                
                //-- Call the closure with values
                cl(delegate.getSelectedValues().toSeq.asInstanceOf[Seq[CT]])
            }
            
          }

          
        })
        
      }

    }.asInstanceOf[VUIList[CT,Component]]

  }
  
  def comboBox[CT](implicit tag : ClassTag[CT]) : VUIComboBox[CT,Component] = {
    
    return new SwingJComponentCommonDelegate[JComboBox[CT]](new JComboBox[CT]()) with VUIComboBox[CT,Component] {

     
      
      // Model
      //-------------------
    	
      //-- Default
      this.delegate.setModel(new SwingComboBoxModelModelAdapter[CT](modelImpl))
        
      onWith("model.changed") {
        m : ComboBoxModel[CT] => 
          this.delegate.setModel(new SwingComboBoxModelModelAdapter[CT](m))
      }
      

      // Events
      //--------------
      
      //FIXME
      def onSelected(cl: CT => Unit)= {
        
        this.delegate.addItemListener(new ItemListener {
          
          
          def itemStateChanged( e : ItemEvent) = {
            
            cl(e.getItem().asInstanceOf[CT])
            
          }
          
        }
        )
        
      }

      

      // Selection
      //-----------------

      /**
       * Select the object if contained into model
       */
      override def select(obj: CT) : Unit = {

        this.delegate.setSelectedItem(obj)

      }
      
      //def clearSelection = delegate.clearSelection

    }.asInstanceOf[VUIComboBox[CT,Component]]
    
    
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
  
  def radioButton : VUIRadioButton[Component] = {
    
    return new SwingJComponentCommonDelegate[JRadioButton](new JRadioButton()) with VUIRadioButton[Component] {

    	
      // State
      //--------------
      def isChecked = this.delegate.isSelected()
      def setChecked(b:Boolean) = this.delegate.setSelected(true)
      
      // Text
      //-----------
      def setText(str: String) = {
        this.delegate.setText(str)
      } 
      def getText = delegate.getText()

    }
    
  }
  
  def toggleGroup : ToggleGroup = {
    return new ToggleGroup {}
  }

}
