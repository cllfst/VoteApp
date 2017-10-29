window.onload = function () {
    move();
}

function move() {
    var elem = document.querySelector('#myBar');
    var width = 1;
    var id = setInterval(frame, 10);
    function frame() {
        if (width >= 100) {
            clearInterval(id);
        } else {
            width++;
            elem.style.width = width + '%';
        }
    }
}