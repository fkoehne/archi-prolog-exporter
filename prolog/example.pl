/*
This a prolog representation of an Archi model (http://www.archimatetool.com).
It was created using the prolog exporter plugin (https://github.com/fkoehne/archi-prolog-exporter).
The syntax (esp. the module syntax) is tested with SWI-Prolog, but should work with other engines as well.

Export date: Sat Dec 27 16:13:52 CET 2014
Model: prologExample
[undocumented]
*/
:- use_module(vocabulary).
:- use_module(traversal).
:- use_module(consistency).

element('businessactor','7ec9ef3c','Enterprise Architect').
element('applicationcomponent','9d2b137e','Archi').
element('applicationinterface','5da2c4e0','Archi Prolog Exporter').
element('applicationinterface','5c736d20','Archi User Interface').
element('applicationfunction','14c0ebf0','Check Model Consistency').
element('applicationcomponent','56ae230b','Prolog').
element('applicationinterface','921a8d12','Prolog Shell Interface').
element('applicationcomponent','0999035f','notepad++').
element('node','5ac2b74f','My Laptop').
element('infrastructureservice','c764b640','Model Storage').
element('driver','bbbf7631','Model Complexity').
element('goal','1899f5a0','Model Consistency').
relationship('association','fdd1bd27','9d2b137e','5ac2b74f').
relationship('usedby','25531cbe','9d2b137e','7ec9ef3c').
relationship('composition','4fd3e6fe','9d2b137e','5da2c4e0').
relationship('composition','95bcb23a','9d2b137e','5c736d20').
relationship('association','4a0f669d','56ae230b','5ac2b74f').
relationship('composition','fe506d70','56ae230b','921a8d12').
relationship('usedby','a0344a77','921a8d12','14c0ebf0').
relationship('association','97b3a4b3','bbbf7631','5da2c4e0').
relationship('realisation','d61c24b4','5ac2b74f','c764b640').
relationship('usedby','a6102961','c764b640','9d2b137e').
relationship('usedby','33256362','c764b640','56ae230b').
relationship('association','75906496','9d2b137e','1899f5a0').
relationship('realisation','c1f26af1','14c0ebf0','1899f5a0').
