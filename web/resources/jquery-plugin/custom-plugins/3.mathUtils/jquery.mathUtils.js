/**
 * Created by zhiwen on 15-5-10.
 */
(function($){
    /**
     *  一、全全局函数
     *  何为全局函数？就像jQuery.each()、$.ajax()等
     *  形如：
     *  $.命名空间.方法名();
     *
     *  定义全局函数
     *
     */
    //1.$.方法名 = function(){};
    $.sum = function(x,y){
        return x+y;
    };
    //2.通过jQuery.extend(object)定义全局函数
    $.extend({
        extendSum:function(x,y){
            return x+y;
        }
    });
    //3.定义命名空间隔离的全局函数
    $.mathUtils = {
      sum:function(x,y){
          return x+y;
      }
    };

    //4.通过jQuery.extend(object)定义命名空间隔离的全局函数.
    $.extend({
        extendMathUtils:{
            sum:function(x,y){
                return x+y;
            }
        }
    });



    /**
     *  二、jQuery对象方法
     *  对象方法就是就是通过jQuery选择器选中HTML元素后才能调用的调用的。
     *  形如：$("selector").方法();
     *
     *  定义对象方法，是通过
     *  $.fn.方法名=function(){};
     *  $.fn对象是$.prototype的别名，这样使用是处于代码简洁
     */

    /**
     * 1. 第一个对象方法
     *
     */
    $.fn.hello1 = function(){
        alert("你好，jQuery对象方法！"+"您的内容是："+$element.html());
    };

    /**
     * 2.对象方法的上下文this
     * 在jQuery对象方法中this引用的都是当前的jQuery对象。
     * 所以可以通过this可以调用所有的jQuery方法。
     */
    $.fn.hello2 = function(){

        console.log(this);//这里指的是jQuery对象。


    };

    /**
     * 3.隐式迭代
     * 因为jQuery的选择表达式可以匹配零、一或多个元素，
     * 所以在编写对象方法的时候必须考虑到所有的这些可能的结果，所以就有隐式迭代了。
     */
    $.fn.hello2 = function() {
        this.each(function () {
            var $element = $(this);
            alert("你好，jQuery对象方法！" + "您的内容是：" + $element.html());
        });
    };

    /**
     * 4.方法连缀
     * 首先要明白什么是jQuery的方法连缀？
     * 所谓的方法连缀就是一直可以调用jquery的方法，
     * 也就是所jquery的所有方法（全局函数，对象方法）的放回值还是一个jquery对象。
     */
    $.fn.hello3 = function() {
        return this.each(function () {
            var $element = $(this);
            alert("你好，jQuery对象方法！" + "您的内容是：" + $element.html());
        });
    };


    /**
     *  三、提供灵活的方法参数
     *  一个灵活的插件，应该可以让用户定义插件显示多种形态，
     *  所以插件应该有自己相关的参数来描述插件的属性。
     */

    /**
     * 1.参数对象
     *
     */
})(jQuery);