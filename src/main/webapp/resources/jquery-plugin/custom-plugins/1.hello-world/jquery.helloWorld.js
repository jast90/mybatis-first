/**
 * Created by zhiwen on 15-4-30.
 */
(function($){
    $.fn.helloWorld = function(){
        this.each(function(){
            $(this).text("Hello jQuery plugin!");
        });
    }
}(jQuery))