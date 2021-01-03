package slashsourcegraph

val markdownMapping = mapOf(
    // Ada
    "adb" to "ada",
    "ada" to "ada",
    "ads" to "ada",
    // Actionscript
    "as" to "actionscript",
    
    // Apache
    "apacheconf" to "apache",
    
    // Applescript
    "applescript" to "applescript",
    "scpt" to "applescript",

    // Bash
    "sh" to "shell",
    "bash" to "shell",
    "zsh" to "shell",

    // Clojure
    "clj" to "clojure",
    "cljs" to "clojure",
    "cljx" to "clojure",

    // CSS
    "css" to "css",

    // CMake
    "cmake" to "cmake",
    "cmake.in" to "cmake",
    "in" to   "cmake",

    // Coffeescript
    "coffee" to "coffescript",
    "cake" to "coffescript",
    "cson" to "coffescript",
    "cjsx" to "coffescript",
    "iced" to "coffescript",

    // C#
    "cs" to "csharp",
    "csx" to "csharp",

    // C/C++
    "c" to "cpp",
    "cc" to "cpp",
    "cpp" to "cpp",
    "cxx" to "cpp",
    "c++" to "cpp",
    "h++" to "cpp",
    "hh" to "cpp",
    "h" to "cpp",
    "hpp" to "cpp",
    "pc" to "cpp",
    "pcc" to "cpp",
    
    // CUDA
    "cu" to "cuda",
    "cuh" to "cuda",
    
    // Dart
    "dart" to "dart",
    
    // Diff
    "diff" to "diff",
    "patch" to "diff",
    
    // Django
    "jinja" to "django",
    
    // DOS
    "bat" to "dos",
    "cmd" to "dos",
    
    // Elixir
    "ex" to "elixir",
    "exs" to "elixir",
    
    // Elm
    "elm" to "elm",
    
    // Erlang
    "erl" to "erlang",
    
    // Fortran
    "f" to "fortran",
    "for" to "fortran",
    "frt" to "fortran",
    "fr" to "fortran",
    "forth" to "fortran",
    "4th" to "fortran",
    "fth" to "fortran",
    
    // F#
    "fs" to "fsharp",
    
    // Go
    "go" to "go",
    
    // GraphQL
    "graphql" to "graphql",
    
    // Groovy
    "groovy" to "groovy",
    
    // HAML
    "haml" to "haml",
    
    // Handlebars
    "hbs" to "handlebars",
    "handlebars" to "handlebars",
    
    // Haskell
    "hs" to "haskell",
    "hsc" to "haskell",
    
    // HTML
    "htm" to "html",
    "html" to "html",
    "xhtml" to "html",
    
    // INI
    "ini" to "ini",
    "cfg" to "ini",
    "prefs" to "ini",
    "pro" to "ini",
    "properties" to "ini",
    
    // Java
    "java" to "java",
    
    // JavaScript
    "js" to "javascript",
    "jsx" to "javascript",
    "es" to "javascript",
    "es6" to "javascript",
    "jss" to "javascript",
    "jsm" to "javascript",
    
    // JSON
    "json" to "json",
    "sublime_metrics" to "json",
    "sublime_session" to "json",
    "sublime-keymap" to "json",
    "sublime-mousemap" to "json",
    "sublime-project" to "json",
    "sublime-settings" to "json",
    "sublime-workspace" to "json",
    
    // Jsonnet
    "jsonnet" to "jsonnet",
    "libsonnet" to "jsonnet",
    
    // Julia
    "jl" to "julia",
    
    // Kotlin
    "kt" to "kotlin",
    "ktm" to "kotlin",
    "kts" to "kotlin",
    
    // Less
    "less" to "less",
    
    // Lisp
    "lisp" to "lisp",
    "asd" to "lisp",
    "cl" to "lisp",
    "lsp" to "lisp",
    "l" to "lisp",
    "ny" to "lisp",
    "podsl" to "lisp",
    "sexp" to "lisp",
    "el" to "lisp",
    
    // Lua
    "lua" to "lua",
    "fcgi" to "lua",
    "nse" to "lua",
    "pd_lua" to "lua",
    "rbxs" to "lua",
    "wlua" to "lua",
    
    // Makefile
    "mk" to "makefile",
    "mak" to "makefile",
    
    // Markdown
    "md" to "markdown",
    "mkdown" to "markdown",
    "mkd" to "markdown",
    
    // nginx
    "nginxconf" to "nginx",
    
    // Objective-C
    "m" to "objectivec",
    "mm" to "objectivec",
    
    // OCaml
    "ml" to "ocaml",
    "eliom" to "ocaml",
    "eliomi" to "ocaml",
    "ml4" to "ocaml",
    "mli" to "ocaml",
    "mll" to "ocaml",
    "mly" to "ocaml",
    "re" to "ocaml",
    
    // Pascal
    "p" to "pascal",
    "pas" to "pascal",
    "pp" to "pascal",
    
    // Perl
    "pl" to "perl",
    "al" to "perl",
    "cgi" to "perl",
    "perl" to "perl",
    "ph" to "perl",
    "plx" to "perl",
    "pm" to "perl",
    "pod" to "perl",
    "psgi" to "perl",
    "t" to "perl",
    
    // PHP
    "php" to "php",
    "phtml" to "php",
    "php3" to "php",
    "php4" to "php",
    "php5" to "php",
    "php6" to "php",
    "php7" to "php",
    "phps" to "php",
    
    // Powershell
    "ps1" to "powershell",
    "psd1" to "powershell",
    "psm1" to "powershell",
    
    // Proto
    "proto" to "protobuf",
    
    // Python
    "py" to "python",
    "pyc" to "python",
    "pyd" to "python",
    "pyo" to "python",
    "pyw" to "python",
    "pyz" to "python",
    
    // R
    "r" to "r",
    "rd" to "r",
    "rsx" to "r",
    
    // Ruby
    "rb" to "ruby",
    "builder" to "ruby",
    "eye" to "ruby",
    "gemspec" to "ruby",
    "god" to "ruby",
    "jbuilder" to "ruby",
    "mspec" to "ruby",
    "pluginspec" to "ruby",
    "podspec" to "ruby",
    "rabl" to "ruby",
    "rake" to "ruby",
    "rbuild" to "ruby",
    "rbw" to "ruby",
    "rbx" to "ruby",
    "ru" to "ruby",
    "ruby" to "ruby",
    "spec" to "ruby",
    "thor" to "ruby",
    "watchr" to "ruby",
    
    // Rust
    "rs" to "rust",
    "rs.in" to "rust",
    
    // SASS
    "sass" to "scss",
    "scss" to "scss",
    
    // Scala
    "sbt" to "scala",
    "sc" to "scala",
    "scala" to "scala",
    
    // Scheme
    "scm" to "scheme",
    "sch" to "scheme",
    "sls" to "scheme",
    "sps" to "scheme",
    "ss" to "scheme",
    
    // Smalltalk
    "st" to "smalltalk",
    
    // SQL
    "sql" to "sql",
    
    // Stylus
    "styl" to "stylus",
    
    // Swift
    "swift" to "swift",
    
    // Thrift
    "thrift" to "thrift",
    
    // TypeScript
    "ts" to "typescript",
    "tsx" to "typescript",
    
    // Twig
    "twig" to "twig",
    
    // Visual Basic
    "vb" to "vbnet",
    "vbs" to "vbscrip",
    
    // Verilog, including SystemVerilog
    "v" to "verilog",
    "veo" to "verilog",
    "sv" to "verilog",
    "svh" to "verilog",
    "svi" to "verilog",
    
    // VHDL
    "vhd" to "vhdl",
    "vhdl" to "vhdl",
    
    // VIM
    "vim" to "vim",
    
    // XML
    "xml" to "xml",
    "adml" to "xml",
    "admx" to "xml",
    "ant" to "xml",
    "axml" to "xml",
    "builds" to "xml",
    "ccxml" to "xml",
    "clixml" to "xml",
    "cproject" to "xml",
    "csl" to "xml",
    "csproj" to "xml",
    "ct" to "xml",
    "dita" to "xml",
    "ditamap" to "xml",
    "ditaval" to "xml",
    "dll.config" to "xml",
    "dotsettings" to "xml",
    "filters" to "xml",
    "fsproj" to "xml",
    "fxml" to "xml",
    "glade" to "xml",
    "gml" to "xml",
    "grxml" to "xml",
    "iml" to "xml",
    "ivy" to "xml",
    "jelly" to "xml",
    "jsproj" to "xml",
    "kml" to "xml",
    "launch" to "xml",
    "mdpolicy" to "xml",
    "mjml" to "xml",
    "mod" to "xml",
    "mxml" to "xml",
    "nproj" to "xml",
    "nuspec" to "xml",
    "odd" to "xml",
    "osm" to "xml",
    "pkgproj" to "xml",
    "plist" to "xml",
    "props" to "xml",
    "ps1xml" to "xml",
    "psc1" to "xml",
    "pt" to "xml",
    "rdf" to "xml",
    "resx" to "xml",
    "rss" to "xml",
    "scxml" to "xml",
    "sfproj" to "xml",
    "srdf" to "xml",
    "storyboard" to "xml",
    "stTheme" to "xml",
    "sublime-snippet" to "xml",
    "targets" to "xml",
    "tmCommand" to "xml",
    "tml" to "xml",
    "tmLanguage" to "xml",
    "tmPreferences" to "xml",
    "tmSnippet" to "xml",
    "tmTheme" to "xml",
    "ui" to "xml",
    "urdf" to "xml",
    "ux" to "xml",
    "vbproj" to "xml",
    "vcxproj" to "xml",
    "vsixmanifest" to "xml",
    "vssettings" to "xml",
    "vstemplate" to "xml",
    "vxml" to "xml",
    "wixproj" to "xml",
    "wsdl" to "xml",
    "wsf" to "xml",
    "wxi" to "xml",
    "wxl" to "xml",
    "wxs" to "xml",
    "x3d" to "xml",
    "xacro" to "xml",
    "xaml" to "xml",
    "xib" to "xml",
    "xlf" to "xml",
    "xliff" to "xml",
    "xmi" to "xml",
    "xml.dist" to "xml",
    "xproj" to "xml",
    "xsd" to "xml",
    "xspec" to "xml",
    "xul" to "xml",
    "zcml" to "xml",
    
    // YAML
    "yml" to "yaml",
    "yaml" to "yaml",
)