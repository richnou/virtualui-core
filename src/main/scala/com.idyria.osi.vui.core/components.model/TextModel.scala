package com.idyria.osi.vui.core.components.model


import com.idyria.osi.tea.listeners._

/**
    To be mixed-in by classes wishing to notify support for Text Model

*/
trait TextModelSupport {

    /**
        Set the text model of this component
    */
    def setModel( model : TextModel )

}

/**
    Represents a Text model

*/
trait TextModel extends ListeningSupport {

    private var textContent = ""


    /**
        Set Text Value, and notify listeners
    */
    def setText(str: String) = {

        this.textContent = str

        @->("model.setText",this.textContent)

    }

    def getText() = this.textContent

}


/**
    Default Standard implementation of TextModel

*/
class DefaultTextModel extends TextModel {

}
