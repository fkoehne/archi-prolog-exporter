archi-prolog-exporter
=====================

An Archi export plugin that converts the model to prolog clauses for analysis and consistency checks

Motivation
----------
Why would anyone need this?
- Powerful reasoning accompanies expressive and flexible form of modeling.
- ArchiMate model with its elements and defined relationships calls for this type of query, 
both in order to analyse a model in order to learn from it and prepare decisions
and in order to find inconsistencies in the model or to make sure modeling guidelines are respected
- Personal motivation: keeping in touch with a beautiful language and problem solving approach. 
I first approached the idea of using prolog on a knowledge base representing an enterprise architecture with 
one of our students, Sven Thiele, in his bachelor thesis in 2011. The idea has been dormant hence, but
I plan to revive it and see where it goes from here.

Developer Environment Setup:
----------------------------
Set up an Archi development environement as presented here (in my case starting from a SpringSource STS):
http://archi.cetis.ac.uk/developer/import-code.html

Then create a launch configuration as shown here: http://archi.cetis.ac.uk/developer/running-archi.html
Make sure to increase the PermGen-Space as necessary if you include further plugins (–XX:MaxPermSize=512m on Java 7 for me) 

The basic export functionality is prepared and well documented here:
http://archi.cetis.ac.uk/developer/import-export.html 
The exporter so far is little more than that and can be incorporated in the same way as presented there. 

Backlog
-------
- [X] Set up Archi development environment
- [X] Export something from Archi (e.g. names of all applications) in to a single txt file using a custom exporter plugin as a technical POC.
- [X] Refine the export: "As an architect, I can list all application components in a given model via application(X) as a building ground for further reasoning"
- [X] Order the clauses to get rid of warnings
- [X] Introduce generalized element() clauses to easily identify ids
- [X] Introduce generalized relationship() clauses to easily identify ids
- [X] Create a git repository with that code
- [X] Choose an appropriate license and add / link it
- [X] Include motivation elements into the export
- [ ]  "As an architect I can list all applications that use a given application (directly or transitively via application(X), uses(Y, myPotentiallyDependentApp)."
- [ ] As model user I can see (and query) the time the export was created
- [ ] Consistency rule: Nodes w/o purpose
- [ ] Consistency rule: Applications w/o goals
- [ ] Consistency rule: Applications not used in processes
- [ ] Consistency rule: Relationships that are not used in any diagram (likely to be leftovers)
- [ ] Integrate model reasoning into archi user interface.
