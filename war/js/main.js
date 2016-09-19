var canLoadMore = true;
    var posts = 0;
    var morePosts = true;
    var page = 0;
    $(window).bind("load", function() {

        loadDo();

        $(window).scroll(function() {
            if(canLoadMore && morePosts) {

                var documentHeight = $(document).height();
                var windowHeight = window.innerHeight;
                var scolledPos = $(window).scrollTop();

                if( scolledPos + 200 >= documentHeight - windowHeight) {
                    loadDo();
                }
            }
        });
    });
    
    $(".postOverlay").hide();
    $("a.createLink").bind("click", function(event) {
    	event.preventDefault();
    	
    	
    	
    	$(".postOverlay").slideDown();
    	$("a.createLink").addClass("active");
    	$(".title").focus();
    });
    $("a.cancel").bind("click", function(event) {
    	event.preventDefault();
    	
    	$(".postOverlay").slideUp();
    	$("a.createLink").removeClass("active");
    });

    function loadDo() {
        canLoadMore = false;
        $(".post").removeClass("hide");
        $(".pull").hide();
        $(".end").hide();


        // Give us 5 loading posts.
        for (var i = 0; i < 5; i++) {
            $(".posts").append("<div class='post loading'><div class='top'><h1><span class='loading'></span></h1><h2><span class='loading'></span></h2><h3><span class='loading'></span></h3></div><p><span class='loading'></span></p>        </div>")
        }

        
		$.getJSON( "/posts", {page: page} , function( data ) {
			page = page + 1;
            loadDone(data);
            canLoadMore = true;

            if(!morePosts){ // TODO change this to be when we receive less than 5 results
                $(".end").show();
            } else {
                $(".pull").show();
            }
		});
    }

    function loadDone(data) {
        $(".post.loading").remove();

        for (var i in data["posts"]) {
        	var post = data["posts"][i];
            var h1 = post["title"];
            var h2 = post["date"];
            var h3 = post["author"];
            var p = post["message"].join("\n\n");
//            .split("\n").map(function(a){ return "<p>"+a +"</p>"; }).join("");
           
            var converter = new showdown.Converter();
            p = converter.makeHtml(p);
            
            var color = post["color"];
            $(".posts").append("<div class='post "+color+"'><div class='top'><a href='#'><h1>"+h1+"</h1></a><h2>"+h2+"</h2><h3>"+h3+"</h3></div><div class='content'>"+p+"</div></div>");
        }


        posts = posts + data["posts"].length;
        if(data["posts"].length < 5) {
        	morePosts = false;
        }
    }
