# KA-app: Infection Project

### This is the repository for my solution to the Khan Academy Infection Project.


[Check out the live demo!](https://dry-ravine-36414.herokuapp.com/Infection)

Interesting Demo Use Case:
* Limited Infection with 45 percent of users
* Exact Infection with 55 percent of users

I built this java application so that it could hypothetically be used by Khan Academy to analyze the percentage of their users that should receive a new version of the website, and what the tradeoffs would be.

The first part of the project asked for an implementation of Total Infection: if one node in a graph component 
gets infected, all the other nodes in the same component must also get infected. 

My solution to this specific section can be seen in the function totalInfection in [Infection.java](https://github.com/sudjeev/KA-app/blob/master/src/main/java/Infection.java).
Here I am simply using a Linked List to do a Breadth First Search and then infecting all the UserNodes in the component, starting from 
the given node.

The next part of the project, Limited Infection, is a little more open ended. Specifically I was asked to:
  
  <i>
  "be able to infect close to a given number of users. 
  Ideally we’d like a coach and all of their students to either have a feature or not. 
  However, that might not always be possible. Implement a procedure for limited infection."
  </i>

To solve this problem I wrote two functions limitedInfection and exactInfection in [Infection.java](https://github.com/sudjeev/KA-app/blob/master/src/main/java/Infection.java).

<b>limitedInfection:</b> This function will get as close as possible to the percentage of users we want to infect, by only using the function <i>totalInfection</i>. This means that no single graph component will have users with different versions of the site, guaranteeing a better user experience. However, the tradeoff is that it is entirely possible that the function will be unable to infect the exact percentage of users desired.

This is where the second function comes in!

<b>exactInfection:</b> This function guarantees that the exact percentage of users that we want to infect will be infected. This is done by:

1. First, seeing how close we can get to our desired percentage by only totally infecting full components. Once we reach 
       the point where infecting another full component will exceed our desired percentage, we stop and move to step 2.
2. Next, all uninfected nodes are put into a PriorityQueue which sorts nodes by the number of edges they have (fewest edges                  first). Finally we simply dequeue and infect as many nodes as we need to reach our desired percentage.

The benefit of exactInfection is that we are guaranteed to infect the desired number of users. The tradeoff is that some of the
infected users may be in a component with users on a different version of the site, potentially compromising overall user experience.

To quantify the negative impact of exactInfection, I also wrote the function <b>countUpsetUsers</b> in [Graph.java](https://github.com/sudjeev/KA-app/blob/master/src/main/java/Graph.java).
This basically shows how many users are at risk of a bad site experience by counting the number of edges between users on different versions of the website.

While I am somewhat satisfied with the information this tool provides I do believe the one major issue with my implementation is <i>runtime</i>.

Both limitedInfection and exactInfection call functions (allSums and allPositiveSums respectively) in order find the best set of components to totally infect. The issue is that allSums and allPositiveSums are both variations of the famous subsetSum problem which
is known for being NP-Complete. This is why calculating limitedInfection and exactInfection can be time consuming and lead to long page load times in the [demo](https://dry-ravine-36414.herokuapp.com/Infection).

Given some more time, the other things I would have liked to improve are:

1. A better implementation of exactInfection. Instead of simply sorting UserNodes by the number of edges we could: 
    * Add weights to the edges that represent how often two users interact and then sort based on this value. We would then choose to           infect a node with weak edges over one with strong edges.
    * Add a property to UserNodes that shows how often a user engages with the website. We can then target highly engaged users(brand           loyal) with new versions of the site as they are likely to continue using the website despite their perception of minor bugs.
    
2. Allowing users of [my online tool](https://dry-ravine-36414.herokuapp.com/Infection) to pass in a range of percentage values. The    tool would essentially enable the user to compare the tradeoffs of limitedInfection <b>against</b> exactInfection for every value in the range. This would definitly make it easier to decide how many users to infect and what type of infection algorithm to use.
    
3. Making the live demo more responsive and mobile friendly.



Sudjeev



  
  
