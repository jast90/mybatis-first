<%--
  Created by IntelliJ IDEA.
  User: zhiwen
  Date: 15-4-22
  Time: 下午9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="jquery" value="/resources/js/jquery-1.11.2.min.js"/>
<c:url var="bootstrapCSS" value="/resources/bootstrap-3.3.5-dist/css-slate/bootstrap.css"/>
<c:url var="bootstrapJS" value="/resources/bootstrap-3.3.5-dist/js/bootstrap.min.js"/>
<html>
<head>
    <title>添加文章</title>
    <link href="${bootstrapCSS}" rel="stylesheet">
    <script src="${jquery}"></script>
    <script src="${bootstrapJS}"></script>
</head>
<body>
    <div class="container-fluid">
        <form id="articleForm" >
            <label for="title">title:</label><input class="form-control" id="title" type="text" name="title"><br>
            <label for="content">content:</label><input class="form-control" type="text" id="content" name="content"><br>
            <a href="javascript:void(0);">add</a>
        </form>
    </div>
</body>
<script type="application/javascript">
    $(function(){
      $("a").on("click",function(){
          console.log($("#articleForm").serializeArray());
          $.ajax({
              url:'<c:url value="/article/add.do"/>',
              type:"POST",
              data:$("#articleForm").serialize(),
              success:function(data){
                  console.log(data);
              }
          });
      });
    });
</script>
</html>
