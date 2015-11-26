package com.idyria.osi.vui.core.validation

import com.idyria.osi.vui.impl.html.TreeBuilder
import com.idyria.osi.vui.impl.html.components.HTMLNode

/**
 * This trait should be mixed in a TreeBuilder to add validation language functions to the treeBuilder
 */
trait ValidationTreeBuilderLanguage extends TreeBuilder[HTMLNode[_ <: org.w3c.dom.Node]] {

  // Apply Rule to the currentNode if possible
  //---------------------
  private def applyToCurrent(v: Validator) = {

    this.currentNode match {
      case null                                 ⇒
      case validationSupport: ValidationSupport ⇒ validationSupport.addValidator(v)
      case _                                    ⇒
    }

  }

  // Requirement
  //----------
  def required = applyToCurrent(RequiredValidator)

  // String Rules
  //------------------------
  def minLength(l: Int) = applyToCurrent(new MinLengthValidator(l))

  // Email
  //----------------
  
  // Customs
  //-------------
  
  /**
   * Usage Example:
   * 
   * inputText("name") {
   *  validate {
   * 	value => 
   *  		// Checks here
   *  }
   * 
   * }
   */
  def validate(validationClosure: String => Unit) = {
    applyToCurrent(new ValueValidator {
      def validate(value: String): Unit = validationClosure(value)
    })
  }

}

/**
 * To be mixed in components that can support ValidationSupport
 *
 * Validation happens with string based values, because of serialisation between client and server logic
 */
trait ValidationSupport {

  var validators = List[Validator]()

  /**
   * Add a new validator to the ones that should be applied to the current element
   */
  def addValidator(v: Validator) = {
    validators = validators :+ v
  }

  /**
   * Validate the current element
   * Passed value is an option, because the validators decide if it is bad that no value is present
   */
  def validate(value: Option[String]) = {
    validators.foreach(_.validate(value))
  }

}

/**
 * Base trait for classes implementing a validation
 */
trait Validator {

  /**
   * Passed value is an option, because the validator decides if it is bad that no value is present
   */
  def validate(value: Option[String]): Unit

}

/**
 * This is a simple validator, that ignores validation if None if provided as value, and just calls validate(String) otherwise
 */
trait ValueValidator extends Validator {

  def validate(value: Option[String]): Unit = {
    value match {
      case None    ⇒
      case Some(v) ⇒ this.validate(v)
    }
  }

  def validate(value: String): Unit

}