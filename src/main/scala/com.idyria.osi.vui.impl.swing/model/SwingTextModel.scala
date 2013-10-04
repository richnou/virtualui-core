package com.idyria.osi.vui.impl.swing.model

import com.idyria.osi.vui.core.components.model._

import javax.swing._
import javax.swing.text._
import javax.swing.event._

import com.idyria.osi.tea.swing._
 
trait SwingTextModelSupport  extends  TextModelSupport {

	//var textModel : TextModel = new DefaultTextModel()
  
    def getDocument() : Document

    /// Set to true to avoid Listener trigger loop update, when events are send from one of the listeners to the other listener
    var listenerActive = false

    ///---- Swing -> Text Model
    this.getDocument().addDocumentListener(new DocumentListener {

        def changedUpdate(e: javax.swing.event.DocumentEvent): Unit =  {

            if (listenerActive || SwingTextModelSupport.this.modelImpl==null)
                return

            listenerActive = true
            SwingTextModelSupport.this.modelImpl.setText(e.getDocument.getText(0,e.getDocument.getLength))
            listenerActive = false

        }
        def insertUpdate(e: javax.swing.event.DocumentEvent): Unit =  {

            if (listenerActive || SwingTextModelSupport.this.modelImpl==null)
                return

            listenerActive = true
            SwingTextModelSupport.this.modelImpl.setText(e.getDocument.getText(0,e.getDocument.getLength))
            listenerActive = false
        }
        def removeUpdate(e: javax.swing.event.DocumentEvent): Unit =  {

            if (listenerActive || SwingTextModelSupport.this.modelImpl==null)
                return

            listenerActive = true
            SwingTextModelSupport.this.modelImpl.setText(e.getDocument.getText(0,e.getDocument.getLength))
            listenerActive = false
        }

    })

    ///---- TextModel -> Swing



    /// Clean and set text
    val setTextClosure = {
        str : String =>

            TeaSwing.invokeLater {
                listenerActive = true
                this.getDocument().remove(0,this.getDocument.getLength)
                this.getDocument().insertString(0,str,null)
                listenerActive = false
            }

    }

    //-------------


}
