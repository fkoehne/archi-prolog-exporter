:- module(consistency, [infrastructureless/1]).

/*

Consistency rules that can be used to analyse the consistency of the model.

*/


/* An application without at least one infrastructure which it uses is considered incomplete, since it can not be used sans infrastructure.*/
infrastructureless(X):-
	element('applicationcomponent',X,_),
	\+ (undirectedRelationship(X,IE),
	    infrastructure(IE)).



/* A component that does not relate to any goals may be worth investigating. */
applicationNotRelatedToGoals(X):-
	element('applicationcomponent',X,_),
	\+ (goal(Goal),path(X,Goal,_)).
