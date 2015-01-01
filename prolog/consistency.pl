:- module(consistency, [infrastructureless/1, redundantRelationship/2]).

/*

Consistency rules that can be used to analyse the consistency of the model.

*/


/* An application without at least one infrastructure which it uses (or which it is somehow related to) is considered incomplete, since it can not be used sans infrastructure.*/
infrastructureless(X):-
	element('applicationcomponent',X,_),
	\+ (undirectedRelationship(X,IE),
	    infrastructure(IE)).



/* A component that does not relate to any goals may be worth investigating. */
applicationNotRelatedToGoals(X):-
	element('applicationcomponent',X,_),
	\+ (goal(Goal),path(X,Goal,_)).

/* Multiple identical relationships between two elements are not useful (it is implied that X and Y are elements). */
redundantRelationship(X,Y):-
 relationship(Type,First, X, Y),
 relationship(Type,Second, X, Y),
 First \= Second.
 