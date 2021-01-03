package slashsourcegraph

val searchQueryGenerators = mapOf<String, (options: List<ApplicationCommandInteractionDataOption>?) -> String>(
    "search" to {
        it!![0].value!!
    },
    "raw-search" to {
        it!!.fold("") { acc, next ->
            next.value ?: return@fold acc

            val name = when(next.name) {
                "pattern-type" -> "patternType"
                else -> next.name
            }

            val value = when(next.name) {
                "case" -> if(next.value == "true") "yes" else "no"
                else -> next.value
            }

            acc + "${name}:${value}"
        }
    }
)