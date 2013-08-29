package com.idyria.osi.vui.lib.gridbuilder2


object GridBuilderExample3 extends App with GridBuilder {

    var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
    var uniLogo = image(url) {
        img => img size(250,-1)
    }
    var uniLogo2 = image(url) {
        img => img size(250,-1)
    }
    var uniLogo3 = image(url) {
        img => img size(250,-1)
    }
    var uniLogo4 = image(url) {
        img => img size(250,-1)
    }
 

    var ui = frame { f=>

        f size(800,600)
        f title ("GridBuilderExample3")

        f <= grid {

           /* "top" row {

                 //label("Top")

            }

            "middle" row {

                /*uniLogo spanRight 2 {  
                                      row(label("Image Right"))
                                      row(label("Image Right bottom"))
                                                       
                                    }*/

            }
*/
            "bottom" row using("align"->"right") {

            }

          
        }


    }

    ui.show

}
