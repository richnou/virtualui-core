package com.idyria.osi.vui.lib.gridbuilder


object GridBuilderExample1 extends App with GridBuilder {

    var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
    var uniLogo = image(url) {
        img => img size(250,-1)
    }
    var uniLogo2 = image(url) {
        img => img size(250,-1)
    }
 

    var ui = frame { f=>

        f size(800,600)
        f title ("GridBuilderExample1")

        f <= grid {

            "top" row {

                 label("Top")

            }

            "middle" row {

                uniLogo spanRight 2 {  
                                      row(label("Image Right"))
                                      row(label("Image Right bottom"))
                                                       
                                    }

            }

            "login" row {

                uniLogo2 spanRight 2 {  
                                      row { label("Login: ") | textInput | label(" OK ")}
                                      using("rowspan"->3){row(label("Image Right bottom"))}
                                      row(label("Image Right bottom"),"rowspan"->3)            
                                    }

            }
        }


    }

    ui.show

}
