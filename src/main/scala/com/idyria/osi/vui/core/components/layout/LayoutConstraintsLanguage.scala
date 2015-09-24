package com.idyria.osi.vui.core.components.layout


trait LayoutConstraintsLanguage {


    def alignLeft = LayoutConstraint("align" -> "left")
    def alignRight = LayoutConstraint("align" -> "right")
    def alignCenter = LayoutConstraint("align" -> "center")
    def alignTop = LayoutConstraint("align"->"top")
    def alignBottom = LayoutConstraint("align"->"bottom")
    
    def spread = LayoutConstraint("spread"->true)
    def pushRight = LayoutConstraint("pushRight" -> true)
    
    /**
     * Push the component up in its placement
     * In a grid context, that would put it up one cell up
     */
    def pushUp(c : Int) : LayoutConstraint = LayoutConstraint("pushUp" -> c)
    def pushUp : LayoutConstraint = pushUp(1)
    
    def expandWidth = LayoutConstraint("expandWidth"->true)
    def expandHeight = LayoutConstraint("expandHeight"->true)
    def expand = LayoutConstraint("expand"->true)

    def rowSpan(s:Int) = LayoutConstraint("rowspan" -> s )
    def colSpan(s:Int) = LayoutConstraint("colspan" -> s )
}
