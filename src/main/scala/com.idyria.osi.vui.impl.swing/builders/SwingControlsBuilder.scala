/**
 *
 */
package com.idyria.osi.vui.impl.swing.builders

import java.awt._
import java.awt.event._
import java.awt.image._
import java.net._
import scala.language.implicitConversions
import com.idyria.osi.vui.core.components.controls._
import javax.imageio._
import javax.swing._
import javax.swing.JButton
import javax.swing.JLabel
import com.idyria.osi.vui.impl.swing.model.SwingTreeModelAdapter


/**
 * @author rleys
 *
 */
trait SwingControlsBuilder extends ControlsBuilder[Component] {

 

  /**
   * Implements returning a label
   */
  override def label(text:String) : VUILabel[Component] = {

    return new SwingJComponentCommonDelegate[JLabel](new JLabel(text)) with VUILabel[Component] {

        //this.delegate.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black))
        

      
        // Text
        //--------------
        def setText(str: String) = delegate.setText(str)

    }
  }
  
  /**
   * Implements returning a textarea
   */
  override def text : VUIText[Component] = {

    return new SwingJComponentCommonDelegate[JTextArea](new JTextArea()) with VUIText[Component] {

        //this.delegate.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black))
        
        this.delegate.setEditable(false)
      
    	//-- Line wrap per default
    	this.base.setLineWrap(true)
      
        // Text
        //--------------
        def setText(str: String) = delegate.setText(str)

    }
  }

  /**
    Implements Image
  */
  def image(path: URL) : VUIImage[Component] = {

    new SwingJComponentCommonDelegate[JLabel](new JLabel) with VUIImage[Component] {

       //this.delegate.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black))

        // Save URL
        var url = path

        /**
          Load into JLabel and resize if necessary
        */
        def load = {

          if (this.delegate.getIcon==null) {

            println("Loading image")

            var sizeDimension = delegate.getPreferredSize

            // Read Source Image
            //----------------
            var originalImage = ImageIO.read(url)

            // Resolve Sizes for resizing
            //-------------------------------
            (sizeDimension.width,sizeDimension.height) match {

              // Resize based on Width
              case (width,-1) =>

                  sizeDimension.height = width * originalImage.getHeight / originalImage.getWidth
                  size(sizeDimension.width,sizeDimension.height)

              // Resize based on Height
              case (-1,height) =>

                  sizeDimension.width = height * originalImage.getWidth / originalImage.getHeight
                  size(sizeDimension.width,sizeDimension.height)

              // Do nothing
              case _ =>
            }

            //-- Prepare target Image
            //-----------
            var resizedImage = new BufferedImage(sizeDimension.width, sizeDimension.height, originalImage.getType);
            var g = resizedImage.createGraphics

            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

            g.drawImage(originalImage, 0, 0, sizeDimension.width, sizeDimension.height, null);
            g.dispose

            // Set to Label
            //------------------
            this.delegate.setIcon(new ImageIcon(resizedImage))

          }


          
          

        }

        override def toString : String = s"Image: $url"

    }

  }

  /**
   * Implements returning a button
   */
  def button(text: String) : VUIButton[Component] = {

    // Create Button
    //-------------------
    return new SwingJComponentCommonDelegate[JButton](new JButton(text)) with VUIButton[Component] {

      override def disable = super.disable

      // Actions
      //-------------------
 
      /**
        On clicked maps to action listener
      */
      override def onClicked(action: => Any) =  delegate.addActionListener(new ActionListener() {
       
        override def  actionPerformed(e : ActionEvent) = SwingUtilities.invokeLater(new Runnable {

            override def run() = {

              SwingUtilities.invokeLater(new Runnable() {

                  override def run() = {
                    //println("Button Action invoked later")
                    action
                  }

                })
            } 
          })
      })

        
    }
    
  }
  
  /**
   * Create the Tree component
   */
  def tree : VUITree[Component] = {

    return new SwingJComponentCommonDelegate[JTree](new JTree()) with VUITree[Component] {

      var model : SwingTreeModelAdapter = null
      
      this.delegate.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.black))
      
      /**
       * Change Model
       */
      def setModel(model: TreeModel) = {
        
        // Set
        //--------------
        this.model = new SwingTreeModelAdapter(model)
        this.delegate.setModel(this.model)
        
        // Update View parameters
        //------------------
        model.root.show match {
          case true => 
            
            this.base.setShowsRootHandles(true)
            this.base.setRootVisible(true)
            
          case false => 
            
            this.base.setShowsRootHandles(false)
            this.base.setRootVisible(false)
        }
      } 
      
    
        
    }
    
  }


  


}
