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
        alert("你好，jQuery对象方法！");
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
     *  所以插件应该有自己相关的参数（参数列表或者是参数对象）来描述插件的属性。
     */

    /**
     * 1.参数对象
     *  作为一种像像插件用户公开选项的方式，对象要比参数列表更加友好，
     *  所有我们应该使用对象作为参数。所以就有了参数对象了。
     */
    $.fn.hello4 = function(options){
      return this.each(function(){
         var $element = $(this);
          //然后就可以在逻辑中使用参数对象了
      });
    };

    /**
     * 2.默认参数
     * 随着参数的增多，每次都要指定参数值，这样做是很不合理的。
     * 那么一组合理的默认值就显得格外中重要了，他能够增强插件的易用性。
     * 首相要理解$.extend()全局方法的用法，参考：http://www.css88.com/jqapi-1.9/jQuery.extend/
     */
    $.fn.hello5 = function(opts){
        //默认参数对象
        var defaults = {
            obj1:"value1",
            obj2:"value2"
        }
        //调用的时候就直接掉options，会合并deafaults 和opts，如果存在相同对象值取靠后的。
        //所以可以达到覆盖默认参数的效果。
        var options = $.extend(defaults,opts);
    };

    /**
     * 3.回调函数
     *  在默认参数对象中我们可以定义普通属性、数组、函数还可以是对象
     *  当我们定义了函数的时候，结合$.extend(target，object)方法就可实现回调函数了。
     *  因为自定义的函数回覆盖默认的函数，所以就形成了回调函数。
     */
    $.fn.helloCallBack = function(opts){
        //默认参数对象
        var defaults = {
            obj1:"value1",
            obj2:"value2",
            sayHello:function(){
                alert("Hello World!");
            }
        }
        //调用的时候就直接掉options，会合并deafaults 和opts，如果存在相同对象值取靠后的。
        //所以可以达到覆盖默认参数的效果。
        var options = $.extend(defaults,opts);
        options.sayHello();

        //插件逻辑
        return this.each(function(){
            options.sayHello();
        });
    };
    //调用方式：$("selector").helloCallBack({obj1:"",obj2:"",sayHello:function(){}});


    /**
     * 4.可定制的默认值
     *  所谓可定制的默认值，就是可以手动配置默认值后，
     *  不需要在使用的时候传一堆的大堆的参数过来。
     *  如何做到可以定制呢？
     *  通过$.fn.pluginname.deafults={},也就是把默认参数设置成全局变量可以通过命名空间找到并修改他的值的
     */

    $.fn.helloCustomDefaults = function(opts){
        $.fn.helloCustomDefaults.defaults = {
            message:"Hello World！"
        }
        var options = $.extend({}, $.fn.helloCustomDefaults.defaults,opts);

        //插件逻辑
        return this.each(function(){
            var $element = $(this);

        });
    }

    /**
     * 四、何为jQuery插件？如何开发一个插件？
     * 1.何为jQuery插件？
     *     首先要明白一点，其实jQuery就是一个js插件，通过jQuery可以让我们更好的操作或者渲染HTML元素，
     * 而jQuery插件其实是扩展jQuery的功能使之满足自己的要求。
     *
     * 2.如何开发一个jQuery插件？
     *     首先你要清楚你的插件是要拿来干什么的?是用来操作HTML元素、渲染HTML元素还是操作和渲染HTML元素呢？
     *
     *
     */

})(jQuery);