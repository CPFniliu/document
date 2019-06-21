# skill

## Spring MVC 一个Controller可以同时返回json或者ModalAndView

1. 方案一(返回值类型为ModelAndView)

   ```java
      @RequestMapping(value = "/htmlorjson/{type}", method = {RequestMethod.GET, RequestMethod.POST})
      public ModelAndView ajaxPaymentBatchApproveProcess(@PathVariable String type)
      {
         if ("html".equals(type)) {
               //index表示指定路径下的jsp页面的名称
               ModelAndView mav = new ModelAndView("index");
               return mav;
         } else {
               ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
               mav.addObject("result", "failed");
               return mav;
         }
      }
   ```

2. 方案二(返回值类型为ModelAndView)

   ```java
   @RequestMapping("/ajaxPaymentBatchApproveProcess")
      public Object ajaxPaymentBatchApproveProcess(String packageIds, HttpServletResponse response) throws IOException {
         if (packageIds.equals("1")){
               return new ModelAndView("redirect:" + "https://traffic.sxwinstar.net/error/exception");
         }else{
            response.getWriter().write(packageIds);
               return  null;
         }
      }
   ```
