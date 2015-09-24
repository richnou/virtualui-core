package com.idyria.osi.vui.core.components.model

import com.idyria.osi.tea.listeners._

trait ModelSupport[M] extends ListeningSupport {

  var modelImpl: M = _

  /**
   *    Set the text model of this component
   */
  def model_=(model: M) = {

    this.modelImpl = model

    @->("model.changed", model)

  }

  /**
   * Return the text model of this component
   */
  def model: M = this.modelImpl

}

/**
 * To be mixed-in by classes wishing to notify support for Text Model
 *
 */
trait TextModelSupport extends ModelSupport[TextModel] {

  modelImpl = new DefaultTextModel

}

/**
 * Represents a Text model
 *
 */
trait TextModel extends ListeningSupport {

  private var textContent = ""

  // Listen for changes
  //----------

  /**
   * Triggers when the model text is updated (model.setText) event
   */
  def onTextUpdate(cl: String => Any) = {
    onWith("model.setText") {
      text: String =>
        cl(text)
    }
  }

  /**
   * Set Text Value, and notify listeners
   */
  def setText(str: String) = {

    this.textContent = str

    @->("model.setText", this.textContent)

  }

  def getText() = this.textContent

  def insertText(str:String) = {
    this.textContent = s"$textContent$str"
    @->("model.insertText", str)
  }
  
}

/**
 * Default Standard implementation of TextModel
 *
 */
class DefaultTextModel extends TextModel {

}
