<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

 <form action="MainSearch" method="POST">
    Site: <select name="site">
    <option value="autoscoot">autoscout24.fr</option>
    </select>
    <br />
    Year: <input type="text" name="first_registration" value="2016">
    <br />
     Model: <input type="text" name="type_model" value="1">
    <br />
     Brand: <input type="text" name="brand" value="BMW">
    <br />
     Price: <input type="text" name="price" value="100000">
    <br />
    <br />
    Search name: <input type="text" name="search_name">
   <input type="submit" name="send" value="submit">
 </form>

</body>
</html>