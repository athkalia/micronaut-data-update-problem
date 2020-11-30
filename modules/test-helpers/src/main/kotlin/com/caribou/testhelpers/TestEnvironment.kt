package com.caribou.testhelpers

import java.lang.reflect.Field
import java.util.*

/** Update this to run all micronaut tests (that either use the `@MicronautTest` annotation or are direct handler tests)
 * in another environment. Values allowed:
 * "local" -> Loads config from "application-local.yml". Most notably it runs tests on a pre-existing local MySQL db.
 * "test" -> Loads config from "application-test.yml". Most notably it runs tests on MySQL db in a docker container.
 */
const val TEST_ENVIRONMENT = "test"

// Function sets an environment variable.
// See https://stackoverflow.com/a/7201825/1247323 on why this is so complex.
fun setEnvironmentVariables(environmentVariableMap: Map<String, String>) {
    try {
        val processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment")
        val theEnvironmentField: Field = processEnvironmentClass.getDeclaredField("theEnvironment")
        theEnvironmentField.isAccessible = true
        @Suppress("UNCHECKED_CAST") val env = theEnvironmentField.get(null) as MutableMap<String, String>
        env.putAll(environmentVariableMap)
        val theCaseInsensitiveEnvironmentField: Field = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment")
        theCaseInsensitiveEnvironmentField.isAccessible = true
        @Suppress("UNCHECKED_CAST") val cienv = theCaseInsensitiveEnvironmentField.get(null) as MutableMap<String, String>
        cienv.putAll(environmentVariableMap)
    } catch (e: NoSuchFieldException) {
        val classes: Array<Class<*>> = Collections::class.java.declaredClasses
        val env = System.getenv()
        for (cl in classes) {
            if ("java.util.Collections\$UnmodifiableMap" == cl.name) {
                val field: Field = cl.getDeclaredField("m")
                field.isAccessible = true
                val obj: Any = field.get(env)
                @Suppress("UNCHECKED_CAST") val map = obj as MutableMap<String, String>
                map.clear()
                map.putAll(environmentVariableMap)
            }
        }
    }
}
