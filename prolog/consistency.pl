:- module(consistency, [infrastructureless/1]).

/*

Consistency rules that can be used to analyse the consistency of the model.

*/

/* It may be irrelvant in which direction a relationship is modelled. */
undirectedRelationship(S,T):-relationship(_,_,S,T).
undirectedRelationship(S,T):-relationship(_,_,T,S).

/* An application without at least one infrastructure which it uses is considered incomplete, since it can not be used sans infrastructure.*/
infrastructureless(X):-
	element('applicationcomponent',X,_),
	\+ (undirectedRelationship(X,IE),
	    infrastructure(IE)).



/*
Is there a path connecting two elements?
https://www.cpp.edu/~jrfisher/www/prolog_tutorial/2_15.html */
travel(A,B,P,[B|P]):-
	relationship(_,_,A,B).

travel(A,B,Visited,Path):-
	relationship(_,_,A,C),
	C \== B,
	\+member(C,Visited),
	travel(C,B,[C|Visited], Path).

path(A,B,Path):-
	travel(A,B,[A],Q),
	reverse(Q, Path).

pathDesc([],'').
pathDesc([Head|Tail], Result):-
	describe(Head, Description),
	pathDesc(Tail, TailDescription),
	atom_concat(Description, TailDescription, Result).

/* A component that does not relate to any goals may be worth investigating. */
applicationNotRelatedToGoals(X):-
	element('applicationcomponent',X,_),
	\+ (goal(Goal),path(X,Goal,_)).

/*

Shortcuts that make it easier to formulate other rules.

*/

infrastructure(X):-element('node',X,_).
infrastructure(X):-element('infrastructureservice',X,_).
infrastructure(X):-element('infrastructureinterface',X,_).
infrastructure(X):-element('infrastructurefunction',X,_).
infrastructure(X):-element('device',X,_).

/* reachable */

directlyReachable(S,Reachable):-bagof(Target, undirectedRelationship(S, Target), Reachable).
/* bagof(Named, X^(undirectedRelationship('1899f5a0', X), named(X, Named)), Names). */

/* wildcard_match('[a-z]*.{pro,pl}[%~]', 'a_hello.pl%'). */


