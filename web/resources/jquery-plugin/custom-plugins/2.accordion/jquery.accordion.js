/**
 * Created by zhiwen on 15-4-30.
 */
(function($){
    $.fn.accordion = function(options){
        var settings = $.extend({},{open:false},options);
        return this.each(function(){
            var dts = $(this).children('dt');
            dts.click(onClick);//给所有的dt都设置click事件，用来将自己下面的dd显示出来
            dts.each(reset);//将所有的dd都隐藏
            if(settings.open)//将第一个dd显示出来
                $(this).children('dt:first-child').next().show();
        });

        function onClick(){
            $(this).siblings('dt').each(hide);
            $(this).next().slideToggle('fast');
            return false;
        }

        function hide(){
            $(this).next().slideUp('fast');
        }

        function reset(){
            $(this).next().hide();
        }

    }
}(jQuery));