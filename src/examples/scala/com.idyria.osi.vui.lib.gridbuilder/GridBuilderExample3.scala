package com.idyria.osi.vui.lib.gridbuilder2


object GridBuilderExample3 extends App with GridBuilder {

    var url = getClass.getClassLoader.getResource("com.idyria.osi.vui.lib.gridbuilder/hd_logo_standard_sw_16cm_rgb.png")
    println("IMG URL: "+url)
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

           "top" row {

                 label("Top center") using("align" -> "center")

            }

            "top" row  {

                 (label("Top Left") using ("align"->"left"))

            }

            "top with two" row {

                 (label("Top Left") | (label("Top Right on Right") using alignRight))

            }

            "middle" row {

                uniLogo spanRight {  
                                      println("In SPAN RIGHT Content")
                                      "-" row(label("Image Right"))
                                      "-" row(label("Image Right bottom"))
                                                       
                                    }

            }


            "middle2" row {

                label("Hello") using ("expand" -> true) spanRight {  
                                      println("In SPAN RIGHT Content")
                                      "-" row(label("Image Right"))
                                      "-" row(label("Image Right bottom"))
                                                       
                                    }

            }

            "bottom" row using("align"->"right") {

              textInput using ("expandWidth" -> true,"align"->"left")

            }

            "bottom2" row grid("expandWidth"->true,"spread"->true) {

              //println("Text ")
              "input" row {textInput using ("expandWidth" -> true,"align"->"left")}
              

            }

            

          
        }


    }

    ui.show

}
