package com.idyria.osi.vui.lib.gridbuilder


object GridBuilderExample2 extends App with GridBuilder {

    var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
    var uniLogo = image(url) {
        img => img size(250,-1)
    }
   
 

    var ui = frame { f=>

        f size(800,600)
        f title ("GridBuilderExample1")

        f <= grid {

            "top" row {

                 label("Top")

            }

          

            "login" row {

                uniLogo spanRight 4 {  
                                      row { (label("Login: ")) | (textInput  using ("expandWidth" -> true)) | label(" OK ")}

                                      using("rowspan"->3,"expandWidth" -> true){row(label("Image Right bottom"))}

                                      row((label("Image Right bottom") using ("rowspan"-> 3,"expandWidth" -> true)))

                                      row(label("Image Right bottom"),"rowspan"-> 3,"expandWidth" -> true)            
                                    }

            }

        }


    }

    ui.show

}
