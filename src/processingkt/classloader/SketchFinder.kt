package processingkt.classloader

import processingkt.KApplet
import java.io.File
import java.io.IOException

object SketchFinder {
    fun getSketches(): List<Class<KApplet>> {
        val allClasses= getClasses("processingkt")
        return allClasses.filter {
            it.superclass.simpleName.endsWith("KApplet")
        }.map {
            it as Class<KApplet>
        }
    }

    fun getSketchNames(): List<String> {
        val allClasses= getClasses("processingkt")
        return allClasses.filter {
            it.superclass.simpleName.endsWith("KApplet")
        }.map {
            it.simpleName
        }
    }

    fun getSketchAt(index: Int): KApplet{
        val sketches = getSketches()
        when {
            index < sketches.size -> return getSketches()[index].newInstance()
            else -> throw Exception("No sketch at index $index")
        }
    }

    @Throws(ClassNotFoundException::class, IOException::class)
    private fun getClasses(packageName: String): Array<Class<*>> {
        val classLoader = Thread.currentThread().contextClassLoader!!
        val path = packageName.replace('.', '/')
        val resources = classLoader.getResources(path)
        val dirs = mutableListOf<File>()
        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            dirs.add(File(resource.file))
        }
        val classes = mutableListOf<Class<*>>()
        for (directory in dirs) {
            findClasses(directory, packageName).forEach {
                classes.add(it)
            }
        }
        classes.sortBy { it.simpleName }.also {
            return classes.toTypedArray()
        }
    }

    @Throws(ClassNotFoundException::class)
    private fun findClasses(directory: File, packageName: String): List<Class<*>> {
        val classes = mutableListOf<Class<*>>()
        if (!directory.exists()) {
            return classes
        }
        val files = directory.listFiles()
        for (file in files!!) {
            if (file.isDirectory) {
                assert(!file.name.contains("."))
                findClasses(file, packageName + "." + file.name).forEach {
                    classes.add(it)
                }
            } else if (file.name.endsWith(".class")) {
                classes.add(Class.forName(packageName + '.'.toString() + file.name.substring(0, file.name.length - 6)))
            }
        }
        return classes
    }
}