# jQuery 选择器

  ```html
  <!DOCTYPE html>
  <html>
  <head>
  <meta charset="utf-8">
  <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
  </script>
  <script>
  $(document).ready(function(){
    $("button").click(function(){
      // $("a[target='_blank']").hide();
      var h = $("p:first");
      h.text($("a:eq(1)").attr("hj"))

      // 获取第二个a标签里面属性 hj 的值
      $("a:eq(1)").attr("hj")
    });
  });
  </script>
  </head>
  <body>

  <h2>这是标题</h2>
  <p>这是一个段落。</p>
  <p>这是另外一个段落。</p>
  <p><a href="http://www.runoob.com/html/" target="_blank" hj="fd1">HTML 教程</a></p>
  <p><a href="http://www.runoob.com/html/" target="_blank" hj="fd222">HTML 教程11</a></p>
  <p><a href="http://www.runoob.com/css/">CSS 教程</a></p>
  <button>点我</button>

  </body>
  </html>
  ```

  ```js
    // 选取当前 HTML 元素
    $(this)
    // 选取所有元素
    $("*")
    // 选择所有 p 标签元素
    $("p").hide()
    // 选择id=test的元素
    $("#test")
    // 选择class为test的元素
    $(".test")
    // 选取第一个 <p> 元素
    $("p:first")
    // 获取第二个a标签里面属性 hj 的值
    $("a:eq(1)").attr("hj")
    // 选取第一个 <ul> 元素的第一个 <li> 元素
    $("ul li:first");
    // 选取每个 <ul> 元素的第一个 <li> 元素
    $("ul li:first-child")
    // 选取带有 href 属性的元素
    $("[href]")
    // 选取所有 target 属性值等于 "_blank" 的 <a> 元素
    $("a[target='_blank']")
    // 选取所有 target 属性值不等于 "_blank" 的 <a> 元素
    $("a[target!='_blank']")
    // 选取所有 type="button" 的 <input> 元素 和 <button> 元素
    $(":button")
    // 选取偶数位置的 <tr> 元素
    $("tr:even")
    // 选取奇数位置的 <tr> 元素
    $("tr:odd")
  ```
