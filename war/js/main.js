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
            var p = post["message"].split("\n").map(function(a){ return "<p>"+a +"</p>"; }).join("");
           
            var color = post["color"];
            $(".posts").append("<div class='post "+color+"'><div class='top'><a href='#'><h1>"+h1+"</h1></a><h2>"+h2+"</h2><h3>"+h3+"</h3></div>"+p+"</div>");
        }


        posts = posts + data["posts"].length;
        if(data["posts"].length < 5) {
        	morePosts = false;
        }
    }
