# Components Reference 2 

%{toc}

## Containers


## Layouts

### HBox

### VBox

### Grid Manager



## Controls


### Button


Inherits: Common

Scala Doc:  [Here](scaladocs/index.html) <scaladocs/index.html>

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  var button = button("TEXT") {
  	b => 
  	
  }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#### Event: onClickFork

The onClickFork event runs the provided event handler in a new Thread, leveraging the risk of UI freeze.
During Event processing, the button is disabled and enabled back when all handlers are done.

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  button.onClickFork {
  	println(s"Clicked in a separate thread")
  }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

## Common Events

### On Click

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  component.onClick {
  	println(s"Clicked")
  }
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~




