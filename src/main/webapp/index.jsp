<%@ page session="false"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<t:page>
    <jsp:attribute name="title">Khan Academy Infection</jsp:attribute>
    <jsp:body>
        <div class="jumbotron" id="welcome">
 
          <form method="GET">
           <p class="lead">Type of Infection:
          	<label class="checkbox-inline">
						  <input type="radio" name="infection" id="inlineCheckbox1" value="limited" checked> Limited Infection
						</label>
						<label class="checkbox-inline">
						  <input type="radio" name="infection" id="inlineCheckbox2" value="exact"> Exact Infection
						</label>
					 </p>
						<br />
					 <p class="lead">Percentage of users to infect: <input type="number" name="percentage" value="${percentage}"></p>

					 <br />
					 <input type="submit" value="Start Infecting" class="btn btn-default" />

					</form>

					<script type="text/javascript" src="http://mbostock.github.com/d3/d3.js?2.1.3"></script>

			    <style type="text/css">
		        .slice text {
		            font-size: 16pt;
		            font-family: Arial;
		        }   
    			</style>

					<style>

					.links line {
					  stroke: #999;
					  stroke-opacity: 0.6;
					}

					.nodes circle {
					  stroke: #fff;
					  stroke-width: 1.5px;
					}

					</style>
					<svg width="960" height="600"></svg>
					<script src="https://d3js.org/d3.v4.min.js"></script>
					<script>

					var svg = d3.select("svg"),
					    width = +svg.attr("width"),
					    height = +svg.attr("height");

					var color = d3.scaleOrdinal(d3.schemeCategory20);

					var simulation = d3.forceSimulation()
					    .force("link", d3.forceLink().id(function(d) { return d.id; }))
					    .force("charge", d3.forceManyBody())
					    .force("center", d3.forceCenter(width / 2, height / 2));


					var obj = JSON.parse('${graph}');

					//var obj = JSON.parse("{\"nodes\": [{\"id\": \"1\", \"group\": 3}], \"links\": []}");

					function draw(graph) {

					  var link = svg.append("g")
					      .attr("class", "links")
					    .selectAll("line")
					    .data(graph.links)
					    .enter().append("line")
					      .attr("stroke-width", function(d) { return 1; });

					  var node = svg.append("g")
					      .attr("class", "nodes")
					    .selectAll("circle")
					    .data(graph.nodes)
					    .enter().append("circle")
					      .attr("r", 4)
					      .attr("fill", function(d) {
					      	console.log(color(1));
					       if(d.group == "B"){
					       	return "#3498db";
					       } else {
					       	return "#e74c3c";
					       }
					     })
					      .call(d3.drag()
					          .on("start", dragstarted)
					          .on("drag", dragged)
					          .on("end", dragended));

					  node.append("title")
					      .text(function(d) { return d.id; });

					  simulation
					      .nodes(graph.nodes)
					      .on("tick", ticked);

					  simulation.force("link")
					      .links(graph.links);

					  function ticked() {
					    link
					        .attr("x1", function(d) { return d.source.x; })
					        .attr("y1", function(d) { return d.source.y; })
					        .attr("x2", function(d) { return d.target.x; })
					        .attr("y2", function(d) { return d.target.y; });

					    node
					        .attr("cx", function(d) { return d.x; })
					        .attr("cy", function(d) { return d.y; });
					  }
					}

					// d3.json("./infection.json", function(error, graph) {
					//   if (error) throw error;

					//   var link = svg.append("g")
					//       .attr("class", "links")
					//     .selectAll("line")
					//     .data(graph.links)
					//     .enter().append("line")
					//       .attr("stroke-width", function(d) { return Math.sqrt(d.value); });

					//   var node = svg.append("g")
					//       .attr("class", "nodes")
					//     .selectAll("circle")
					//     .data(graph.nodes)
					//     .enter().append("circle")
					//       .attr("r", 5)
					//       .attr("fill", function(d) { return color(d.group); })
					//       .call(d3.drag()
					//           .on("start", dragstarted)
					//           .on("drag", dragged)
					//           .on("end", dragended));

					//   node.append("title")
					//       .text(function(d) { return d.id; });

					//   simulation
					//       .nodes(graph.nodes)
					//       .on("tick", ticked);

					//   simulation.force("link")
					//       .links(graph.links);

					//   function ticked() {
					//     link
					//         .attr("x1", function(d) { return d.source.x; })
					//         .attr("y1", function(d) { return d.source.y; })
					//         .attr("x2", function(d) { return d.target.x; })
					//         .attr("y2", function(d) { return d.target.y; });

					//     node
					//         .attr("cx", function(d) { return d.x; })
					//         .attr("cy", function(d) { return d.y; });
					//   }
					// });

					function dragstarted(d) {
					  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
					  // d.fx = d.x;
					  // d.fy = d.y;
					}

					function dragged(d) {
					  // d.fx = d3.event.x;
					  // d.fy = d3.event.y;
					}

					function dragended(d) {
					  if (!d3.event.active) simulation.alphaTarget(0);
					  // d.fx = null;
					  // d.fy = null;
					}

					draw(obj);

					</script>

        </div>
    </jsp:body>
</t:page>