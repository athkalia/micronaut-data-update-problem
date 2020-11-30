package com.caribou.base.runtime

import io.micronaut.function.aws.runtime.AbstractMicronautLambdaRuntime
import io.micronaut.logging.LogLevel

// Common runtime parent class. Any common config goes here. All Micronaut function modules subclass it to define their
// own runtime as they can have different generics parameters.
abstract class FunctionBaseRuntime<RequestType, ResponseType, HandlerRequestType, HandlerResponseType> :
    AbstractMicronautLambdaRuntime<RequestType, ResponseType, HandlerRequestType, HandlerResponseType>() {

    // Overriding the default log level and maxing it out. This is controlled by the 'application.yml' file anyway.
    override fun getLogLevel(): LogLevel {
        return LogLevel.ALL
    }

}
