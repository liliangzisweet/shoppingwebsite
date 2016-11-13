<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc">
    <div class="n-result">
        <h3>服务器开小差了...</h3>
        <p>将在 <span id="mes">3</span> 秒钟后返回首页！</p> 
    </div>
 </div>
</div>
<#include "/include/footer.ftl">
<script language="javascript" type="text/javascript"> 
var i = 3; 
var intervalid; 
intervalid = setInterval("redirect()", 1000); 
function redirect() { 
if (i == 0) { 
window.location.href = "/"; 
clearInterval(intervalid); 
} 
document.getElementById("mes").innerHTML = i; 
i--; 
} 
</script> 
</body>
</html>