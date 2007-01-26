<!--
	Site design inspired by http://fullahead.org
-->
<cfset oPage = ViewState.getValue('PageObject')>
<cfset section = oPage.getPageName()>
<cfset myself = ViewState.getValue('myself')>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en-AU">

<head>

  <title>CFEclipse.org</title>

  <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
  <meta name="author" content="CFEclipse.org" />
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="robots" content="index, follow, noarchive" />
  <meta name="googlebot" content="noarchive" />

  <link rel="stylesheet" type="text/css" href="/assets/css/html.css" media="screen, projection, tv " />
  <link rel="stylesheet" type="text/css" href="/assets/css/layout.css" media="screen, projection, tv" />
  <link rel="stylesheet" type="text/css" href="/assets/css/print.css" media="print" />

</head>

<body class="subpage">
<!-- CONTENT: Holds all site content except for the footer.  This is what causes the footer to stick to the bottom -->
<div id="subpage_content">

    <div class="subpage_block_black"></div>
  <div id="subpage_menu">
 	 <div id="subpage_logo"></div>
	<cfoutput>
	<ul class="floatRight">
		<li><a href="#myself#page.index" title="Home" class="#iif(section EQ "home", DE('here'), DE('not_here'))#">Home</a></li>
	    <li><a href="#myself#page&page=download" title="Download" class="#iif(section EQ "download", DE('here'), DE('not_here'))#">Download</a></li>
	    <li><a href="#myself#page&page=features" title="Features" class="#iif(section EQ "features", DE('here'), DE('not_here'))#">Features</a></li>
	    <li><a href="#myself#page&page=support" title="Support" class="#iif(section EQ "support", DE('here'), DE('not_here'))#">Support</a></li>
	    <li><a href="#myself#page&page=news" title="News" class="#iif(section EQ "news", DE('here'), DE('not_here'))#">News</a></li>
		<li><a href="#myself#page&page=about" title="About" class="#iif(section EQ "about", DE('here'), DE('not_here'))#">About</a></li>
	</ul>
	</cfoutput> 
	 
</div>  
		  
   <div class="subpage_block_black"></div>

  <!-- PAGE CONTENT BEGINS: This is where you would define the columns (number, width and alignment) -->
  <div id="page">
		<cfoutput>#ViewCollection.getView('body')#</cfoutput>
  </div> <!-- END page -->

</div> <!-- end subpage_content -->

<!-- FOOTER: Site footer for links, copyright, etc. -->
<div id="footer">

  <div id="width">
    <span class="floatLeft">
      valid <a href="http://validator.w3.org/check?uri=referer" title="Validate XHTML">XHTML</a> <span class="grey">|</span>
      should be valid <a href="http://jigsaw.w3.org/css-validator" title="Validate CSS">CSS</a>
    </span>

    <span class="floatRight">  </span>
  </div>

</div> <!-- end footer -->
<cfif ViewState.getValue('google_production','false')>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
</script>
<script type="text/javascript">
_uacct = "UA-259985-2";
urchinTracker();
</script>
</cfif>
</body>

</html>