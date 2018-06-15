/*window.onload = function() {
    document.addEventListener("contextmenu", function(e){
      e.preventDefault();
    }, false);
    document.addEventListener("keydown", function(e) {
      // CTRL + SHIFT + "I" key
      if (e.ctrlKey && e.shiftKey && e.keyCode == 73) {
        disabledEvent(e);
      }
      // CTRL + SHIFT + "J" key
      if (e.ctrlKey && e.shiftKey && e.keyCode == 74) {
        disabledEvent(e);
      }
      // CTRL + "S" key
      if (e.ctrlKey && e.keyCode == 83) {
          disabledEvent(e);
        }
      // CTRL + "U" key
      if (e.ctrlKey && e.keyCode == 85) {
        disabledEvent(e);
      }
      // "F1" key
      if ((e.which || e.keyCode) == 112) {
    	  disabledEvent(e);
      }
      // "F3" key
      if ((e.which || e.keyCode) == 114) {
    	  disabledEvent(e);
      }
      // "F5" key
      if ((e.which || e.keyCode) == 116) {
    	  disabledEvent(e);
      }
      // "F6" key
      if ((e.which || e.keyCode) == 117) {
    	  disabledEvent(e);
      }
      // "F12" key
      if (event.keyCode == 123) {
        disabledEvent(e);
      }
      // "CTRL" key
      if (e.ctrlKey || e.keyCode == 17) {
    	  disabledEvent(e);
      }
      // "CTRL + SHIFT + R" key
      if (e.ctrlKey && e.shiftKey && e.keyCode == 82) {
        disabledEvent(e);
      }
      // "CTRL + R" key
      if (e.ctrlKey && e.keyCode == 82) {
    	  disabledEvent(e);
      }
    }, false);
    function disabledEvent(e){
      if (e.stopPropagation){
        e.stopPropagation();
      } else if (window.event){
        window.event.cancelBubble = true;
      }
      e.preventDefault();
      return false;
    }
  };
  
$(document).ready(function() {
	history.pushState(null, null, location.href);
	window.onpopstate = function() {
		history.go(1);
	};
	
	document.oncontextmenu = document.body.oncontextmenu = function() {return false;}
});*/