# Overview

Aside: this document is a work in progress and is designed to evolve to refine the features or support new ones. We
will be building sample apps incrementally to help refine the approach.

This document describes an approach that allows relatively simple *Kotlin* applications to be specified in such a way
that they can easily be developed, tested and deployed by engineers, whether human or machine (AI). 


> NB: All kokolex apps will be Kotlin first, and we expect that all code written will be in Kotlin. In some cases, libraries in 
> other languages may be used where there is a Kotlin wrapper. Typically, these would be java or javascript/typescript in the correct
> contexts, but could include wasm libraries in the future, perhaps.

A given application is described at the top level by a single kson file typically name <applicationId>.kspec.kson. It
defines some basic properties of the application and describes technologies/frameworks that are used in particular 
layers of the application or for the build and deployment processes.

Here is an example of such a file, followed by a description of the various properties:

```kson
applicationId: sampleApp
applicationName: 'Sample App'
applicationVersion: 1.0.0
applicationDomain: sampleapp.kokolex.com
applicationDescription: %
  A sample application used to build and test the process
  %%
ui: {
  framework: Vaadin
  appLayout: Default
  entities: {
    Foo: {
      layout: Default
      entityEditorLayout: Default
      searchResultsLayout: Default
    }
  }
}
storage:
    framework: Exposed {
        db: SQLite
    }
  .
rpc: -
authenticationEtc: NONE
types:%
  UUID: String {
    format: 'Base 36 Encoded UUID'
  }

  Name: String {
    minLength: 2
    maxLength: 30
    format: 'All characters excpet CR/LF and tab'
    formatRegex: Generate
  }

  Foo: Entity {
    fields: {
      id: UUID        .
      name: Name {
        searchable: true
        textSearch: true
        isName: true
      }
      ui: Standard
      entityLayout: Default
      searchColumns: Default
    }
    %%
deployment: Local



