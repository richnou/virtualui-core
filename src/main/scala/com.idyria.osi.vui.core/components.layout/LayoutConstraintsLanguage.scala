package com.idyria.osi.vui.core.components.layout


trait LayoutConstraintsLanguage {


    def alignLeft = LayoutConstraints("align" -> "left")
    def alignRight = LayoutConstraints("align" -> "right")
    def spread = LayoutConstraints("spread"->true)
    def pushRight = LayoutConstraints("pushRight" -> true)
    def expandWidth = LayoutConstraints("expandWidth"->true)
    def expandHeight = LayoutConstraints("expandHeight"->true)
    def expand = LayoutConstraints("expand"->true)

}
