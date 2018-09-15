(ns java-to-clj.core

  )

(defn parse [x]
  (let [^CompilationUnit compilationUnit (.parse JavaParser "class A { }")
        ^Optional<ClassOrInterfaceDeclaration> a (.getClassByName compilationUnit "A")]
    a))

compilationUnit.findAll(ClassOrInterfaceDeclaration.class).stream()
        .filter(c -> !c.isInterface()
                && c.isAbstract()
                && !c.getNameAsString().startsWith("Abstract"))
        .forEach(c -> {
            String oldNamec.getNameAsString()
            String newName"Abstract" + oldName
            System.out.println("Renaming class " + oldName + " into " + newName)
            c.setName(newName)
        })
