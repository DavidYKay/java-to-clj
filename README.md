# java-to-clj

This is a basic Java -> Clojure source code converter.

Note! Some aspects of Java are not yet supported!

See the "TODO" list for parts of the Java language that aren't implemented yet.

Please file a ticket if you'd like me to hurry up and implement one of the below language features.

## Usage

* `lein ring server`
* Visit [http://localhost:3000](http://localhost:3000)
* Paste your Java code into the left pane
* Click "convert"
* You now have Clojure code in the right pane.

## TODO

* Anonymous class declaration - reify
* Increment
* For
* ForEach with Lambda
* class
* continue
* do
* Super
* Method Reference
* Deploy to Heroku/similar
* Log Exceptions
* Button for "this was a poor translation"
* while - mutable increment?
* Labels?
* Synchronized?
* Annotations?
* Type Expression?

## License

Copyright © 2018 David Young-chan Kay

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
