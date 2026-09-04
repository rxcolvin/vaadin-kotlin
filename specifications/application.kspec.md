# Overview

Aside: this document is a work in progress and is designed to evolve to refine the features or support new ones. We
will be building sample apps incrementally to help refine the approach.

This document describes an approach that allows relatively simple *Kotlin* applications to be specified in such a way
that they can easily be developed, tested and deployed by engineers, whether human or machine (AI). 


> NB: All kokolex apps will be Kotlin first, and we expect that all code written will be in Kotlin. In some cases, libraries in 
> other languages may be used where there is a Kotlin wrapper. Typically, these would be java or javascript/typescript in the correct
> contexts, but could include wasm libraries in the future, perhaps.

A given application is described at the top level by a single file typically name <applicationId>.kspec.kdf. It
defines some basic properties of the application and describes technologies/frameworks that are used in particular 
layers of the application or for the build and deployment processes.

A rough guide to the format is in the file ./kdf_format.md.


Here is an example of such a file, followed by a description of the various properties:

```
/*
* This is a sample application - block comment
*/
application: {
  id: sampleApp   //inline comment
  name: 'Sample App'
  applicationVersion: 1.0.0
  applicationDescription: %
    A sample application used to build and test the process
      of developing a Kokolex application.
    %%
}
storage: {
  default: {
    framework: Exposed {
        db = SQLite // '=' rather than ':' 
    }
  }
}
targets: {
    SSR_Vaadin {
        ui: &desktop
        storage : &default
        executables: {
            exeJar:{}
            war: {}
            graalvmNative: {
                targets: [
                    macosX64
                ]
            }
            docker: {
            }
        }
    } 
    Compose_Desktop_RPC_Client: {
      executables: {
        exeJar :{}
        kotlinNative : {
          targets: [
            macosX64 
            linuxX64
            ]
        } 
      }      
    }
    Compose_Desktop_Standalone {
      server: &rpcServer
      executables: {
        exeJar :{}
        kotlinNative : {
          targets: [macosX64, linuxX64,]
        }       
    }
    Compose_Web_RPC_Client {
    }    
    RPC_Server: {
        id: rpcServer
        rpc: KoltinRpc
        storage : Exposed {
            db: SQLite
        }
    }      
}

ui: {
  default: {
      navigation: Default        
      entities: {
        Foo: {
          layout: Default
          entityEditorLayout: Default
          searchResultsLayout: Default
        }
      }
  }
  desktop: {
    extends: default
    navigation:DeskTop   
  }
}

authenticationEtc: {
    default: None
}

types:{
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
  }
}
```
## application
* id: the unique identifier for the application used build artifacts and other names.
* name: used in the UI to identify the application and documentation, for example.
* version: the version of the application. Used in the UI and build artifacts, for example.
* description: a short description of the application for documentation purposes.


## targets
A single functional application can be delivered in multiple ways and with different architectures. Each has their
own specific requirements and capabilities as listed below:

### SSR_Vaadin
This target delivers the application using Server Side Rendering (SSR) and Vaadin. It can be configured as follows:
* *ui*: is either a reference $id to a UI definition in the top level ui section or a UI definition itself, See ui below.
If no value is provided, then the "default" UI is used.
* *storage*: defines the approach to storing data. It is either a reference $id to a storage definition 
in the top level storage section or a storage definition itself. If no value is provided, then the default storage 
is used.



### Compose_Desktop_RPC_Client
This target delivers the application as a desktop application using Compose for Desktop and a Kotlin RPC client.
details TODO

### Compose_Desktop_Standalone
This target delivers the application as a desktop application using Compose for Desktop and a standalone Kotlin RPC server.
details TODO

### Compose_Web_RPC_Client

This target delivers the application as a web application using Compose for Web and a Kotlin RPC client.

### RPC_Server
This target delivers the application as a standalone Kotlin RPC server.
Details TODO

## UI
This section defines one or more UI schemes that can be used in the various targets.
The approach to defining the UI is described in the ui/UI.kspec.md file.

## storage
This section defines one or more storage schemes that can be used in the various targets.
The approach to defining the storage is described in the storage/Storage.kspec.md file. (TBD)

## rpc
TODO

## authenticationEtc
TODO. But NONE is the only type supported currently. Which means that anyone with access to the URL a web app or
has the client app installed can access the app.


## types
The *types* element can either reference a separate file or be defined inline.

The specific types are defined in the types/DataTypes.kspec.md file.




