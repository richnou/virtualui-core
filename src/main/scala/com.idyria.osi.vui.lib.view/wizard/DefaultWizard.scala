package com.idyria.osi.vui.lib.view.wizard

import com.idyria.osi.vui.lib.view.wizard._
import com.idyria.osi.vui.lib.view._
import com.idyria.osi.vui.core.components.form._
import com.idyria.osi.vui.lib.placeholder._
import com.idyria.osi.vui.lib.gridbuilder._

class DefaultWizardDialog extends WizardDialog with PlaceHolder with GridBuilder {

    // Configuration
    //--------------------

    /**
        If set to true, a list of the steps with the currently highlighted steps will be displayed
        @group configuration
    */
    var displayListOfSteps = true
    var stepsList : VUIList[Any] = null

    // Place Holder
    //------------------------
    override def changeView (view:View) = {

        // Change Placeholder
        place("middle")(view.content)

        // Try to highlight in list
        if (stepsList!=null) {
            stepsList.clearSelection
            stepsList.select(view.name)
        }

        // Repaint
        dialog.revalidate

    }

    // Default Design
    //-------------------
    override def showDialog = {

        // Init
        this.initDialog

        // Place first view
        this.views.headOption match {
            case Some(view) => ->(view.name)
            case None       => throw new RuntimeException("Showing Default Wizard with no defined views....")
        }

        // Show
        super.showDialog
    }

    /**
        Create the default wizard GUI
    */
    override def initDialog() = {

        var expandConstraint = new Tuple2("expand",true)

        println("Preparing default wizard")
        this.dialog {
            dialog => 

                dialog layout vbox

                dialog <= grid {
                    
                    // Top : Title
                    //---------------- 
                    "top" row alignLeft {

                        this.name match {
                            case name if(name==null) => (label("Unnamed Wizard") using ("spread" -> true))
                            case name => (label(name) using ("spread" -> true))
                        }
                        
                    }

                    // Middle placeholder for views
                    //-----------------------------------
                    this.displayListOfSteps match {

                        // With List on the left
                        case true => 
                            "middle" row  {

                                // Prepare list 
                                stepsList = list {
                                    l => 
                                      
                                      
                                      //this.view.foreach(v => l..add(v.name))
                                }

                                // Create Column
                                (stepsList using expandHeight) | (placeHolder("middle") using expand)
                                

                            }
                        case false => 
                            "middle" row using("expand"-> true) { placeHolder("middle") }
                    }
                     

                    // Bottom: Controls
                    //-----------------------
                    "bottom" row using(spread,expandWidth) { 

                            subgrid {
                                    
                                "-" row {
        
                                        ( 
                                            button("Cancel") { b => 



                                            } using pushRight

                                        )  | (

                                            button("Next") { b => 

                                                b.onClicked {
                                                    nextView
                                                }

                                            }

                                        )
        
                                }
                        }
                            
                   
                    }
                    

                }
                
        }

        this.dialog.revalidate

        

    }
    


}
