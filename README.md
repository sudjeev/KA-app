# KA-app: Infection Project

[Check out the live demo!](https://dry-ravine-36414.herokuapp.com/Infection)

This is the repository for my solution to the Khan Academy Infection Project.

I built this java application so that it could hypothetically be used by Khan Academy to analyze what percentage of their users should receive a new version of the website, and what the tradeoffs are for such a decision.

The first part of the project asked for an implementation of Total Infection: if one node in a graph component 
gets infected, all the other nodes in the same component also become infected. 

My solution to this specific section can be seen in the function totalInfection in [Infection.java](https://github.com/sudjeev/KA-app/blob/master/src/main/java/Infection.java).
Here I am simply using a Linked List to do a Breadth First Search, infecting all UserNodes in the component starting from 
the given node.

The next part of the project, Limited Infection, is a little more open ended. Specifically I was asked to:
  
  <i>
  "be able to infect close to a given number of users. 
  Ideally we’d like a coach and all of their students to either have a feature or not. 
  However, that might not always be possible. Implement a procedure for limited infection."
  </i>

To solve this problem I wrote two functions limitedInfection and exactInfection in [Infection.java](https://github.com/sudjeev/KA-app/blob/master/src/main/java/Infection.java).

<b>limitedInfection:</b> This function will try to get as close as possible to the percentage of users we want to infect, by only using the function <i>totalInfection</i>. This means that no single graph component will have users with different versions of the site, guaranteeing a better user experience. However, the tradeoff is that it is entirely possible that we will not be able to infect the exact percentage of users we wanted.

This is where the second function comes in!

<b>exactInfection:</b> This function guarantees that the exact percentage of users that we want to infect will be infected. 
  This is done in two parts:
    1. First, we see how close we can get to our desired percentage by only totally infecting full components. Once we reach 
       the point where infecting another full component will infect more users that our desired percentage; we stop and move to step 2. 
    2. Here we see how many users we still need to infect in order to hit out percentage. Then, all uninfected nodes are put into a 
       PriorityQueue which sorts nodes by the number of edges they have (fewest edges first). Finally we simply dequeue and infect as
       many nodes as we need to reach our desired percentage.

The good thing about exactInfection is that we are guaranteed to infect the desired the number of users. The tradeoff is that some of the
infected users may be in a component with users on a different version of the site, potentially compromising overall user experience.

To quantify the negative impact of exactInfection I also wrote the function <b>countUpsetUsers</b> in [Graph.java](https://github.com/sudjeev/KA-app/blob/master/src/main/java/Graph.java).
This basically shows how many users are at risk of a bad site experience by counting the number of edges between users on different versions of the website.

While I am somewhat satisfied with the information this tool provides I do believe the one major issue with my implementation is <i>runtime</i>.

Both limitedInfection and exactInfection call functions (allSums and allPositiveSums respectively) in order find the best set of components to totally infect. The issue is that allSums and allPositiveSums are both variations of the famous subsetSum problem which
is known for being NP-Complete. This is why calculating limitedInfection and exactInfection can be time consuming and leads to long page load times in the [demo](https://dry-ravine-36414.herokuapp.com/Infection).

Given some more time, the other things I would have liked to improve were:

  1. A better implementation of exactInfection. Instead of simply sorting UserNodes by the number of edges we could: 
    - Add weights to the edges that represent how often two users interact and then sort based on this value instead. We would then choose       to infect a node with weak edges over one with strong edges.
    - Add a property to UserNodes that shows how often a user engages. We can then target highly engaged users(brand loyal) with new             versions of the site as they are likely to continue using the website despite the perception of minor bugs.
 2. Allowing users of the live demo to pass in a range of percentage values. This way the online tool would essentially evaluate how close 
    limitedInfection could get and the impact on UX caused by exactInfection for each percentage in the range. Seeing a matrix of percent,     limitedInfection and exactInfection for a range of values would definitly make it easier to decide on the optimal type of infection.
3. Making the live demo responsive and mobile friendly, its clear that web development today must be compatible with all devices and          screen sizes.







  
  
