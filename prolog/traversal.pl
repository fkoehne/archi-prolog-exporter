:- module(traversal, [undirectedRelationship/2, path/3, directlyConnectedElements/2, pathDesc/2]).

/*

Predicates that allow to navigate through the Archimate graph(s) more easily.

*/

/* It may be irrelevant in which direction a relationship is modelled. 
 S=Source Node (an Element)
 T=Target Node (an Element)
*/
undirectedRelationship(S,T):-relationship(_,_,S,T).
undirectedRelationship(S,T):-relationship(_,_,T,S).


/* Is there a path connecting two elements? Note that this predicate uses directed relationships only.
 See https://www.cpp.edu/~jrfisher/www/prolog_tutorial/2_15.html 
 A=Source Node (an Element that serves as a starting point)
 B=Target Node (an Element we try to reach)
 P=The path found, if any (a list of elements)
*/
path(A,B,Path):-
	travel(A,B,[A],Q),
	reverse(Q, Path).
 
travel(A,B,P,[B|P]):-
	relationship(_,_,A,B).

travel(A,B,Visited,Path):-
	relationship(_,_,A,C),
	C \== B,
	\+member(C,Visited),
	travel(C,B,[C|Visited], Path).
	

/* What elements can we reach from a given element through relationships or vice versa (1 step)?
 S=Source Node (an element)
 Reachable=A list of elements that can be reached from S or that reach S. */
directlyConnectedElements(S,Reachable):-bagof(Target, undirectedRelationship(S, Target), Reachable).
	
/* If you found a path (or some other list of elements) and want to present that in readable form, then
 consider this predicate.
 [List]=A list of Elements assumed to be connected in the order presented (possibly on other path as well).
 Result=An atom created as a readable representation for a path through a model - it contains descriptions
  of all elements.
*/
pathDesc([],'').
pathDesc([Head|Tail], Result):-
	describe(Head, Description),
	pathDesc(Tail, TailDescription),
	atom_concat(Description, TailDescription, Result).

