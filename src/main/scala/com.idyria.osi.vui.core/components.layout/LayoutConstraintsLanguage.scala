package com.idyria.osi.vui.core.components.layout


trait LayoutConstraintsLanguage {


    def alignLeft = LayoutConstraint("align" -> "left")
    def alignRight = LayoutConstraint("align" -> "right")
    def spread = LayoutConstraint("spread"->true)
    def pushRight = LayoutConstraint("pushRight" -> true)
    def expandWidth = LayoutConstraint("expandWidth"->true)
    def expandHeight = LayoutConstraint("expandHeight"->true)
    def expand = LayoutConstraint("expand"->true)

    def top = LayoutConstraint("align"->"top")
    
}
