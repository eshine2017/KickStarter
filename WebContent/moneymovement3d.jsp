<!-- Bootstrap core CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<!-- suboptimal loading of js here to make loading cleaner - remove after async loading is done -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script> 
<script type='text/javascript' src="static/js/planetary/d3.v3.min.js"></script>
<script type='text/javascript' src="static/js/planetary/topojson.v1.min.js"></script> 
<script type='text/javascript' src="static/js/planetary/planetaryjs.min.js"></script>
<body style='overflow:hidden;'>
<canvas id='rotatingGlobe' width='800px' height='800px' style='width:100vw; height:100vw; cursor: move;'></canvas>
</body>
<script>
var urlParams = new URLSearchParams(window.location.search);
locationID = urlParams.get('l_id');

cache = null;
isFetching = false;
function getAndCache(callback){
	if(cache == null && !isFetching){
		isFetching = true;
		$.ajax({
	        url: '/dbms_ks/api/location/' + locationID + '/moneymovement',
	        success: function (data) {
	        	cache = data;
	        },
	        complete : function(){
	        	isFetching = false;
	        }
	    });
	}
	callback(cache);
}

(function() {
	  var globe = planetaryjs.planet();
	  // Load our custom `autorotate` plugin; see below.
	  globe.loadPlugin(autorotate(10));
	  // The `earth` plugin draws the oceans and the land; it's actually
	  // a combination of several separate built-in plugins.
	  //
	  // Note that we're loading a special TopoJSON file
	  // (world-110m-withlakes.json) so we can render lakes.
	  globe.loadPlugin(planetaryjs.plugins.earth({
	    topojson: { file:   'static/js/planetary/world-110m.json' },
	    oceans:   { fill:   '#FFFFFF' },
	    land:     { fill:   '#6e4cbb' },
	    borders:  { stroke: '#FFFFFF' }
	  }));
	
	  // The `pings` plugin draws animated pings on the globe.
	 // globe.loadPlugin(planetaryjs.plugins.pings());
	  // The `zoom` and `drag` plugins enable
	  // manipulating the globe with the mouse.
	  globe.loadPlugin(planetaryjs.plugins.zoom({
	    scaleExtent: [100, 300]
	  }));
	  globe.loadPlugin(planetaryjs.plugins.drag({
	    // Dragging the globe should pause the
	    // automatic rotation until we release the mouse.
	    onDragStart: function() {
	      this.plugins.autorotate.pause();
	    },
	    onDragEnd: function() {
	      this.plugins.autorotate.resume();
	    }
	  }));
	  // Set up the globe's initial scale, offset, and rotation.
	  globe.projection.scale(200).translate([350, 200]).rotate([0, -10, 0]);
	
	  globe.loadPlugin(function(planet) {
		  planet.onDraw(function () {
		    planet.withSavedContext(function (context) {
		    	getAndCache(function(data){
		    		if(data == null) return; //waiting for async caching
		    		
		    		fromLoc = data['from']
		    		for(idx in data['links']){
		    			link = data['links'][idx] 
	    	    		var arc = {
							type: "LineString", 
							coordinates: [[fromLoc['longitude'], fromLoc['latitude']], [ link['longitude'], link['latitude']]]
						}
				        context.beginPath();
		    			context.strokeStyle="#76ff03";
				        planet.path.context(context)(arc);
				        context.stroke();
				        context.closePath();		
		    		}
		    	});
		    });
		  });
	  });
	  
	  /*
	  // Every few hundred milliseconds, we'll draw another random ping.
	  var colors = ['red', 'yellow', 'white', 'orange', 'green', 'cyan', 'pink'];
	  setInterval(function() {
	    var lat = Math.random() * 170 - 85;
	    var lng = Math.random() * 360 - 180;
	    var color = colors[Math.floor(Math.random() * colors.length)];
	    globe.plugins.pings.add(lng, lat, { color: color, ttl: 2000, angle: Math.random() * 10 });
	  }, 150);*/

	  var canvas = document.getElementById('rotatingGlobe');
	  // Special code to handle high-density displays (e.g. retina, some phones)
	  // In the future, Planetary.js will handle this by itself (or via a plugin).
	  if (window.devicePixelRatio == 2) {
	    canvas.width = 800;
	    canvas.height = 800;
	    context = canvas.getContext('2d');
	    context.scale(2, 2);
	  }
	  // Draw that globe!
	  globe.draw(canvas);

	  // This plugin will automatically rotate the globe around its vertical
	  // axis a configured number of degrees every second.
	  function autorotate(degPerSec) {
	    // Planetary.js plugins are functions that take a `planet` instance
	    // as an argument...
	    return function(planet) {
	      var lastTick = null;
	      var paused = false;
	      planet.plugins.autorotate = {
	        pause:  function() { paused = true;  },
	        resume: function() { paused = false; }
	      };
	      // ...and configure hooks into certain pieces of its lifecycle.
	      planet.onDraw(function() {
	        if (paused || !lastTick) {
	          lastTick = new Date();
	        } else {
	          var now = new Date();
	          var delta = now - lastTick;
	          // This plugin uses the built-in projection (provided by D3)
	          // to rotate the globe each time we draw it.
	          var rotation = planet.projection.rotate();
	          rotation[0] += degPerSec * delta / 1000;
	          if (rotation[0] >= 180) rotation[0] -= 360;
	          planet.projection.rotate(rotation);
	          lastTick = now;
	        }
	      });
	    };
	  };
	})();
</script> 
  