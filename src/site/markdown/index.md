# Virtual UI Core

Virtual UI is a Scala API to create and manage GUI components and their associated models.

It is defined without implementation to any GUI toolkit like Swing or JavaFX, and maps at runtime to the desired target.

The Goal of VUI is not to implement all possible functions, but cover 80-90% of the GUI creation work, which is usually quite repetitive, through a simple API.

## Available API

VUI defines a main UI Component API based on a Scene Graph interface

## Target Runtimes

The target runtimes for VUI are:

* Swing
* JavaFX
* HTML (HTML has its simpler user Interface with extra language components for its specific stuff)

## Extra Libraries

VUI contains a few generic Model definitions, which maps to the standard components.
For example:

* Graph Data Model
* View state machines (To Implement Dialog Boxes for example)

## Components Reference

See the [Components](components.html) page
