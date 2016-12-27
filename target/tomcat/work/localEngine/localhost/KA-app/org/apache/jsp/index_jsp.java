package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/WEB-INF/tags/page.tag");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, false, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      if (_jspx_meth_t_005fpage_005f0(_jspx_page_context))
        return;
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_t_005fpage_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  t:page
    org.apache.jsp.tag.web.page_tag _jspx_th_t_005fpage_005f0 = new org.apache.jsp.tag.web.page_tag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_t_005fpage_005f0);
    _jspx_th_t_005fpage_005f0.setJspContext(_jspx_page_context);
    String _jspx_temp0 = "Khan Academy Infection";
    // /index.jsp(7,0) null
    _jspx_th_t_005fpage_005f0.setTitle(_jspx_temp0);
    _jspx_th_t_005fpage_005f0.setJspBody(new Helper( 0, _jspx_page_context, _jspx_th_t_005fpage_005f0, null));
    _jspx_th_t_005fpage_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_t_005fpage_005f0);
    return false;
  }

  private class Helper
      extends org.apache.jasper.runtime.JspFragmentHelper
  {
    private javax.servlet.jsp.tagext.JspTag _jspx_parent;
    private int[] _jspx_push_body_count;

    public Helper( int discriminator, JspContext jspContext, javax.servlet.jsp.tagext.JspTag _jspx_parent, int[] _jspx_push_body_count ) {
      super( discriminator, jspContext, _jspx_parent );
      this._jspx_parent = _jspx_parent;
      this._jspx_push_body_count = _jspx_push_body_count;
    }
    public boolean invoke0( JspWriter out ) 
      throws Throwable
    {
      out.write("\n");
      out.write("        <div class=\"jumbotron\" id=\"welcome\">\n");
      out.write(" \n");
      out.write("          <form method=\"GET\">\n");
      out.write("           <p class=\"lead\">Type of Infection:\n");
      out.write("          \t<label class=\"checkbox-inline\">\n");
      out.write("\t\t\t\t\t\t  <input type=\"radio\" name=\"infection\" id=\"inlineCheckbox1\" value=\"Limited\" checked> Limited Infection\n");
      out.write("\t\t\t\t\t\t</label>\n");
      out.write("\t\t\t\t\t\t<label class=\"checkbox-inline\">\n");
      out.write("\t\t\t\t\t\t  <input type=\"radio\" name=\"infection\" id=\"inlineCheckbox2\" value=\"Exact\"> Exact Infection\n");
      out.write("\t\t\t\t\t\t</label>\n");
      out.write("\t\t\t\t\t </p>\n");
      out.write("\t\t\t\t\t\t<br />\n");
      out.write("\t\t\t\t\t <p class=\"lead\">Percentage of users to infect: <input type=\"number\" name=\"percentage\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${percentage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></p>\n");
      out.write("\n");
      out.write("\t\t\t\t\t <br />\n");
      out.write("\t\t\t\t\t <input align=\"center\" type=\"submit\" value=\"Start Infecting\" class=\"btn btn-default\" />\n");
      out.write("\t\t\t\t\t \n");
      out.write("\t\t\t\t\t <br />\n");
      out.write("\t\t\t\t\t <br />\n");
      out.write("\n");
      out.write("\t\t\t\t\t</form>\n");
      out.write("\n");
      out.write("\t\t\t\t\t<h4 align=\"center\">Attempting ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${infection}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write(" Infection of ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${percentage}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("% of users</h4>\n");
      out.write("\t\t\t\t\t<h4 align=\"center\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${percentInfected}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("% of users have been infected, ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${percentBadUsers}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("% of users have a potentially bad user experience</h4>\n");
      out.write("\t\t\t\t\t<script type=\"text/javascript\" src=\"http://mbostock.github.com/d3/d3.js?2.1.3\"></script>\n");
      out.write("\n");
      out.write("\t\t\t    <style type=\"text/css\">\n");
      out.write("\t\t        .slice text {\n");
      out.write("\t\t            font-size: 16pt;\n");
      out.write("\t\t            font-family: Arial;\n");
      out.write("\t\t        }   \n");
      out.write("    \t\t\t</style>\n");
      out.write("\n");
      out.write("\t\t\t\t\t<style>\n");
      out.write("\n");
      out.write("\t\t\t\t\t.links line {\n");
      out.write("\t\t\t\t\t  stroke: #999;\n");
      out.write("\t\t\t\t\t  stroke-opacity: 0.6;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\t\t\t.nodes circle {\n");
      out.write("\t\t\t\t\t  stroke: #fff;\n");
      out.write("\t\t\t\t\t  stroke-width: 1.5px;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\t\t\t</style>\n");
      out.write("\t\t\t\t\t<svg width=\"960\" height=\"600\"></svg>\n");
      out.write("\t\t\t\t\t<script src=\"https://d3js.org/d3.v4.min.js\"></script>\n");
      out.write("\t\t\t\t\t<script>\n");
      out.write("\n");
      out.write("\t\t\t\t\tvar svg = d3.select(\"svg\"),\n");
      out.write("\t\t\t\t\t    width = +svg.attr(\"width\"),\n");
      out.write("\t\t\t\t\t    height = +svg.attr(\"height\"),\n");
      out.write("\t\t\t\t\t    radius = 3;\n");
      out.write("\n");
      out.write("\t\t\t\t\tvar color = d3.scaleOrdinal(d3.schemeCategory20);\n");
      out.write("\n");
      out.write("\t\t\t\t\tvar simulation = d3.forceSimulation()\n");
      out.write("\t\t\t\t\t    .force(\"link\", d3.forceLink().id(function(d) { return d.id; }))\n");
      out.write("\t\t\t\t\t    .force(\"charge\", d3.forceManyBody())\n");
      out.write("\t\t\t\t\t    .force(\"center\", d3.forceCenter(width / 2, height / 2));\n");
      out.write("\n");
      out.write("\n");
      out.write("\t\t\t\t\tvar obj = JSON.parse('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${graph}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("');\n");
      out.write("\n");
      out.write("\t\t\t\t\tfunction draw(graph) {\n");
      out.write("\n");
      out.write("\t\t\t\t\t  var link = svg.append(\"g\")\n");
      out.write("\t\t\t\t\t      .attr(\"class\", \"links\")\n");
      out.write("\t\t\t\t\t    .selectAll(\"line\")\n");
      out.write("\t\t\t\t\t    .data(graph.links)\n");
      out.write("\t\t\t\t\t    .enter().append(\"line\")\n");
      out.write("\t\t\t\t\t      .attr(\"stroke-width\", function(d) { return 1; });\n");
      out.write("\n");
      out.write("\t\t\t\t\t  var node = svg.append(\"g\")\n");
      out.write("\t\t\t\t\t      .attr(\"class\", \"nodes\")\n");
      out.write("\t\t\t\t\t    .selectAll(\"circle\")\n");
      out.write("\t\t\t\t\t    .data(graph.nodes)\n");
      out.write("\t\t\t\t\t    .enter().append(\"circle\")\n");
      out.write("\t\t\t\t\t      .attr(\"r\", 5)\n");
      out.write("\t\t\t\t\t      .attr(\"fill\", function(d) {\n");
      out.write("\t\t\t\t\t       if(d.group == \"B\"){\n");
      out.write("\t\t\t\t\t       \treturn \"#3498db\";\n");
      out.write("\t\t\t\t\t       } else {\n");
      out.write("\t\t\t\t\t       \treturn \"#e74c3c\";\n");
      out.write("\t\t\t\t\t       }\n");
      out.write("\t\t\t\t\t     })\n");
      out.write("\t\t\t\t\t      .call(d3.drag()\n");
      out.write("\t\t\t\t\t          .on(\"start\", dragstarted)\n");
      out.write("\t\t\t\t\t          .on(\"drag\", dragged)\n");
      out.write("\t\t\t\t\t          .on(\"end\", dragended));\n");
      out.write("\n");
      out.write("\t\t\t\t\t  node.append(\"text\")\n");
      out.write("\t\t\t\t\t  \t\t.attr(\"dx\", 12)\n");
      out.write("      \t\t\t\t\t.attr(\"dy\", \".35em\")\n");
      out.write("\t\t\t\t\t      .text(function(d) { return d.id; });\n");
      out.write("\n");
      out.write("\t\t\t\t\t  simulation\n");
      out.write("\t\t\t\t\t      .nodes(graph.nodes)\n");
      out.write("\t\t\t\t\t      .on(\"tick\", ticked);\n");
      out.write("\n");
      out.write("\t\t\t\t\t  simulation.force(\"link\")\n");
      out.write("\t\t\t\t\t      .links(graph.links);\n");
      out.write("\n");
      out.write("\t\t\t\t\t  function ticked() {\n");
      out.write("\t\t\t\t\t    link\n");
      out.write("\t\t\t\t\t        .attr(\"x1\", function(d) { return d.source.x; })\n");
      out.write("\t\t\t\t\t        .attr(\"y1\", function(d) { return d.source.y; })\n");
      out.write("\t\t\t\t\t        .attr(\"x2\", function(d) { return d.target.x; })\n");
      out.write("\t\t\t\t\t        .attr(\"y2\", function(d) { return d.target.y; });\n");
      out.write("\n");
      out.write("\t\t\t\t\t    node.attr(\"cx\", function(d) { return d.x = Math.max(radius, Math.min(width - radius, d.x)); })\n");
      out.write("\t\t\t\t\t        .attr(\"cy\", function(d) { return d.y = Math.max(radius, Math.min(height - radius, d.y)); });\n");
      out.write("\t\t\t\t\t  }\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\t\t\tfunction dragstarted(d) {\n");
      out.write("\t\t\t\t\t  if (!d3.event.active) simulation.alphaTarget(0.3).restart();\n");
      out.write("\t\t\t\t\t  d.fx = d.x;\n");
      out.write("\t\t\t\t\t  d.fy = d.y;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\t\t\tfunction dragged(d) {\n");
      out.write("\t\t\t\t\t  d.fx = d3.event.x;\n");
      out.write("\t\t\t\t\t  d.fy = d3.event.y;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\t\t\tfunction dragended(d) {\n");
      out.write("\t\t\t\t\t  if (!d3.event.active) simulation.alphaTarget(0);\n");
      out.write("\t\t\t\t\t  d.fx = null;\n");
      out.write("\t\t\t\t\t  d.fy = null;\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\t\t\tdraw(obj);\n");
      out.write("\n");
      out.write("\t\t\t\t\t</script>\n");
      out.write("\n");
      out.write("        </div>\n");
      out.write("    ");
      return false;
    }
    public void invoke( java.io.Writer writer )
      throws JspException
    {
      JspWriter out = null;
      if( writer != null ) {
        out = this.jspContext.pushBody(writer);
      } else {
        out = this.jspContext.getOut();
      }
      try {
        this.jspContext.getELContext().putContext(JspContext.class,this.jspContext);
        switch( this.discriminator ) {
          case 0:
            invoke0( out );
            break;
        }
      }
      catch( Throwable e ) {
        if (e instanceof SkipPageException)
            throw (SkipPageException) e;
        throw new JspException( e );
      }
      finally {
        if( writer != null ) {
          this.jspContext.popBody();
        }
      }
    }
  }
}
