package com.idyria.osi.vui.lib.view.wizard




object WizardExample1 extends App {


    println("Welcome to WizardExample1")


    // Let's Define a Wizard
    //-------------
    object wizard extends WizardDialog {

        // Parameters
        //----------------
        dialog.size(800,600)
        dialog.title("WizardExample1")

        // Views
        //----------
        "A" is {

            node  => 

                node layout = vbox

                node <= label("View A")
                node <= button("Go to B") {
                    button => button.onClicked {

                        "B"

                    }
                }


        }

        "B" is {

            node => 


               node layout = vbox

               // node <= label("View B")
                node <= button("Go to C") {
                    button => button.onClicked {

                        "C"

                    }
                }
                node <= button("Produce an error") {
                    button => 
                    button.disable
                    button.onClicked {

                       throw new RuntimeException("Reached here through exception")

                    }
                }

        }

        "C" is {

            node => 


                node layout = vbox

               // node <= label("View C grats!")
                node <= button("Reset") {
                    button => button.onClicked {

                        resetView

                    }
                }
        }

        "error" is {
            node => 


                node layout = vbox

               // var msg = node <= label("Error View")
                node <= button("Reset") {
                    button => button.onClicked {

                        resetView

                    }
                }

        
                node.onWith("error") {
                    e : Throwable => 

                    //msg.setText(s"Error: ${e}")
                }
        }


    }

    wizard.showDialog

    println("End Of Dialog")

    /*
    var wizard = new Wizard() {

        "A" is {

            node : SGGroup[Any] => 



        }

        "B" is {

            node => 

        }

        "C" is {

            node => 

        }

        "error" is {
            node => 
            
        }

    }*/

}
