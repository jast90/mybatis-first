/**
 * Created by zhiwen on 15-5-10.
 */
(function($){
    //何为全局方法？就像jQuery.each()、$.ajax()等
    //定义全局函数
    $.sum = function(x,y){
        return x+y;
    };
    //通过jQuery.extend(object)定义全局函数
    $.extend({
        extendSum:function(x,y){
            return x+y;
        }
    });
    //定义命名空间隔离的全局函数
    $.mathUtils = {
      sum:function(x,y){
          return x+y;
      }
    };

    //通过jQuery.extend(object)定义命名空间隔离的全局函数.
    $.extend({
        extendMathUtils:{
            sum:function(x,y){
                return x+y;
            }
        }
    })
})(jQuery);