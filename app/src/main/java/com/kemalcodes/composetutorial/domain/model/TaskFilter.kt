// TaskFilter.kt — The different ways to filter the task list.
// ALL shows everything, ACTIVE shows only incomplete tasks,
// and COMPLETED shows only finished tasks.
package com.kemalcodes.composetutorial.domain.model

enum class TaskFilter {
    ALL,
    ACTIVE,
    COMPLETED
}
