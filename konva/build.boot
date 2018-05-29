(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.10.0" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "2.1.3")
(def +version+ (str +lib-version+ "-0"))

(task-options!
 pom  {:project     'cljsjs/konva
       :version     +version+
       :description "HTML5 2d canvas library for desktop and mobile applications"
       :url         "https://konvajs.github.io/"
       :scm         {:url "https://github.com/konvajs/konva/tree/2.1.3"}
       :license     {"MIT" "https://github.com/konvajs/konva/blob/2.1.3/LICENCE"}})

(deftask package []
  (comp
    (download :url (format "https://cdn.rawgit.com/konvajs/konva/%s/konva.js" +lib-version+)
              :checksum "E1E94B639F8B7A0844136F6CC6504404")
    (download :url (format "https://cdn.rawgit.com/konvajs/konva/%s/konva.min.js" +lib-version+)
              :checksum "51B516F9454918E786AB2363CA16ECF8")
    (sift :move {#"^konva.js"     "cljsjs/konva/development/konva.inc.js"
                 #"^konva.min.js" "cljsjs/konva/production/konva.min.inc.js"})
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.konva")
    (pom)
    (jar)))