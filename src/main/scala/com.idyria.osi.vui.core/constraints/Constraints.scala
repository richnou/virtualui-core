package com.idyria.osi.vui.core.constraints


import scala.language.implicitConversions
import com.idyria.osi.vui.core.styling.CommonVUIElementTrait
import com.idyria.osi.tea.listeners.ListeningSupport


/**
 * Main trait to add Basic functions for constraints setting on a component
 */
trait Constrainable extends CommonVUIElementTrait with ListeningSupport {

  /**
   * Stores all the constraints of this type
   */
  var fixedConstraints: Constraints = Constraints()

  // Constraints Setting
  //----------------
  def apply(constraint: Constraint*): Self = {
    constraint.foreach(this.fixedConstraints(_))
    @->("constraints.updated")
    this.asInstanceOf[Self]
  }
  def apply(constraints: Constraints): Self = {
    constraints.constraints.foreach(c => this.fixedConstraints(c._2))
    @->("constraints.updated")
    this.asInstanceOf[Self]
  }

}


/**
 * Represents a single constraint
 */
class Constraint (
    
  /**
   * All Constraints implementation must have a name
   */
  var name : String) {
  
  
  /**
   * The value of a constraint may be null
   */
  var value : Any = null
  
  
}


object Constraint  {
 
  
  def unapply(c: Constraint) : Option[(String,Any)] = {
    
    Some((c.name,c.value)) 
  }
  
  
  /**
   * Convert a single tuple to a constraint
   */
  implicit def convertTuple1ToConstraint(tuple: Tuple2[String, Any]): DefaultConstraint = {

    var constraint = new DefaultConstraint(tuple._1)
    constraint.value = tuple._2
    constraint
  }
  
}

/**
 * A Default class implementation for simple string to value mappings without any specific constraint class type
 */
class DefaultConstraint( name : String) extends Constraint(name) {
  
 
  
}
object DefaultConstraint {
  
    
  /**
   * Convert a single tuple to a constraint
   */
  implicit def convertTuple1ToConstraint(tuple: Tuple2[String, Any]): DefaultConstraint = {

    var constraint = new DefaultConstraint(tuple._1)
    constraint.value = tuple._2
    constraint
  }
 
  
}

/**
 * Holder fgor Multiple Constraint types
 */
class Constraints {

  /**
   * Map holding the Constraints values
   */
  var constraints = Map[String, Constraint]()

  /**
   * Size of constraints bag
   */
  def size = constraints.size


  /**
   * Add A constraint to this constraints list
   */
  def apply(cs: Constraint*) : Constraints = {

    cs.foreach( c =>  this.constraints = this.constraints + (c.name -> c))
    this
  }
  
  /*def apply(c: Constraint) : Constraints = {
	  this.constraints = this.constraints + (c.name -> c)
	  this
  }*/
  
  def apply(c: (String,Any)) : Constraints = {
	
    //this(c)
    
    this.constraints = this.constraints + Constraints.convertTuple4ToConstraint(c)
    
    //constraints.foreach( c =>  this.constraints = this.constraints + (c.name -> c))
	  
    this
  }
  
  /**
   * Add A constraints bag to this constraints list
   */
  /*def apply(constraints: Constraints) = {

    constraints.foreach( c =>  this.constraints = this.constraints + (c.name -> c))

  }*/
  
  
  /**
   * Return the value of a constraint
   * Throws an exception if non existent
   */
  def get(str: String): Any = {

    this.constraints.get(str) match {
      case Some(res) => res.value
      case None => throw new RuntimeException(s"Layout constraints does not contain key $str")
    }
  }

  def getOption(str: String, whishedClass: Class[_]): Option[Any] = this.constraints.get(str) match {
    
    case Some(element) if(element.value==null) => None
    case Some(element) if (whishedClass.isAssignableFrom(element.getClass)) => Option(element.value)
    case _ => None
  }
  def getOption(str: String): Option[Any] = this.constraints.get(str) match {
    case None => None
    case Some(constraint) => Option(constraint.value)
  }

