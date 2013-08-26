package com.idyria.osi.vui.lib.gridbuilder


object GridBuilderExample1 extends App with GridBuilder {

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

                uniLogo2 spanRight 4 {  
                                      row { (label("Login: ")) | (textInput  using ("expandWidth" -> true,"expand" -> true,"relative" -> true)) | label(" OK ")}

                                      using("rowspan"->3,"expandWidth" -> true){row(label("Image Right bottom"))}

                                      row((label("Image Right bottom") using ("rowspan"-> 3,"expandWidth" -> true)))

                                      row(label("Image Right bottom"),"rowspan"-> 3,"expandWidth" -> true)            
                                    }

            }

            "login" row {

                uniLogo3 spanRight 4 {  
                                      row { (textInput  using ("expandWidth" -> true)) | (label("Login: ")) | label(" OK ")}

                                      using("rowspan"->3,"expandWidth" -> true){row(label("Image Right bottom"))}

                                      row((label("Image Right bottom") using ("rowspan"-> 3,"expandWidth" -> true)))

                                      row(label("Image Right bottom"),"rowspan"-> 3,"expandWidth" -> true)            
                                    }

            }

            "login" row {

                uniLogo4 spanRight 4 {  
                                      row {  (label("Login: ")) | (label("test")  using ("expandWidth" -> true)) |label(" OK ")}

                                      using("rowspan"->3,"expandWidth" -> true){row(label("Image Right bottom"))}

                                      row((label("Image Right bottom") using ("rowspan"-> 3,"expandWidth" -> true)))

                                      row(label("Image Right bottom"),"rowspan"-> 3,"expandWidth" -> true)            
                                    }

            }
        }


    }

    ui.show

}
