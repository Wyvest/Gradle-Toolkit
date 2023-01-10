package xyz.deftu.gradle

import org.gradle.api.Project
import xyz.deftu.gradle.utils.propertyOr

class ProjectData(
    val present: Boolean,
    val name: String,
    val version: String,
    val group: String,
    val mainClass: String?
) {
    companion object {
        @JvmStatic
        fun from(project: Project): ProjectData {
            val extension = project.extensions.findByName("projectData") as ProjectData?
            if (extension != null) return extension

            if (!project.hasProperty("project.group")) return ProjectData(false, "", "", "", null)

            val name = project.propertyOr("project.name", project.name, false)
            val version = project.propertyOr("project.version", project.version.toString(), false)
            val group = project.propertyOr("project.group", project.group.toString(), false)
            val mainClass = project.propertyOr("project.mainClass", null, false)
            val data = ProjectData(true, name, version, group, mainClass)
            project.extensions.add("projectData", data)
            return data
        }
    }
}