  // Operators
  //----------------

  /**
   * Returns a new LAyout constraints beeing this one + the input one
   */
  def +(input: Constraints) = {

    var result = Constraints()
    this.constraints.values.foreach(result(_))
    input.constraints.values.foreach(result(_))
    result
  }

}

object Constraints {

  /**
   * Default Constructor
   */
  def apply(): Constraints = new Constraints
  
  /**
   * Constraints( name -> value,name->value)
   */
  def apply(cs: Constraint*): Constraints = {

    var constraints = new Constraints
    cs.foreach {
      t => constraints(t)
    }
    constraints

  }
  
  def apply(c: Constraint): Constraints = {

    var constraints = new Constraints
    constraints(c)
    constraints

  }
  
  /*def apply(constraints: (String,Any)*) : Constraints = {
    
    var res = new Constraints
    constraints.foreach(c => res(c))
    res
    
  }*/
  
  /*implicit def convertTuplesToConstraints(tuples: (String, Any)*): Constraints = {
	  
    var constraints = new Constraints
    tuples.foreach {
      t => 
        var c = new DefaultConstraint(t._1)
        c.value = t._2
        constraints(c)
    }
    constraints
    
   
  }*/
  
 

  implicit def convertTuplesToConstraints(tuples: (String, Constraint)*): Constraints = {

    var constraints = new Constraints
    tuples.foreach {
      t => constraints(t)
    }
    constraints

  }
 
  implicit def convertConstraintSeqToConstraints(cs:Seq[Constraint]) : Constraints = {
    
    var constraints = new Constraints
    cs.foreach {
      c => constraints(c)
    }
    constraints
    
  }
  
  implicit def convertConstraintSeq2ToConstraints(cs:Constraint*) : Constraints = {
    
    var constraints = new Constraints
    cs.foreach {
      c => constraints(c)
    }
    constraints
    
  }
  
  implicit def convertConstraintToConstraints(cs:Constraint) : Constraints = {
    
    var constraints = new Constraints
    constraints(cs)
    constraints
    
  }
  
  implicit def convertConstraintsToConstraintList(c:Constraints) : Seq[Constraint] = {
    
    var res = Seq[Constraint]()
    c.constraints.foreach {
      case (k,constraint) => res = res :+ constraint
    }
    res
  }
  
  
   
  /**
   * Convert a single tuple to a constraint
   */
  implicit def convertTuple1ToConstraint(tuple: Tuple2[String, Any]): DefaultConstraint = {

    var constraint = new DefaultConstraint(tuple._1)
    constraint.value = tuple._2
    constraint
  }
  
  implicit def convertTuple2ToConstraint[T <: Any](tuple:  Tuple2[String, T]): Constraint = {

    var constraint = new DefaultConstraint(tuple._1)
    constraint.value = tuple._2
    constraint
  }
  
  implicit def convertTuple3ToConstraint(tuple: (String, Constraint)): Constraint = {

    tuple._2
  }
  
  implicit def convertTuple4ToConstraint(tuple: (String, Any)): (String,Constraint) = {
	  
    (tuple._1 -> convertTuple2ToConstraint(tuple))
    

  }
  
  
  /*implicit def convertTuplesToConstraints(tuples: (String, Any)*): Constraints = {
	  
     var res = new Constraints
     tuples.foreach(res(_))
     res 

  }*/ 
   
   implicit def convertTupleToConstraints(tuples: (String, Any)): Constraints = {
	 
     var res = new Constraints
     res(tuples)
     res
     
    //(tuple._1 -> convertTuple2ToConstraint(tuple))
    

  }

  
  /**
   * Convert a constraint to a single tuple
   */
  implicit def convertConstraintToTuple(c: Constraint): (String, Constraint) = {
    (c.name -> c)
  }

}

trait ConstraintsConversions {
  
  

  

  
  
}


