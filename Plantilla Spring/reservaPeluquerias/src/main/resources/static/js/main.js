var index = 0;
$(document).ready(function() {
    showSlides();
});
          
function nextImg(n) {
    index += n;
    showSlides();
}

function showSlides() {
    var slides = document.getElementsByClassName("mySlides");
    if(index >= slides.length) {
        index = 0;
    }

    if(index < 0) {
        index = slides.length - 1;
    }

    for (i=0; i<slides.length; i++) {
        slides[i].style.display = "none";
    }

    slides[index].style.display = "block";
}