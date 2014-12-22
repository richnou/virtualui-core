package com.idyria.osi.vui.core.validation

object RequiredValidator extends Validator {

  def validate(v: Option[String]) = {
    v match {
      case Some(v) ⇒
      case None    ⇒ throw new RuntimeException("Value is required, none provided")
    }
  }

}

class MinLengthValidator(var length: Int) extends ValueValidator {

  def validate(value: String): Unit = {
    value.length() match {
      case l if (l < length) ⇒ throw new RuntimeException(s"Provided value must be at least $length characters long")
      case _                 ⇒
    }
  }

}
