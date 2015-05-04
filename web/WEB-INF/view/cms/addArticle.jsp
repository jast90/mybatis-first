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
<html>
<head>
    <title>添加文章</title>
    <script src="${jquery}"></script>
</head>
<body>
    <form id="articleForm" >
        <label for="title">title:</label><input id="title" type="text" name="title"><br>
        <label for="content">content:</label><input type="text" id="content" name="content"><br>
        <a href="javascript:void(0);">add</a>
    </form>
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
