
    var post_form = document.getElementsByClassName('container-2')[0];
    var go_back = document.getElementById('go-back');

    post_form.style.display ="flex";

    post_form.style.animation ="slide-in-down 1s ease-in forwards";


    go_back.addEventListener('click',()=>{

    post_form.style.animation ="slide-out-anim 1s ease-out forwards";

    post.style.animation = "none";


    setTimeout( function(){
    post_form.style.display = "none";},1000);


});
