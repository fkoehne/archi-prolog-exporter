:- module(vocabulary, [named/2, named/1, typed/2, goal/1, describe/2, infrastructure/1]).

/*

Basic vocabulary that makes it easier to define all kinds of rules.

*/

/* 
 All Elements are considered named, regardless of their type. 
 X=Element ID
 Name=Name of element
*/
named(X, Name):-element(_, X, Name).

/* 
 If element names are consistently used, they can be used instead of model IDs in order to make the shell interaction more readable. 
 X=Name of Element*/
 named(X):-element(_,_,X).

/* 
 All Elements and relationships are typed and can be found 
 by their type. 
 X=Element ID
 Type=Type of element or relationship. */
typed(X, Type):-element(Type, X,_).
typed(X, Type):-relationship(Type, X, _,_).

/* 
 Goals are identified elements of type goal, regardless of their name 
 X=Element ID */
goal(X):-element('goal',X,_).

/* 
 It may be useful to see more than one property of an element at once. 
 X=Element ID
 Description=Textual description of the element with ID X.*/
describe(X, Description):-
	element(Type, X, Name),
	atom_concat(Name, ' (', Part1),
	atom_concat(Part1, Type, Part2),
	atom_concat(Part2, ', ', Part3),
	atom_concat(Part3, X, Part4),
	atom_concat(Part4, ')', Description).
	
/*
 Is a given Element part of the infrastructure layer?
 X=Element ID.
*/
infrastructure(X):-element('node',X,_).
infrastructure(X):-element('infrastructureservice',X,_).
infrastructure(X):-element('infrastructureinterface',X,_).
infrastructure(X):-element('infrastructurefunction',X,_).
infrastructure(X):-element('network',X,_).
infrastructure(X):-element('communicationpath',X,_).
infrastructure(X):-element('artifact',X,_).
infrastructure(X):-element('systemsoftware',X,_).
infrastructure(X):-element('device',X,_).
	